package com.example.huaweic;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class finEjercicio extends AppCompatActivity {
    TextView TiempoT, Kcal, TiempoU, TiempoS, KcalS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_ejercicio);
        TiempoT=findViewById(R.id.tiempoT);
        Kcal=findViewById(R.id.kcal);
        TiempoU=findViewById(R.id.TiempoS);
        TiempoS=findViewById(R.id.TiempoS);
        PantallaEntrPer p=new PantallaEntrPer();
        p.sonidostop();
        KcalS=findViewById(R.id.kcalS);
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
    public void TiemposE(int id){
        if(id==1) {
            int TE = 15;
            TiempoU.setText("Tiempo por ejercicio: "+String.valueOf(TE)+" segundos");
            int TTT=(TE*7)*4;
            int TT=(TTT*1)/60;
            TiempoT.setText("Tiempo Total: "+TT+" minutos");
        }
        if(id==2) {
            int TE = 30;
            TiempoU.setText("Tiempo por ejercicio: "+String.valueOf(TE)+" segundos");
            int TTT=(TE*6)*4;
            int TT=(TTT*1)/60;
            TiempoT.setText("Tiempo Total: "+TT+" minutos");
        }
        if(id==3) {
            int TE = 1;
            TiempoU.setText("Tiempo por ejercicio: "+String.valueOf(TE)+" minuto");
            int TTT=(60*6)*4;
            int TT=(TTT*1)/60;
            TiempoT.setText("Tiempo Total: "+TT+" minutos");
        }
        if(id==4) {
            int TE = 1;
            TiempoU.setText("Tiempo por ejercicio: "+String.valueOf(TE)+" minuto");
            int TTT=(60*6)*4;
            int TT=(TTT*1)/60;
            TiempoT.setText("Tiempo Total: "+TT+" minutos");
        }
        if(id==5) {
            int TE = 1;
            TiempoU.setText("Tiempo por ejercicio: "+String.valueOf(TE)+" minuto");
            int TTT=(60*5)*4;
            int TT=(TTT*1)/60;
            TiempoT.setText("Tiempo Total: "+TT+" minutos");
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void rellenar(){
        dbcreator admin = new dbcreator(getApplicationContext(),"Concurso_Huawei.db",null,1);
        SQLiteDatabase BaseDeDatos=admin.getReadableDatabase();
        String hoy=diaactual();
        Cursor fila=BaseDeDatos.rawQuery("SELECT Dia, Id, KcalPerdida FROM Dias WHERE Dia= '"+hoy+"'",null);
        if(fila.moveToFirst()){
            Kcal.setText("Kcal quemadas: "+fila.getString(2));
            int id=Integer.parseInt(fila.getString(1));
            TiemposE(id);
            Cursor fila1=BaseDeDatos.rawQuery("Select UsuNombre, UsuEdad, UsuPeso, UsuTiempo, UsuKcalS FROM Usuarios",null);
            if(fila1.moveToFirst()){
                int tempT=(Integer.parseInt(fila1.getString(3))*1)/60;
                TiempoS.setText("Tiempo Semanal: "+tempT+" minutos");
                KcalS.setText("Kcal semanales: "+fila.getString(4));
                BaseDeDatos.close();
            }
        }
    }
}