package com.example.learnkanji;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SecondActivity extends android.app.Activity {
    private RequestQueue mQueue;
    public static ArrayList<String> kanji_data_local = new ArrayList<>();
    public static ArrayList<String> hiragana_data_local = new ArrayList<>();
    public static ArrayList<String> romaji_data_local = new ArrayList<>();
    public static ArrayList<String> english_data_local = new ArrayList<>();
    public static ArrayList<WordlistItem> arrayList = new ArrayList<>();
    List<WordlistItem> words;
    private
    boolean hasInternetAcces = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Button start = findViewById(R.id.startButton);
        Button progress = findViewById(R.id.progressButton);
        Button wordlist = findViewById(R.id.wordlistButton);
        checkInternetConnection();
        mQueue = Volley.newRequestQueue(this);

        //CHECK FOR INTERNET ACCES
        if (hasInternetAcces) {
            //GET API DATA IN LOCAL FILE
            Log.d("internetCheck", "success");

            getDataUsingVolley();

        }

        else {

                fetchFromRoom();
                Toast.makeText(SecondActivity.this, "No internet connection and local files found, connect to internet to get data", Toast.LENGTH_SHORT).show();

        }

        start.setOnClickListener(view -> {
            startActivity(new Intent(SecondActivity.this, ThirdActivity.class));
        });

        progress.setOnClickListener(view -> {
            startActivity(new Intent(SecondActivity.this, ProgressActivity.class));
        });

        wordlist.setOnClickListener(view -> {
            startActivity(new Intent(SecondActivity.this, WordlistActivity.class));
        });




    }

    private void checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        hasInternetAcces = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
        Log.d("internet test", String.valueOf(hasInternetAcces));
    }

    private void fetchFromRoom(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Data> dataWords = DatabaseClient.getInstance(SecondActivity.this).getAppDatabase().wordDAO().getAll();
                arrayList.clear();
                for (Data data: dataWords) {
                    WordlistItem wordlistItem = new WordlistItem(data.getId(), data.getKanji(), data.getHiragana(), data.getRomaji(), data.getEnglish());
                    arrayList.add(wordlistItem);


                }

            }
        });
        thread.start();
    }

    private void getDataUsingVolley() {
        String url = "http://10.0.2.2:8000/api/data";//emulator
        //String url = "http://127.0.0.1:8000/api/data";
        Log.d("api call", "api request called");


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,

                response -> {
                    try {

                        JSONArray jsonArray = response.getJSONArray("Data");
                        words = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<WordlistItem>>() {}.getType());
                        
                        arrayList.clear();
                        arrayList.addAll(words);

                        saveTask();

                        hasInternetAcces = true;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                hasInternetAcces = false;
            }
        });
        mQueue.add(request);
    }

        private void saveTask() {


            class SaveTask extends AsyncTask<Void, Void, Void> {

                @Override
                protected Void doInBackground(Void... voids) {

                    //creating a task

                    for (int i = 0; i < words.size(); i++){
                        Data data = new Data();
                        data.setId(words.get(i).getId());
                        data.setKanji(words.get(i).getKanji());
                        data.setHiragana(words.get(i).getHiragana());
                        data.setRomaji(words.get(i).getRomaji());
                        data.setEnglish(words.get(i).getEnglish());

                        DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().wordDAO().InsertWord(data);
                    }


                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
                }
            }

            SaveTask st = new SaveTask();
            st.execute();
        }




}
