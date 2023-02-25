package com.example.huaweic;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import com.example.huaweic.services.LocationTracker;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class PantallaEntrPer extends AppCompatActivity {
    TextView titulo, Total1, Temp, Total2, Temp2;
    public static MediaPlayer mp;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_entr_per);
        titulo=(TextView) findViewById(R.id.ejhoy);
        Total1=(TextView) findViewById(R.id.TiempoTotal);
        Temp=(TextView) findViewById(R.id.TiempoTemp);
        Total2=(TextView) findViewById(R.id.Total2);
        Temp2=(TextView) findViewById(R.id.Temp2);
        rellenar();
    }
    public void regresar(View v){
        Intent siguiente = new Intent(this, Personal.class);
        startActivity(siguiente);
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
    public void empezar(View v){
        sonidostart();
        Intent siguiente = new Intent(this, Descanso.class);
        startActivity(siguiente);
    }
    public void sonidostart(){
        mp=MediaPlayer.create(this,R.raw.music);
        mp.start();
    }
    public  void sonidostop(){
        mp.stop();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void rellenar(){
        String hoy;
        hoy=diaactual();
        dbcreator admin = new dbcreator(getApplicationContext(),"Concurso_Huawei.db",null,1);
        SQLiteDatabase BaseDeDatos=admin.getReadableDatabase();
        Cursor fila=BaseDeDatos.rawQuery("SELECT Dia, Id, KcalPerdida FROM Dias WHERE Dia= '"+hoy+"'",null);
        if(fila.moveToFirst()){
            if(Integer.parseInt(fila.getString(1))==1){
                titulo.setText("Pecho - Espalda");
                Total1.setText("00:15");
                Temp.setText("00:00:15");
                Total2.setText("00:25");
                Temp2.setText("00:00:25");
            }
            if(Integer.parseInt(fila.getString(1))==2){
                titulo.setText("Brazos - Hombros");
                Total1.setText("00:30");
                Temp.setText("00:00:30");
                Total2.setText("00:40");
                Temp2.setText("00:00:40");
            }
            if(Integer.parseInt(fila.getString(1))==3){
                titulo.setText("Abdominales");
                Total1.setText("01:00");
                Temp.setText("00:01:00");
                Total2.setText("00:30");
                Temp2.setText("00:00:30");
            }
            if(Integer.parseInt(fila.getString(1))==4){
                titulo.setText("Piernas");
                Total1.setText("01:00");
                Temp.setText("00:01:00");
                Total2.setText("00:30");
                Temp2.setText("00:00:30");
            }
            if(Integer.parseInt(fila.getString(1))==5){
                titulo.setText("Calistemia");
                Total1.setText("00:40");
                Temp.setText("00:00:40");
                Total2.setText("00:20");
                Temp2.setText("00:00:20");
            }
            BaseDeDatos.close();
        }
    }
}