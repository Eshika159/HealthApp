package com.example.adity.healthcareapp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SuggestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);
        Intent intent = getIntent();
        String t = intent.getStringExtra("Disease");


        Toast.makeText(SuggestActivity.this, t, Toast.LENGTH_LONG).show();
    }
}
