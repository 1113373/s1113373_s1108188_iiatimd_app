package com.example.iiatimd2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    public TextView emailText;
    public TextView passwordText;
    public Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button buttonContinue = findViewById(R.id.button_continue);
        passwordText = findViewById(R.id.registerTextPassword);
        emailText = findViewById(R.id.registerTextEmail);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailText.getText().toString().isEmpty() || passwordText.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter an email and/or password", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!emailText.getText().toString().contains("@")) {
                    Toast.makeText(RegisterActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                    return;
                }
                postDataUsingVolley(emailText.getText().toString(), passwordText.getText().toString());
            }
        });

        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });
    }

    private void postDataUsingVolley(String email, String password) {
        String url = "http://10.0.2.2:8000/api/register"; //emulator
        //String url = "http://127.0.0.1:8000/api/register"; //normal

        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject respObj = new JSONObject(response);
                    startActivity(new Intent(RegisterActivity.this, ThirdActivity.class));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Log.d("errorhandling", String.valueOf(error));
                if (error.toString().contains("AuthFailureError")) {
                    Toast.makeText(RegisterActivity.this, "Wrong credentials", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(RegisterActivity.this, "Request failed = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };
        queue.add(request);
    }


}