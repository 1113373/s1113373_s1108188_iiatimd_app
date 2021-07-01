package com.example.iiatimd2021;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends android.app.Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Button start = findViewById(R.id.cardView2);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //jsonParse();
                startActivity(new Intent(SecondActivity.this, ThirdActivity.class));
            }
        });
    }
}
