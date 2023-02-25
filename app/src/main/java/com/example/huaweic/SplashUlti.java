package com.example.huaweic;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashUlti extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_ulti);
        Intent siguiente = new Intent(this,  MainActivity.class);
        startActivity(siguiente);
    }
}