package com.example.learnkanji;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.util.Calendar;
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
            getRandomCharacter();
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


    public void getRandomCharacter() {
        int n = new Random().nextInt(30);
        int realAnswer = new Random().nextInt(4);
        String correct1;
        String correct2 = SecondActivity.hiragana_data_local.get(n);
        int a2 = new Random().nextInt(SecondActivity.hiragana_data_local.size());

        mTextViewResult.setText(SecondActivity.kanji_data_local.get(n));

        for (int i = 0; i < answerArray.length; i++) {
            button[i] = findViewById(answerArray[i]);
            int a1 = new Random().nextInt(30);

            button[i].setText(SecondActivity.hiragana_data_local.get(a1));

            button[i].setOnClickListener(view -> {
                Context contextWrong = getApplicationContext();
                CharSequence wrong = "Wrong Answer!";
                int duration = Toast.LENGTH_SHORT;
                Toast wrongAnswer = Toast.makeText(contextWrong, wrong, duration);
                wrongAnswer.show();
                mistakeCounter++;
            });

            if (SecondActivity.hiragana_data_local.get(a1).equals(correct2)) {
                if (a1 != a2) {
                    button[i].setText(SecondActivity.hiragana_data_local.get(a2));
                } else button[i].setText(SecondActivity.hiragana_data_local.get(a1 + 1));
            }

        }
        button[realAnswer].setText(SecondActivity.hiragana_data_local.get(n));
        correct1 = (String) button[realAnswer].getText();
        String finalCorrect = correct1;
        button[realAnswer].setOnClickListener(view -> {

            if (finalCorrect.equals(correct2)) {
                mTextViewCounter = findViewById(R.id.answer_counter);
                getRandomCharacter();
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
}
