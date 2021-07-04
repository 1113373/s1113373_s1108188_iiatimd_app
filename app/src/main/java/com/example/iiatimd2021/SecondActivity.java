package com.example.iiatimd2021;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class SecondActivity extends android.app.Activity {
    private RequestQueue mQueue;
    public static ArrayList<String> kanji_data_local = new ArrayList<String>();
    public static ArrayList<String> hiragana_data_local = new ArrayList<String>();
    public static ArrayList<String> romaji_data_local = new ArrayList<String>();
    public static ArrayList<String> english_data_local = new ArrayList<String>();
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
            //Log.d("internetCheck", "succes");
            //Toast.makeText(SecondActivity.this, "Internet connection found.", Toast.LENGTH_SHORT).show();
            getDataUsingVolley();
            //DELAY TO PROCESS THE API CALL
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            //updateLocalFiles();
                            readLocalFiles();
                        }
                    }, 250);

        }
        //NO INTERNET NOTIFICATION
        else {
            Toast.makeText(SecondActivity.this, "No internet connection found.", Toast.LENGTH_SHORT).show();
            //Log.d("internetcheck", "failed");
            readLocalFiles();
        }

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //jsonParse();
                startActivity(new Intent(SecondActivity.this, ThirdActivity.class));
            }
        });

        progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //jsonParse();
                startActivity(new Intent(SecondActivity.this, ThirdActivity.class));
            }
        });

        wordlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //jsonParse();
                startActivity(new Intent(SecondActivity.this, WordlistActivity.class));
            }
        });
    }

    private void checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        hasInternetAcces = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
        //Log.d("internet test", String.valueOf(hasInternetAcces));
    }

    private void readLocalFiles() {
        String splitKanjiList = readFromFile("kanji.txt");
        String[] kanjiList = splitKanjiList.split("\\.");
        String splitHiraganaList = readFromFile("hiragana.txt");
        String[] hiraganaList = splitHiraganaList.split("\\.");
        String splitRomajiList = readFromFile("romaji.txt");
        String[] romajiList = splitRomajiList.split("\\.");
        String splitEnglishList = readFromFile("english.txt");
        String[] englishList = splitEnglishList.split("\\.");

        for (int i = 0; i < kanjiList.length; i++) {
            kanji_data_local.add(kanjiList[i]);
            hiragana_data_local.add(hiraganaList[i]);
            romaji_data_local.add(romajiList[i]);
            english_data_local.add(englishList[i]);
        }
    }

    private void writeToFile(String data, String fileName) {
        try {
            FileOutputStream fileOut = openFileOutput(fileName, MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOut);
            Log.d("writing data to:", fileName + " " + data);
            outputStreamWriter.write(data);
            outputStreamWriter.close();
            //Toast.makeText(getBaseContext(), "File saved successfully!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String readFromFile(String fileName) {

        String result = "";

        try {
            InputStream inputStream = openFileInput(fileName);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receivedString = "";
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

    private void getDataUsingVolley() {
        String url = "http://10.0.2.2:8000/api/data";//emulator
        //normal url "http://127.0.0.1:8000/api/data";
        Log.d("api call", "api request called");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Data");

                            String kanjiList = "";
                            String hiraganaList = "";
                            String romajiList = "";
                            String englishList = "";

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject data = jsonArray.getJSONObject(i);
                                kanjiList += data.getString("kanji");
                                kanjiList += ".";
                                hiraganaList += data.getString("hiragana");
                                hiraganaList += ".";
                                romajiList += data.getString("romaji");
                                romajiList += ".";
                                englishList += data.getString("english");
                                englishList += ".";

                            }

                            writeToFile(kanjiList, "kanji.txt");
                            writeToFile(hiraganaList, "hiragana.txt");
                            writeToFile(romajiList, "romaji.txt");
                            writeToFile(englishList, "english.txt");
                            hasInternetAcces = true;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
}
