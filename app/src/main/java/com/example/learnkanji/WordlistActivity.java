package com.example.learnkanji;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;

import java.io.File;
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
        if (!FileExists("kanji.txt")){
            Toast.makeText(WordlistActivity.this, "No internet connection and local files found, connect to internet to get data", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(WordlistActivity.this, SecondActivity.class));
            return;
        }

        for (int i = 0; i <SecondActivity.kanji_data_local.size(); i++){
            mWordlist.add(new WordlistItem(SecondActivity.kanji_data_local.get(i) , SecondActivity.hiragana_data_local.get(i), SecondActivity.romaji_data_local.get(i), SecondActivity.english_data_local.get(i)));
        }
        mWordlistAdapter = new WordlistAdapter(WordlistActivity.this, mWordlist);
        mRecyclerView.setAdapter(mWordlistAdapter);
    }

    public boolean FileExists(String fname) {
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

}
