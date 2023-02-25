package com.example.huaweic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

public class LoadingDialog {
    public static AlertDialog createDialog(Context context){
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.activity_splash_ulti,null);
        TextView dialogMessage=view.findViewById(R.id.motivacion);
        dialogMessage.setText("Porfavor espere");
        return new AlertDialog.Builder(context)
                .setTitle("Conectando")
                .setView(view)
                .setCancelable(false)
                .create();
    }
}
