package com.example.iiatimd2021;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WordlistActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private WordlistAdapter mWordlistAdapter;
    private ArrayList<WordlistItem> mWordlist;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordlist);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mWordlist = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        for (int i = 0; i <SecondActivity.kanji_data_local.size(); i++){
            mWordlist.add(new WordlistItem(SecondActivity.kanji_data_local.get(i) , SecondActivity.hiragana_data_local.get(i), SecondActivity.romaji_data_local.get(i), SecondActivity.english_data_local.get(i)));
        }
        mWordlistAdapter = new WordlistAdapter(WordlistActivity.this, mWordlist);
        mRecyclerView.setAdapter(mWordlistAdapter);
    }

}
