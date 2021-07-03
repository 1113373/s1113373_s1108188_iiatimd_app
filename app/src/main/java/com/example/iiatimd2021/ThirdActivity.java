package com.example.iiatimd2021;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ThirdActivity extends android.app.Activity{
    private TextView mTextViewResult;
    private RequestQueue mQueue;
//    ArrayList<String> kanji_data_api = new ArrayList<String>();
//    ArrayList<String> hiragana_data_api = new ArrayList<String>();
//    ArrayList<String> romaji_data_api = new ArrayList<String>();
//    ArrayList<String> english_data_api = new ArrayList<String>();
    ArrayList<String> kanji_data_local = new ArrayList<String>();
    ArrayList<String> hiragana_data_local = new ArrayList<String>();
    ArrayList<String> romaji_data_local = new ArrayList<String>();
    ArrayList<String> english_data_local = new ArrayList<String>();
    boolean hasInternetAcces = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        mTextViewResult = findViewById(R.id.text_view_result);
        Button buttonParse = findViewById(R.id.button_parse);
        checkInternetConnection();
        mQueue = Volley.newRequestQueue(this);

        //CHECK FOR INTERNET ACCES
        if (hasInternetAcces) {
            //GET API DATA IN LOCAL FILE
            //Log.d("internetCheck", "succes");
            Toast.makeText(ThirdActivity.this, "Internet connection found.", Toast.LENGTH_SHORT).show();
            getDataUsingVolley();
            //Log.d("test meteen", kanji_data_local.toString());
            //DELAY TO PROCESS THE API CALL
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            updateLocalFiles();
                            readLocalFiles();
                            Log.d("test 0.25 seconde later", kanji_data_local.toString());
                        }
                    }, 250);

        }
        //NO INTERNET NOTIFICATION
        else {
            Toast.makeText(ThirdActivity.this, "No internet connection found.", Toast.LENGTH_SHORT).show();
            //Log.d("internetcheck", "failed");
            readLocalFiles();
        }

        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("kanji test", kanji_data_local.toString());
                getRandomCharacter();
            }
        });
    }

    private void checkInternetConnection(){
        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        hasInternetAcces = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
        //Log.d("internet test", String.valueOf(hasInternetAcces));

    }



    private void updateLocalFiles() {
        //Log.d("kanji data", kanji_data_api.toString());
//        writeToFile(kanji_data_api.toString(), "kanji.txt");
//        writeToFile(hiragana_data_api.toString(), "hiragana.txt");
//        writeToFile(romaji_data_api.toString(), "romaji.txt");
//        writeToFile(english_data_api.toString(), "english.txt");
        //Log.d()
//        kanji_data_local.add(readFromFile("kanji.txt"));
//        hiragana_data_local.add(readFromFile("hiragana.txt"));
//        romaji_data_local.add(readFromFile("romaji.txt"));
//        english_data_local.add(readFromFile("english.txt"));
        //mTextViewResult.setText(romaji_data_local.get(0).toString());
    }

    private void readLocalFiles() {

        String splitKanjiList = readFromFile("kanji.txt");

        String[] test = splitKanjiList.split("\\.");


        for (int i = 0; i < test.length; i++){

            Log.d("Hier is die gekke array", test[i]);
            kanji_data_local.add(test[i]);
        }


//        kanji_data_local.add(readFromFile("kanji.txt"));
        hiragana_data_local.add(readFromFile("hiragana.txt"));
        romaji_data_local.add(readFromFile("romaji.txt"));
        english_data_local.add(readFromFile("english.txt"));
        //kanji_data_local = kanji_data_local.get(0);



    }

    private void writeToFile(String data, String fileName) {
        try {
            FileOutputStream fileOut = openFileOutput(fileName, MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(fileOut);

            Log.d("data write", data);
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

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receivedString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receivedString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receivedString);
                }

                inputStream.close();
                result = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
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
        Log.d("api call", "api request here");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Data");

                            String kanjiList = "";

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject data = jsonArray.getJSONObject(i);

//                                String kanji = data.getString("kanji");

                                kanjiList += data.getString("kanji");
                                kanjiList += ".";
//                                hiragana_data_api.add(data.getString("hiragana"));
//                                romaji_data_api.add(data.getString("romaji"));
//                                english_data_api.add(data.getString("english"));
//                                mTextViewResult.append(kanji + "\n\n");
                                hasInternetAcces = true;
                            }

                            writeToFile(kanjiList, "kanji.txt");

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

    private void getRandomCharacter() {
        int n = new Random().nextInt(30);
        Log.d("random", String.valueOf(n));
        mTextViewResult.setText(kanji_data_local.get(n));
    }

}
