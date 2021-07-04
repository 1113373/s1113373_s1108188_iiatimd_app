package com.example.iiatimd2021;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;

public class ThirdActivity extends android.app.Activity {
    private TextView mTextViewResult;
<<<<<<< HEAD

=======
>>>>>>> development-matt

    private static int[] answerArray = { R.id.answer1, R.id.answer2, R.id.answer3, R.id.answer4 };
    private Button[] button = new Button[answerArray.length];
    private TextView mTextViewCounter;

<<<<<<< HEAD
    private RequestQueue mQueue;
    ArrayList<String> kanji_data_local = new ArrayList<String>();
    ArrayList<String> hiragana_data_local = new ArrayList<String>();
    ArrayList<String> romaji_data_local = new ArrayList<String>();
    ArrayList<String> english_data_local = new ArrayList<String>();
    boolean hasInternetAcces = false;

    int counter = 0;


=======
    int counter = 0;
>>>>>>> development-matt

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        mTextViewResult = findViewById(R.id.text_view_result);
        Button buttonParse = findViewById(R.id.button_parse);

        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("kanji test", kanji_data_local.toString());
                getRandomCharacter();
            }
        });

<<<<<<< HEAD






    }



    private void checkInternetConnection(){
        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        hasInternetAcces = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
        //Log.d("internet test", String.valueOf(hasInternetAcces));
    }

    private void readLocalFiles() {
        String splitKanjiList = readFromFile("kanji.txt");
        String[] kanjiList = splitKanjiList.split("\\.");
        String splitHiraganaList = readFromFile("hiragana.txt");
        String[] hiraganaList = splitHiraganaList.split("\\.");
        String splitRomajiList = readFromFile("romaji.txt");
        String[] romajiList = splitRomajiList.split("\\.");
        String splitEnglishList = readFromFile("english.txt");
        String[] englishList = splitEnglishList.split("\\.");

        for (int i = 0; i < kanjiList.length; i++){
            kanji_data_local.add(kanjiList[i]);
            hiragana_data_local.add(hiraganaList[i]);
            romaji_data_local.add(romajiList[i]);
            english_data_local.add(englishList[i]);
        }
    }

    private void writeToFile(String data, String fileName) {
        try {
            FileOutputStream fileOut = openFileOutput(fileName, MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(fileOut);
            Log.d("writing data to:", fileName + " " + data);
            outputStreamWriter.write(data);
            outputStreamWriter.close();
            //Toast.makeText(getBaseContext(), "File saved successfully!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
=======
>>>>>>> development-matt
    }

    private void getRandomCharacter() {
        int n = new Random().nextInt(30);
        int realAnswer = new Random().nextInt(4);
        Log.d("random", String.valueOf(n));
        String correct1 = "";
        String correct2 = SecondActivity.hiragana_data_local.get(n);
        int a2 = new Random().nextInt(SecondActivity.hiragana_data_local.size());

        mTextViewResult.setText(SecondActivity.kanji_data_local.get(n) + "\n" + "\n" + SecondActivity.romaji_data_local.get(n) + "\n" + SecondActivity.english_data_local.get(n));
        //mTextViewResult.setText(hiragana_data_local.get(n));


        for (int i = 0; i < answerArray.length; i++){
            button[i] = findViewById(answerArray[i]);
            int a1 = new Random().nextInt(30);

<<<<<<< HEAD
            button[i].setText(hiragana_data_local.get(a1));

            button[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Nice", "dit werkt");
                }
            });
=======
            button[i].setText(SecondActivity.hiragana_data_local.get(a1));
>>>>>>> development-matt

            button[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Nice", "dit werkt");
                }
            });

<<<<<<< HEAD
=======
            if (SecondActivity.hiragana_data_local.get(a1) == correct2){
                if (a1 != a2){
                    button[i].setText(SecondActivity.hiragana_data_local.get(a2));
                }
                else button[i].setText(SecondActivity.hiragana_data_local.get(a1+1));
            }
>>>>>>> development-matt

        }
        button[realAnswer].setText(SecondActivity.hiragana_data_local.get(n));
        Log.d("real answer", String.valueOf(realAnswer));

<<<<<<< HEAD
        }
        button[realAnswer].setText(hiragana_data_local.get(n));
        Log.d("real answer", String.valueOf(realAnswer));
=======
       correct1 = (String) button[realAnswer].getText();
>>>>>>> development-matt

        String finalCorrect = correct1;
        button[realAnswer].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

<<<<<<< HEAD
        button[realAnswer].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (correct1 == correct2) {
=======
                if (finalCorrect == correct2) {
>>>>>>> development-matt
                    mTextViewCounter = findViewById(R.id.answer_counter);
                    getRandomCharacter();
                    counter += 1;
                    String correctCounter = Integer.valueOf(counter).toString();
                    mTextViewCounter.setText(correctCounter);
                }

            }
        });

    }




}
