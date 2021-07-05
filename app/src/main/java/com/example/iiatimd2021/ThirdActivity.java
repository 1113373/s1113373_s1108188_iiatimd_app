package com.example.iiatimd2021;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

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
import java.util.Random;

public class ThirdActivity extends android.app.Activity {
    private TextView mTextViewResult;

    private static int[] answerArray = { R.id.answer1, R.id.answer2, R.id.answer3, R.id.answer4 };
    private Button[] button = new Button[answerArray.length];
    private TextView mTextViewCounter;
    private String sessionData = "";
    public static ArrayList<String> mistakes_local = new ArrayList<String>();
    public static ArrayList<String> correct_local = new ArrayList<String>();
    public static ArrayList<String> date_local = new ArrayList<String>();
    public static ArrayList<String> mistakes_file = new ArrayList<String>();
    public static ArrayList<String> correct_file = new ArrayList<String>();
    public static ArrayList<String> date_file = new ArrayList<String>();
    String mistakeList;
    String correctList;
    String dateList;

    int counter = 0;
    int mistakeCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        mTextViewResult = findViewById(R.id.text_view_result);
        Button buttonParse = findViewById(R.id.button_parse);
        Button answer1 = findViewById(R.id.answer1);
        Button answer2 = findViewById(R.id.answer2);
        Button answer3 = findViewById(R.id.answer3);
        Button answer4 = findViewById(R.id.answer4);

        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("kanji test", kanji_data_local.toString());
                getRandomCharacter();
                buttonParse.setVisibility(View.INVISIBLE);
                answer1.setVisibility(View.VISIBLE);
                answer2.setVisibility(View.VISIBLE);
                answer3.setVisibility(View.VISIBLE);
                answer4.setVisibility(View.VISIBLE);
            }
        });
    }


    public void getRandomCharacter() {
        int n = new Random().nextInt(30);
        int realAnswer = new Random().nextInt(4);
        //Log.d("random", String.valueOf(n));
        String correct1 = "";
        String correct2 = SecondActivity.hiragana_data_local.get(n);
        int a2 = new Random().nextInt(SecondActivity.hiragana_data_local.size());

        mTextViewResult.setText(SecondActivity.kanji_data_local.get(n));
        //mTextViewResult.setText(hiragana_data_local.get(n));


        for (int i = 0; i < answerArray.length; i++){
            button[i] = findViewById(answerArray[i]);
            int a1 = new Random().nextInt(30);

            button[i].setText(SecondActivity.hiragana_data_local.get(a1));

            button[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Nice", "dit werkt");
                    Context contextWrong = getApplicationContext();
                    CharSequence wrong = "Wrong Answer!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast wrongAnswer = Toast.makeText(contextWrong, wrong, duration);
                    wrongAnswer.show();
                    mistakeCounter++;
                }
            });

            if (SecondActivity.hiragana_data_local.get(a1) == correct2){
                if (a1 != a2){
                    button[i].setText(SecondActivity.hiragana_data_local.get(a2));
                }
                else button[i].setText(SecondActivity.hiragana_data_local.get(a1+1));
            }

        }
        button[realAnswer].setText(SecondActivity.hiragana_data_local.get(n));
        Log.d("real answer", String.valueOf(realAnswer));

        correct1 = (String) button[realAnswer].getText();

        String finalCorrect = correct1;
        button[realAnswer].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (finalCorrect == correct2) {
                    mTextViewCounter = findViewById(R.id.answer_counter);
                    getRandomCharacter();
                    counter++;
                    String correctCounter = Integer.valueOf(counter).toString();
                    mTextViewCounter.setText(correctCounter);
                }

            }
        });

        if (counter == 1){
            Log.d("counter","30X GEDAAN");
            String date;
            Calendar calendar = Calendar.getInstance();
            date = DateFormat.getDateTimeInstance().format(calendar.getTime());
            if (FileExists("mistakes.txt")) {
                Log.d("bestaat", "bestanden bestaan");

//                readLocalFiles();
//                for (int i = 0; i < mistakes_file.size(); i++){
//                    mistakes_local.add(mistakes_file.get(i));
//                }
//                for (int i = 0; i < correct_file.size(); i++){
//                    correct_local.add(correct_file.get(i));
//                }
//                for (int i = 0; i < date_file.size(); i++){
//                    date_local.add(date_file.get(i));
//                }
//
//                mistakes_local.add(String.valueOf(mistakeCounter));
//                correct_local.add(String.valueOf(counter));
//                date_local.add(date);
//
//                for (int i = 0; i < mistakes_local.size(); i++) {
//                    mistakeList += mistakes_local.get(i);
//                    mistakeList += "|";
//                    correctList += correct_local.get(i);
//                    correctList += "|";
//                    dateList += date_local.get(i);
//                    dateList += "|";
//                }

                String oldMistakes = readFromFile("mistakes.txt");
                String oldCorrect = readFromFile("correct.txt");
                String oldDate = readFromFile("date.txt");
                Log.d("oldMistakes", oldMistakes);

                writeToFile(String.valueOf(oldMistakes + mistakeCounter + "|"), "mistakes.txt");
                writeToFile(String.valueOf(oldCorrect + counter + "|"), "correct.txt");
                writeToFile(String.valueOf(oldDate + date + "|"), "date.txt");

                Log.d("test2", String.valueOf(mistakeList));
            }

            else {
                Log.d("bestaat", "bestanden bestaan niet");
                mistakes_local.add(String.valueOf(mistakeCounter));
                writeToFile(String.valueOf(mistakeCounter + "|"), "mistakes.txt");
                writeToFile(String.valueOf(counter + "|"), "correct.txt");
                writeToFile(String.valueOf(date + "|"), "date.txt");
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
                String receivedString = "";
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
        //Log.d("result reader", result);
        return result;
    }

    private void writeToFile(String data, String fileName) {
        try {
            FileOutputStream fileOut = openFileOutput(fileName, MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOut);
            Log.d("writing data to:", fileName + " " + data);
            outputStreamWriter.write(data);
            outputStreamWriter.close();
            //Toast.makeText(getBaseContext(), "File saved successfully!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean FileExists(String fname) {
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

    private void readLocalFiles() {
        String splitMistakeList = readFromFile("mistakes.txt");
        String[] mistakeList = splitMistakeList.split("|");
        String splitCorrectList = readFromFile("correct.txt");
        String[] correctList = splitCorrectList.split("|");
        String splitDateList = readFromFile("date.txt");
        String[] dateList = splitDateList.split("|");

        for (int i = 1; i < mistakeList.length; i++) {
            mistakes_file.add(mistakeList[i]);
            correct_file.add(correctList[i]);
            date_file.add(dateList[i]);
        }
    }

}
