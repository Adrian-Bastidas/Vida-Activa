package com.example.huaweic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class social extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);
    }
    public void regresar(View v){
        Intent siguiente = new Intent(this, Personal.class);
        startActivity(siguiente);
    }
}