package com.example.learnkanji;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class ProgressActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView2;
    private ProgressAdapter mProgressAdapter;
    private ArrayList<ProgressItem> mProgress;
    private ArrayList<String> mistakeArray = new ArrayList<>();
    private ArrayList<String> correctArray = new ArrayList<>();
    private ArrayList<String> dateArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String splitMistakeList = readFromFile("mistakes.txt");
        String[] mistakeList = splitMistakeList.split("\\|");
        mistakeArray.addAll(Arrays.asList(mistakeList));
        String splitCorrectList = readFromFile("correct.txt");
        String[] correctList = splitCorrectList.split("\\|");
        correctArray.addAll(Arrays.asList(correctList));
        String splitDateList = readFromFile("date.txt");
        String[] dateList = splitDateList.split("\\|");
        dateArray.addAll(Arrays.asList(dateList));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        mRecyclerView2 = findViewById(R.id.recycler_view2);
        mRecyclerView2.setHasFixedSize(true);
        mRecyclerView2.setLayoutManager(new LinearLayoutManager(this));

        mProgress = new ArrayList<>();


//        for (int i = 0; i <SecondActivity.kanji_data_local.size(); i++){
//
//        }
        mProgressAdapter = new ProgressAdapter(ProgressActivity.this, mProgress);
        mRecyclerView2.setAdapter(mProgressAdapter);
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
        //Log.d("result reader", result);
        return result;
    }
}
