package com.example.iiatimd2021;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import java.util.Random;

public class ThirdActivity extends android.app.Activity{
    private TextView mTextViewResult;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        mTextViewResult = findViewById(R.id.text_view_result);
        Button buttonParse = findViewById(R.id.button_parse);

        mQueue = Volley.newRequestQueue(this);

        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonParse();
            }
        });
    }

    private void jsonParse() {
        String url = "http://10.0.2.2:8000/api/data";//emulator
        //normal url "http://127.0.0.1:8000/api/data";

        ArrayList<String> kanji_data = new ArrayList<String>();
        ArrayList<String> hiragana_data = new ArrayList<String>();
        ArrayList<String> romaji_data = new ArrayList<String>();
        ArrayList<String> english_data = new ArrayList<String>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject data = jsonArray.getJSONObject(i);

                                String kanji = data.getString("kanji");
                                String hiragana = data.getString("hiragana");
                                String romaji = data.getString("romaji");
                                String english = data.getString("english");

                                kanji_data.add(kanji);
                                hiragana_data.add(hiragana);
                                romaji_data.add(romaji);
                                english_data.add(english);
                                //mTextViewResult.append(kanji + ", " + hiragana + ", " + romaji + ", " + english + "\n\n");

                            }

                            int n = new Random().nextInt(30);
                            Log.d("random", String.valueOf(n));
                            mTextViewResult.append(kanji_data.get(n) + ", " + hiragana_data.get(n) + ", " + romaji_data.get(n) + ", " + english_data.get(n) + "\n");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);

    }

}
