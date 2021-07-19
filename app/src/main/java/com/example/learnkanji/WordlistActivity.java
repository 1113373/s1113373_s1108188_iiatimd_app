package com.example.learnkanji;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.example.learnkanji.SecondActivity.arrayList;

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

        Log.d("testarray", String.valueOf(SecondActivity.arrayList));


        mWordlistAdapter = new WordlistAdapter(WordlistActivity.this, mWordlist);
        mRecyclerView.setAdapter(mWordlistAdapter);

        fetchFromRoom();
    }

    public boolean FileExists(String fname) {
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

    private void fetchFromRoom(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Data> dataWords = DatabaseClient.getInstance(WordlistActivity.this).getAppDatabase().wordDAO().getAll();
                SecondActivity.arrayList.clear();
                for (Data data: dataWords) {
                    WordlistItem wordlistItem = new WordlistItem(data.getId(), data.getKanji(), data.getHiragana(), data.getRomaji(), data.getEnglish());
                    SecondActivity.arrayList.add(wordlistItem);
                    Log.d("testdbWL", String.valueOf(data.getKanji()));

                    mWordlist.add(new WordlistItem(data.getId(),data.getKanji(),data.getHiragana(),data.getRomaji(),data.getEnglish()));

                }

            }
        });
        thread.start();
    }

}
