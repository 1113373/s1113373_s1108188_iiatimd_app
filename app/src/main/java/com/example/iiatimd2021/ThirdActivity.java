package com.example.iiatimd2021;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        Log.d("random", String.valueOf(n));
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
                    counter += 1;
                    String correctCounter = Integer.valueOf(counter).toString();
                    mTextViewCounter.setText(correctCounter);

                }

            }
        });

    }

}
