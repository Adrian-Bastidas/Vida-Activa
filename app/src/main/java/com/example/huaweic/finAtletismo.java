package com.example.huaweic;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class finAtletismo extends AppCompatActivity {
    public static TextView T,D,K;
    public static ImageView sello, deporte;
    public static String actividad;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_atletismo);
        T=findViewById(R.id.tiempo);
        D=findViewById(R.id.distancia);
        K=findViewById(R.id.quemada);
        deporte=findViewById(R.id.sello);
        sello=findViewById(R.id.actividad);
        sello.setImageResource(R.drawable.bienhecho);
        actividad=getIntent().getStringExtra("actividad");
        figura(actividad);
        rellenar();
    }
    public void figura(String actividad){
        if(actividad=="Ciclismo"){
            deporte.setImageResource(R.drawable.ciclo);
        }
        else if(actividad=="Atletismo"){
            deporte.setImageResource(R.drawable.atletismo1);
        }
    }
    public void cambiar(View view){
        Intent siguiente = new Intent(this, Personal.class);
        startActivity(siguiente);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void rellenar(){
        int horas=getIntent().getIntExtra("horasm",0);
        int minutos=getIntent().getIntExtra("minutosm",0);
        Double distancia=getIntent().getDoubleExtra("distancia",0.0);
        int tiempo=horas+minutos;
        if(horas==0){
            if(minutos==1){
                T.setText(String.valueOf(tiempo)+" minuto");
            }
            else{
                T.setText(String.valueOf(tiempo)+" minutos");
            }
        }
        else{
            T.setText(String.valueOf(horas)+"."+minutos+" horas");
        }
        D.setText(String.valueOf(distancia)+" km");
        kcal(tiempo, distancia);
        dbcreator admin = new dbcreator(getApplicationContext(),"Concurso_Huawei.db",null,1);
        SQLiteDatabase BaseDeDatos=admin.getReadableDatabase();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void kcal(int tiempo, Double distancia){
        dbcreator admin = new dbcreator(getApplicationContext(),"Concurso_Huawei.db",null,1);
        SQLiteDatabase BaseDeDatos=admin.getReadableDatabase();
        Cursor fila=BaseDeDatos.rawQuery("SELECT UsuNombre, UsuEdad, UsuPeso, UsuTiempo,UsuKcalS, UsuDist FROM Usuarios",null);
        if(fila.moveToFirst()){
            int Peso=Integer.parseInt(fila.getString(2));
            Double temp=Double.parseDouble(fila.getString(3));
            Double kal=Double.parseDouble(fila.getString(4));
            Double dist=Double.parseDouble(fila.getString(5));
            String usu=fila.getString(0);
            Double MET=0.0;
            if(actividad=="Ciclismo"){
                MET=0.0175*10.0*Peso;
            }
            else if(actividad=="Atletismo"){
                MET=0.0175*7.0*Peso;
            }
            Double Kcals=Math.round((MET*tiempo)*100.0)/100.0;
            K.setText(String.valueOf(Kcals)+" Kcal");
            escribir(tiempo,Kcals,distancia, usu);
            escribir1(distancia,Kcals);
            BaseDeDatos.close();
        }
    }
    public void escribir(int tiempo, Double Kcals, Double distancia, String usu){
        String nombre[]={usu};
        dbcreator admin = new dbcreator(getApplicationContext(),"Concurso_Huawei.db",null,1);
        SQLiteDatabase BaseDeDatos=admin.getWritableDatabase();
        ContentValues usuario= new ContentValues();
        String tiempoS=String.valueOf(tiempo);
        String Kcal=String.valueOf(Kcals);
        String dist=String.valueOf(distancia);
        usuario.put("UsuTiempo",tiempoS);
        usuario.put("UsuKcalS",Kcal);
        usuario.put("UsuDist",dist);
        BaseDeDatos.execSQL("UPDATE Usuarios SET UsuTiempo='"+tiempoS+"', UsuKcalS='"+Kcal+"', UsuDist='"+dist+"' WHERE UsuNombre='"+usu+"'");
        //BaseDeDatos.update("Usuarios",usuario,"UsuNombre",nombre);
        BaseDeDatos.close();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void escribir1(Double distancia, Double Kcal){
        dbcreator admin = new dbcreator(getApplicationContext(),"Concurso_Huawei.db",null,1);
        SQLiteDatabase BaseDeDatos=admin.getReadableDatabase();
        String diaA=diaactual();
        Cursor fila=BaseDeDatos.rawQuery("SELECT Dia, Id, KcalPerdida, Dist FROM Dias WHERE Dia= '"+diaA+"'",null);
        if(fila.moveToFirst()){
            Double Kcals=Double.parseDouble(fila.getString(2));
            Double dist=Double.parseDouble(fila.getString(3));
            String kcalsT=String.valueOf(Kcals+Kcal);
            String distT=String.valueOf(distancia+dist);
            ContentValues diaC=new ContentValues();
            diaC.put("KcalPerdida",kcalsT);
            diaC.put("Dist",distT);
            String[] hoy={diaA};
            SQLiteDatabase Base=admin.getWritableDatabase();
            BaseDeDatos.execSQL("UPDATE Dias SET KcalPerdida='"+kcalsT+"', Dist='"+distT+"' WHERE Dia='"+diaA+"'");
            //Base.update("Dia",diaC,"Dia",hoy);
            Base.close();
            BaseDeDatos.close();
        }
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
}