package com.example.learnkanji;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class ThirdActivity extends android.app.Activity {
    private TextView mTextViewResult;

    private static int[] answerArray = {R.id.answer1, R.id.answer2, R.id.answer3, R.id.answer4};
    private Button[] button = new Button[answerArray.length];
    private TextView mTextViewCounter;

    int counter = 0;
    int mistakeCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        mTextViewResult = findViewById(R.id.text_view_result);
        Button buttonStart = findViewById(R.id.button_start);
        Button answer1 = findViewById(R.id.answer1);
        Button answer2 = findViewById(R.id.answer2);
        Button answer3 = findViewById(R.id.answer3);
        Button answer4 = findViewById(R.id.answer4);
        TextView startTitle = findViewById(R.id.start_title);
        TextView startRomaji = findViewById(R.id.start_romaji);
        TextView progressText = findViewById(R.id.counter_text);
        TextView progressCounter = findViewById(R.id.answer_counter);
        TextView counterLimit = findViewById(R.id.counter_limit);

        buttonStart.setOnClickListener(view -> {
            if (!FileExists("kanji.txt")){
                Toast.makeText(ThirdActivity.this, "No internet connection and local files found, connect to internet to get data", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ThirdActivity.this, SecondActivity.class));
                return;
            }

            fetchFromRoom();

            buttonStart.setVisibility(View.INVISIBLE);
            startTitle.setVisibility(View.INVISIBLE);
            startRomaji.setVisibility(View.INVISIBLE);

            counterLimit.setVisibility(View.VISIBLE);
            answer1.setVisibility(View.VISIBLE);
            answer2.setVisibility(View.VISIBLE);
            answer3.setVisibility(View.VISIBLE);
            answer4.setVisibility(View.VISIBLE);
            progressCounter.setVisibility(View.VISIBLE);
            progressText.setVisibility(View.VISIBLE);

        });

    }

    public String readFromFile(String fileName) {
        String result = "";

        try {
            InputStream inputStream = openFileInput(fileName);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receivedString;
                StringBuilder stringBuilder = new StringBuilder();

                while ((receivedString = bufferedReader.readLine()) != null) {
                    stringBuilder.append("\n").append(receivedString);
                }

                inputStream.close();
                result = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("FileReader", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("FileReader", "Can not read file: " + e.toString());
        }
        return result;
    }

    private void writeToFile(String data, String fileName) {
        try {
            FileOutputStream fileOut = openFileOutput(fileName, MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOut);
            Log.d("writing data to:", fileName + " " + data);
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean FileExists(String fname) {
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

    private void fetchFromRoom(){



        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Data> dataWords = DatabaseClient.getInstance(ThirdActivity.this).getAppDatabase().wordDAO().getAll();
                SecondActivity.arrayList.clear();

                ArrayList<Data> kanjiList = new ArrayList<>();


                kanjiList.addAll(dataWords);

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        for (int i = 0 ; i < dataWords.size(); i++) {
                            int n = new Random().nextInt(dataWords.size());

                            int realAnswer = new Random().nextInt(4);

                            String correct1;
                            int a2 = new Random().nextInt(dataWords.size());
                            mTextViewResult.setText(kanjiList.get(n).getKanji());

                            Log.d("hi", String.valueOf(kanjiList.size()));
                            String correct2 = dataWords.get(n).getHiragana();



                            Log.d("arraysize", String.valueOf(kanjiList.get(n).getKanji()));


                            for (int a = 0; a < answerArray.length; a++) {
                                button[a] = findViewById(answerArray[a]);
                                int a1 = new Random().nextInt(30);

                                button[a].setText(dataWords.get(a1).getHiragana());

                                button[a].setOnClickListener(view -> {
                                    Context contextWrong = getApplicationContext();
                                    CharSequence wrong = "Wrong Answer!";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast wrongAnswer = Toast.makeText(contextWrong, wrong, duration);
                                    wrongAnswer.show();
                                    mistakeCounter++;
                                });

                                if (dataWords.get(a1).getHiragana().equals(correct2)) {
                                    if (a1 != a2) {
                                        button[a].setText(dataWords.get(a2).getHiragana());
                                    } else button[a].setText(dataWords.get(a1 + 1).getHiragana());
                                }

                            }
                            button[realAnswer].setText(dataWords.get(n).getHiragana());
                            correct1 = (String) button[realAnswer].getText();
                            String finalCorrect = correct1;
                            button[realAnswer].setOnClickListener(view -> {

                                if (finalCorrect.equals(correct2)) {
                                    mTextViewCounter = findViewById(R.id.answer_counter);
                                    fetchFromRoom();
                                    counter++;
                                    String correctCounter = Integer.valueOf(counter).toString();
                                    mTextViewCounter.setText(correctCounter);
                                }

                            });

                            if (counter == 29) {
                                Log.d("counter", "30X GEDAAN");
                                String date;
                                Calendar calendar = Calendar.getInstance();
                                date = DateFormat.getDateTimeInstance().format(calendar.getTime());
                                if (FileExists("mistakes.txt")) {
                                    String oldMistakes = readFromFile("mistakes.txt");
                                    String oldCorrect = readFromFile("correct.txt");
                                    String oldDate = readFromFile("date.txt");
                                    Log.d("oldMistakes", oldMistakes);
                                    counter++;
                                    writeToFile(oldMistakes + mistakeCounter + "|", "mistakes.txt");
                                    writeToFile(oldCorrect + counter + "|", "correct.txt");
                                    writeToFile(oldDate + date + "|", "date.txt");
                                } else {
                                    writeToFile(mistakeCounter + "|", "mistakes.txt");
                                    writeToFile(counter + "|", "correct.txt");
                                    writeToFile(date + "|", "date.txt");
                                }
                                startActivity(new Intent(ThirdActivity.this, SecondActivity.class));
                            }
                        }

                    }

                });

                }


        });
        thread.start();
    }
}
