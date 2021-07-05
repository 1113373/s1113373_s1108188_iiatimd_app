package com.example.learnkanji;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

public class WordlistActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private WordlistAdapter mWordlistAdapter;
    private ArrayList<WordlistItem> mWordlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordlist);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mWordlist = new ArrayList<>();

        for (int i = 0; i <SecondActivity.kanji_data_local.size(); i++){
            mWordlist.add(new WordlistItem(SecondActivity.kanji_data_local.get(i) , SecondActivity.hiragana_data_local.get(i), SecondActivity.romaji_data_local.get(i), SecondActivity.english_data_local.get(i)));
        }
        mWordlistAdapter = new WordlistAdapter(WordlistActivity.this, mWordlist);
        mRecyclerView.setAdapter(mWordlistAdapter);
    }

}
