package com.example.huaweic;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Personal extends AppCompatActivity {
    private static final String TAG = "Personal";
    ImageView entr1, entr2;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        entr1=findViewById(R.id.enterper);
        entr2=findViewById(R.id.prohibir);
        imagen();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void imagen(){
        dbcreator admin = new dbcreator(getApplicationContext(),"Concurso_Huawei.db",null,1);
        SQLiteDatabase BaseDeDatos=admin.getReadableDatabase();
        String hoy=diaactual();
        Cursor fila=BaseDeDatos.rawQuery("SELECT Dia, Id, KcalPerdida, Dist FROM Dias WHERE Dia='"+hoy+"'",null);
        if(fila.moveToFirst()){
            entr1.setVisibility(View.VISIBLE);
            entr2.setVisibility(View.INVISIBLE);
        }
        else{
            entr1.setVisibility(View.INVISIBLE);
            entr2.setVisibility(View.VISIBLE);
        }
        BaseDeDatos.close();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String diaactual(){
        String dia, resp=null;
        Date date = new Date();
        ZoneId timeZone = ZoneId.systemDefault();
        LocalDate getLocalDate = date.toInstant().atZone(timeZone).toLocalDate();
        dia=getLocalDate.getDayOfWeek().toString();
        if(dia=="MONDAY"){
            resp="Lunes";
        }if(dia=="TUESDAY"){
            resp="Martes";
        }if(dia=="WEDNESDAY"){
            resp="Miercoles";
        }if(dia=="THURSDAY"){
            resp="Jueves";
        }if(dia=="FRIDAY"){
            resp="Viernes";
        }if(dia=="SATURDAY"){
            resp="Sabado";
        }if(dia=="SUNDAY"){
            resp="Domingo";
        }
        return resp;
    }
    public void cambio(View ver){

        Intent siguiente = new Intent(this, MenuPrincipal.class);
        startActivity(siguiente);
    }
    public void Personal(View ver) {
        Intent siguiente = new Intent(this, PantallaEntrPer.class);
        startActivity(siguiente);
    }
    public void carrera1(View ver){
        if(ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            Intent siguiente = new Intent(this, Prepararatletismo.class);
            siguiente.putExtra("actividad","Ciclismo");
            startActivity(siguiente);
        }
        else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Log.i(TAG, "sdk >= 23 M");
                // Check whether your app has the specified permission and whether the app operation corresponding to the permission is allowed.
                if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // Request permissions for your app.
                    String[] strings = {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION};
                    // Request permissions.
                    ActivityCompat.requestPermissions(this, strings, 1);
                }
            }
        }
    }
    public void carrera(View ver){
        if(ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            Intent siguiente = new Intent(this, Prepararatletismo.class);
            siguiente.putExtra("actividad","Atletismo");
            startActivity(siguiente);
        }
        else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Log.i(TAG, "sdk >= 23 M");
                // Check whether your app has the specified permission and whether the app operation corresponding to the permission is allowed.
                if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // Request permissions for your app.
                    String[] strings = {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION};
                    // Request permissions.
                    ActivityCompat.requestPermissions(this, strings, 1);
                }
            }
        }

    }
}