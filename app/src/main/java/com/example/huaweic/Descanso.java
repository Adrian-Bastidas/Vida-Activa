package com.example.huaweic;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import pl.droidsonroids.gif.GifImageView;

public class Descanso extends AppCompatActivity {
    TextView titulo, ejercicio, TiempoT, TiempoTemp;
    GifImageView video;
    SoundPool sp, sp1;
    ProgressBar temp;
    MediaPlayer mp;
    int o=100;
    int sonido;
    int e;
    int[] vidres;
    int numres;
    int[] res={R.drawable.primero,R.drawable.segundo,R.drawable.tercero,R.drawable.cuarto,R.drawable.quinto,R.drawable.sexto,R.drawable.septimo};
    int[] res1={R.drawable.fondos,R.drawable.kurl,R.drawable.flexioneselevadas,R.drawable.flexiondiamante,R.drawable.elevacionlateral,R.drawable.flexioncontoqueahombro};
    int[] res2={R.drawable.abdominlarodillaapecho,R.drawable.codoarodilla,R.drawable.abdominalcrunch,R.drawable.tabla,R.drawable.abdominaltoquetalon,R.drawable.skipping};
    int[] res3={R.drawable.sentadilla,R.drawable.zancadas,R.drawable.sentadillaexplosiva,R.drawable.elevaciondetalones,R.drawable.sentadillaconelevaciondepierna,R.drawable.skippingbajo};
    int[] res4={R.drawable.burpee,R.drawable.flexiondiamante,R.drawable.tabla,R.drawable.fondos,R.drawable.planchaconcambiosdeapoyo,R.drawable.planchaconelevaciondebrazo};
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descanso);
        titulo= findViewById(R.id.Titulo);
        ejercicio= findViewById(R.id.ejercicio);
        video= findViewById(R.id.gif);
        TiempoT= findViewById(R.id.TiempoTotal);
        TiempoTemp= findViewById(R.id.TiempoTemp);
        sp1=new SoundPool(1, AudioManager.STREAM_MUSIC,1);
        sp=new SoundPool(1, AudioManager.STREAM_MUSIC,1);
        sonido= sp.load(this,R.raw.beeeep,1);
        temp=findViewById(R.id.progressBar);
        rellenar();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String diaactual(){
        String dia, resp=null;
        Date date = new Date();
        ZoneId timeZone = ZoneId.systemDefault();
        LocalDate getLocalDate = date.toInstant().atZone(timeZone).toLocalDate();
        dia=getLocalDate.getDayOfWeek().toString();
        if(dia.equals("MONDAY")){
            resp="Lunes";
        }if(dia.equals("TUESDAY")){
            resp="Martes";
        }if(dia.equals("WEDNESDAY")){
            resp="Miercoles";
        }if(dia.equals("THURSDAY")){
            resp="Jueves";
        }if(dia.equals("FRIDAY")){
            resp="Viernes";
        }if(dia.equals("SATURDAY")){
            resp="Sabado";
        }if(dia.equals("SUNDAY")){
            resp="Domingo";
        }
        return resp;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void rellenar(){
        String hoy;
        int id=0;
        hoy=diaactual();
        dbcreator admin = new dbcreator(getApplicationContext(),"Concurso_Huawei.db",null,1);
        SQLiteDatabase BaseDeDatos=admin.getReadableDatabase();
        Cursor fila=BaseDeDatos.rawQuery("SELECT Dia, Id, KcalPerdida FROM Dias WHERE Dia= '"+hoy+"'",null);
        int contD = getIntent().getIntExtra("contD",0);
        if(fila.moveToFirst()){
            id=Integer.parseInt(fila.getString(1));
            if(contD<4){
                completar(id, BaseDeDatos, contD);
            }
            else{
                Intent siguiente = new Intent(this, finEjercicio.class);
                startActivity(siguiente);
            }
            BaseDeDatos.close();
        }
    }
    public void colocarvideo(){
        video.setImageResource(vidres[numres]);
    }
    public void repetir(View v){
        colocarvideo();
    }
    public void completar(int id, SQLiteDatabase BaseDeDatos, int contD){
        if(id==1){
            int contE = getIntent().getIntExtra("contE",1);
            e=contE;
            temp.setMax(25);
            o=25;
            Cursor fila1=BaseDeDatos.rawQuery("SELECT EjNombre, EjTiempo, Ejnum, EjMET, EjFuncionalidad, EjDia FROM Ejercicios WHERE EjDia= '"+id+"' AND Ejnum= '"+contE+"'",null);
            if(fila1.moveToFirst()){
                ejercicio.setText(fila1.getString(0));
                TiempoT.setText("00:25");
                vidres=res;
                numres=contE-1;
                colocarvideo();
                //video.setImageResource(res[contE-1]);
                new CountDownTimer(25000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        o-=1;
                        temp.setProgress(o);
                        TiempoTemp.setText("00:" +"00:"+ millisUntilFinished / 1000);
                    }
                    public void onFinish() {
                        sp.play(sonido,1,1,1,0,0);
                        Intent siguiente = new Intent(getApplicationContext(), Ejercicio.class);
                        siguiente.putExtra("contE",contE);
                        siguiente.putExtra("id",id);
                        siguiente.putExtra("contD",contD);
                        startActivity(siguiente);
                    }
                }.start();
            }
        }
        if(id==2){
            int contE = getIntent().getIntExtra("contE",8);
            e=contE;
            temp.setMax(40);
            o=40;
            Cursor fila1=BaseDeDatos.rawQuery("SELECT EjNombre, EjTiempo, Ejnum, EjMET, EjFuncionalidad, EjDia FROM Ejercicios WHERE EjDia= '"+id+"' AND Ejnum= '"+contE+"'",null);
            if(fila1.moveToFirst()){
                ejercicio.setText(fila1.getString(0));
                TiempoT.setText("00:40");
                vidres=res1;
                numres=contE-8;
                colocarvideo();
                //video.setImageResource(res1[contE-8]);
                new CountDownTimer(40000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        o-=1;
                        temp.setProgress(o);
                        TiempoTemp.setText("00:" +"00:"+ millisUntilFinished / 1000);
                    }
                    public void onFinish() {
                        sp.play(sonido,1,1,1,0,0);
                        Intent siguiente = new Intent(getApplicationContext(), Ejercicio.class);
                        siguiente.putExtra("contE",contE);
                        siguiente.putExtra("id",id);
                        siguiente.putExtra("contD",contD);
                        startActivity(siguiente);
                    }
                }.start();
            }
        }
        if(id==3){
            int contE = getIntent().getIntExtra("contE",14);
            e=contE;
            temp.setMax(30);
            o=30;
            Cursor fila1=BaseDeDatos.rawQuery("SELECT EjNombre, EjTiempo, Ejnum, EjMET, EjFuncionalidad, EjDia FROM Ejercicios WHERE EjDia= '"+id+"' AND Ejnum= '"+contE+"'",null);
            if(fila1.moveToFirst()){
                ejercicio.setText(fila1.getString(0));
                TiempoT.setText("00:30");
                vidres=res2;
                numres=contE-14;
                colocarvideo();
                //video.setImageResource(res2[contE-14]);
                new CountDownTimer(30000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        o-=1;
                        temp.setProgress(o);
                        TiempoTemp.setText("00:" +"00:"+ millisUntilFinished / 1000);
                    }
                    public void onFinish() {
                        sp.play(sonido,1,1,1,0,0);
                        Intent siguiente = new Intent(getApplicationContext(), Ejercicio.class);
                        siguiente.putExtra("contE",contE);
                        siguiente.putExtra("id",id);
                        siguiente.putExtra("contD",contD);
                        startActivity(siguiente);
                    }
                }.start();
            }
        }
        if(id==4){
            int contE = getIntent().getIntExtra("contE",20);
            e=contE;
            temp.setMax(30);
            o=30;
            Cursor fila1=BaseDeDatos.rawQuery("SELECT EjNombre, EjTiempo, Ejnum, EjMET, EjFuncionalidad, EjDia FROM Ejercicios WHERE EjDia= '"+id+"' AND Ejnum= '"+contE+"'",null);
            if(fila1.moveToFirst()){
                ejercicio.setText(fila1.getString(0));
                TiempoT.setText("00:30");
                vidres=res3;
                numres=contE-20;
                colocarvideo();
                //video.setImageResource(res3[contE-20]);
                new CountDownTimer(30000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        o-=1;
                        temp.setProgress(o);
                        TiempoTemp.setText("00:" +"00:"+ millisUntilFinished / 1000);
                    }
                    public void onFinish() {
                        sp.play(sonido,1,1,1,0,0);
                        Intent siguiente = new Intent(getApplicationContext(), Ejercicio.class);
                        siguiente.putExtra("contE",contE);
                        siguiente.putExtra("id",id);
                        siguiente.putExtra("contD",contD);
                        startActivity(siguiente);
                    }
                }.start();
            }
        }
        if(id==5){
            int contE = getIntent().getIntExtra("contE",26);
            e=contE;
            temp.setMax(20);
            o=20;
            Cursor fila1=BaseDeDatos.rawQuery("SELECT EjNombre, EjTiempo, Ejnum, EjMET, EjFuncionalidad, EjDia FROM Ejercicios WHERE EjDia= '"+id+"' AND Ejnum= '"+contE+"'",null);
            if(fila1.moveToFirst()){
                ejercicio.setText(fila1.getString(0));
                TiempoT.setText("00:20");
                vidres=res4;
                numres=contE-26;
                colocarvideo();
                //video.setImageResource(res4[contE-26]);
                new CountDownTimer(20000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        o-=1;
                        temp.setProgress(o);
                        TiempoTemp.setText("00:" +"00:"+ millisUntilFinished / 1000);
                    }
                    public void onFinish() {
                        sp.play(sonido,1,1,1,0,0);
                        Intent siguiente = new Intent(getApplicationContext(), Ejercicio.class);
                        siguiente.putExtra("contE",contE);
                        siguiente.putExtra("id",id);
                        siguiente.putExtra("contD",contD);
                        startActivity(siguiente);
                    }
                }.start();
            }
        }
    }
}
