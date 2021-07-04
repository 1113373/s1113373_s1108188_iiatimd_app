package com.example.iiatimd2021;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;

public class ThirdActivity extends android.app.Activity {
    private TextView mTextViewResult;

    private static int[] answerArray = { R.id.answer1, R.id.answer2, R.id.answer3, R.id.answer4 };
    private Button[] button = new Button[answerArray.length];
    private TextView mTextViewCounter;

    int counter = 0;

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

    }

    private void getRandomCharacter() {
        int realAnswer = new Random().nextInt(answerArray.length);
        int n = new Random().nextInt(SecondActivity.hiragana_data_local.size());
        Log.d("random", String.valueOf(n));
        mTextViewResult.setText(SecondActivity.kanji_data_local.get(n) + "\n" + "\n" + SecondActivity.romaji_data_local.get(n) + "\n" + SecondActivity.english_data_local.get(n));
        //mTextViewResult.setText(hiragana_data_local.get(n));
        String text = "";
        String correct1 = "";
        String correct2 = SecondActivity.hiragana_data_local.get(n);


        for (int i = 0; i < answerArray.length; i++){
            button[i] = findViewById(answerArray[i]);
            int a1 = new Random().nextInt(SecondActivity.hiragana_data_local.size());
            button[i].setText(SecondActivity.hiragana_data_local.get(a1));
            text = (String) button[i].getText();

            if (text == correct2) {
                Log.d("testtest", "ER IS EEN DUBBELE");
                int a2 = new Random().nextInt(SecondActivity.hiragana_data_local.size());

                if (a1 != a2){
                    button[i].setText(SecondActivity.hiragana_data_local.get(a2));
                }
                else button[i].setText(SecondActivity.hiragana_data_local.get(a1+1));

                button[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("Nice", "dit werkt");
                    }
                });
            }
        }

        button[realAnswer].setText(SecondActivity.hiragana_data_local.get(n));

        correct1 = (String) button[realAnswer].getText();

        if (correct1 == correct2){
            button[realAnswer].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mTextViewCounter = findViewById(R.id.answer_counter);
                    getRandomCharacter();
                    counter += 1;
                    String correctCounter = Integer.toString(counter);
                    mTextViewCounter.setText(correctCounter);
                }
            });
        }
    }

}
