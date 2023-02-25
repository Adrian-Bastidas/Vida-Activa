package com.example.huaweic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.push.HmsMessaging;

public class MainActivity extends AppCompatActivity {

    EditText campo1, campo2, campo3;
    private static final String TAG ="PushDemoLog";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Huaweic);
        try {
            if(verificarBase()){
                Intent siguiente = new Intent(this, Personal.class);
                startActivity(siguiente);
            }

        }catch(Exception e){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            campo1=(EditText) findViewById(R.id.nombre);
            campo2=(EditText) findViewById(R.id.edad);
            campo3=(EditText) findViewById(R.id.peso);
            HmsMessaging.getInstance(this).setAutoInitEnabled(true);
        }
    }
    private void setAutoInitEnabled(final boolean isEnable) {
        if(isEnable){
            // Enable automatic initialization.
            HmsMessaging.getInstance(this).setAutoInitEnabled(true);
        } else {
            // Disable automatic initialization.
            HmsMessaging.getInstance(this).setAutoInitEnabled(false);
        }
    }
    private void sendRegTokenToServer(String token) {
        Log.i(TAG, "sending token to server. token:" + token);
    }
    public boolean verificarBase(){
        boolean resp=false;
        dbcreator admin = new dbcreator(getApplicationContext());
        SQLiteDatabase BaseDeDatos=admin.getReadableDatabase();
        Cursor fila=BaseDeDatos.rawQuery("SELECT UsuNombre FROM Usuarios",null);
        if(fila.moveToFirst()){
            resp=true;
        }
        return resp;
    }
    //Metodo para verificar campos vacios
    public boolean validar(){
        boolean retorno=true;
        String c1=campo1.getText().toString();
        String c2=campo2.getText().toString();
        String c3=campo3.getText().toString();
        if(c1.isEmpty()){
            campo1.setError("Necesito tu nombre");
            retorno=false;
        }
        if(c2.isEmpty()){
            campo2.setError("No puedes saltarte la edad");
            retorno=false;
        }
        if(c3.isEmpty()){
            campo3.setError("Que no te de pena, dime tu peso");
            retorno=false;
        }

        return retorno;
    }
    //Metodo boton aceptar
    public void siguiente(View ver){
        if(validar()){
            Intent siguiente = new Intent(this, PreparacionCasa.class);
            siguiente.putExtra("nombre",campo1.getText().toString());
            siguiente.putExtra("edad",campo2.getText().toString());
            siguiente.putExtra("peso",campo3.getText().toString());
            startActivity(siguiente);
        }
        else{
            Toast.makeText(this,"No pueden quedar campos vacios",Toast.LENGTH_SHORT).show();
        }

    }
}