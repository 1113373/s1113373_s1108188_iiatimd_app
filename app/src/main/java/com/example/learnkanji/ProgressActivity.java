package com.example.learnkanji;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class ProgressActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView2;
    private ProgressAdapter mProgressAdapter;
    private ArrayList<ProgressItem> mProgress;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        mRecyclerView2 = findViewById(R.id.recycler_view2);
        mRecyclerView2.setHasFixedSize(true);
        mRecyclerView2.setLayoutManager(new LinearLayoutManager(this));

        mProgress = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        for (int i = 0; i <SecondActivity.kanji_data_local.size(); i++){

        }
        mProgressAdapter = new ProgressAdapter(ProgressActivity.this, mProgress);
        mRecyclerView2.setAdapter(mProgressAdapter);
    }

}
