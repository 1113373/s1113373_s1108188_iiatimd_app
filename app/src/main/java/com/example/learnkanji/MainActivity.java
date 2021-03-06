package com.example.learnkanji;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public TextView emailText;
    public TextView passwordText;
    public Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonContinue = findViewById(R.id.button_continue);
        Button buttonRegister = findViewById(R.id.registerRedirectButton);
        passwordText = findViewById(R.id.loginTextPassword);
        emailText = findViewById(R.id.loginTextEmail);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {
            if (emailText.getText().toString().isEmpty() || passwordText.getText().toString().isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter a username and/or password", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (!emailText.getText().toString().contains("@")) {
                Toast.makeText(MainActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                return;
            }
            postDataUsingVolley(emailText.getText().toString(), passwordText.getText().toString());
        });

        buttonContinue.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, SecondActivity.class)));
        buttonRegister.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, RegisterActivity.class)));
    }

    private void postDataUsingVolley(String email, String password) {
        String url = "http://10.0.2.2:8000/api/login"; //emulator
        //String url = "http://127.0.0.1:8000/api/login"; //normal

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {

            try {
                JSONObject respObj = new JSONObject(response);
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            // method to handle errors.
            Log.d("errorhandling", String.valueOf(error));
            if (error.toString().contains("AuthFailureError")) {
                Toast.makeText(MainActivity.this, "Wrong credentials", Toast.LENGTH_SHORT).show();
            }
            else if (error.toString().contains("NoConnectionError")) {
                Toast.makeText(MainActivity.this, "Failed to connect to the database", Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(MainActivity.this, "Request failed = " + error, Toast.LENGTH_SHORT).show();
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        queue.add(request);
    }


}