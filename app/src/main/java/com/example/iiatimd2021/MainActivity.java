package com.example.iiatimd2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonContinue = findViewById(R.id.button_continue);
        TextView passwordText = findViewById(R.id.LoginTextPassword);
        TextView loginText = findViewById(R.id.LoginTextEmail);
//        Button loginButton = findViewById(R.id.);




        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //jsonParse();
                startActivity(new Intent(MainActivity.this, ThirdActivity.class));
            }
        });
    }


}