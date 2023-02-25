package com.example.huaweic;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.HwAds;
import com.huawei.hms.ads.banner.BannerView;

public class Ejercicio extends AppCompatActivity {

    TextView TiempoTotal, TiempoTemp, Titulo, tit;
    private BannerView bannerView;
    int id=0;
    String total=null;
    Double kcal=0.0;
    int finalContE=0;
    ProgressBar temp;
    SoundPool sp;
    int sonido;
    int o=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio);
        HwAds.init(this);
        bannerView = findViewById(R.id.hw_banner_view);
        // Create an ad request to load an ad.
        AdParam adParam = new AdParam.Builder().build();
        bannerView.loadAd(adParam);
        TiempoTotal=findViewById(R.id.TiempoTotal);
        TiempoTemp=findViewById(R.id.TiempoTemp);
        Titulo=findViewById(R.id.titulo);
        id=getIntent().getIntExtra("id",5);
        tit=findViewById(R.id.tit);
        temp=findViewById(R.id.progressBar);
        sp=new SoundPool(1, AudioManager.STREAM_MUSIC,1);
        sonido= sp.load(this,R.raw.beeeep,1);
        rellenar();
    }
    public void continuar(int contE, int contD){
        escribir();
        Intent siguiente = new Intent(getApplicationContext(), Descanso.class);
        siguiente.putExtra("id",id);
        if(contE==7||contE==14||contE==20||contE==26||contE==32){
            contD++;
            if(id==1){
                contE=1;
            }
            if(id==2){
                contE=8;
            }
            if(id==3){
                contE=14;
            }
            if(id==4){
                contE=20;
            }
            if(id==5){
                contE=26;
            }
            siguiente.putExtra("contE", contE);
            siguiente.putExtra("contD",contD);
        }else{
            siguiente.putExtra("contE", finalContE);
            siguiente.putExtra("contD",contD);
        }
        startActivity(siguiente);
    }
    public void rellenar(){
        dbcreator admin = new dbcreator(getApplicationContext(),"Concurso_Huawei.db",null,1);
        int contE = getIntent().getIntExtra("contE", 5);
        SQLiteDatabase BaseDeDatos=admin.getReadableDatabase();
        Cursor fila=BaseDeDatos.rawQuery("SELECT EjNombre, EjTiempo, Ejnum, EjMET, EjFuncionalidad, EjDia FROM Ejercicios WHERE EjDia= '"+id+"' AND Ejnum= '"+ contE +"'",null);
        if(fila.moveToFirst()){
            Titulo.setText(fila.getString(0));
            total=fila.getString(1);
            int contD = getIntent().getIntExtra("contD", 5);
            if(contD==0){
                tit.setText("Primera Ronda");
            }
            if(contD==1){
                tit.setText("Segunda Ronda");
            }
            if(contD==2){
                tit.setText("Tercera Ronda");
            }
            if(contD==3){
                tit.setText("Cuarta Ronda");
            }
            TiempoTotal.setText("00:"+fila.getString(1));
            Double MET =Double.parseDouble(fila.getString(3));
            int tiempo=Integer.parseInt(fila.getString(1));
            temp.setMax(tiempo);
            o=tiempo;
            Double MET1=(tiempo*MET)/60;
            Cursor fila1=BaseDeDatos.rawQuery("SELECT UsuNombre, UsuEdad, UsuPeso, UsuTiempo, UsuKcals FROM Usuarios",null);
            if(fila1.moveToFirst()){
                String peso=fila1.getString(2);
                kcal=0.0175*MET1*Integer.parseInt(peso);
                int segundos=(Integer.parseInt(fila.getString(1))*1000);
                contE++;
                finalContE = contE;
                BaseDeDatos.close();
                int finalContE1 = contE;
                new CountDownTimer(segundos, 1000) {
                    public void onTick(long millisUntilFinished) {
                        o-=1;
                        temp.setProgress(o);
                        TiempoTemp.setText("00:" +"00:"+ millisUntilFinished / 1000);
                    }
                    public void onFinish() {
                        sp.play(sonido,1,1,1,0,0);
                        continuar(finalContE1, contD);
                    }
                }.start();
            }
        }
    }
    public void escribir(){
        Double Kanterior=Double.parseDouble(traerkcaldia());
        Double regreso=Kanterior+kcal;
        ContentValues datosdia = new ContentValues();
        ContentValues datosdia1 = new ContentValues();
        datosdia.put("KcalPerdida",regreso);
        dbcreator db = new dbcreator(getApplicationContext(),"Concurso_Huawei.db",null,1);
        SQLiteDatabase BaseDeDatos=db.getWritableDatabase();
        String[] idS={String.valueOf(id)};
        BaseDeDatos.update("Dias",datosdia,"Id=?", idS);
        String[] usuario=datosUsuario();
        Double kcalUsu=Double.parseDouble(usuario[2])+kcal;
        String kcalUsu1=String.valueOf(kcalUsu);
        datosdia1.put("UsuKcalS",kcalUsu1);
        int TiempoUsu=Integer.parseInt(usuario[1])+Integer.parseInt(total);
        String TiempoUsu1=String.valueOf(TiempoUsu);
        datosdia1.put("UsuTiempo",TiempoUsu1);
        String[] nombre={usuario[0]};
        //BaseDeDatos.update("Usuarios",datosdia1,"UsuNombre", nombre);
        BaseDeDatos.execSQL("UPDATE Usuarios SET UsuTiempo='"+TiempoUsu1+"', UsuKcalS='"+kcalUsu1+"' WHERE UsuNombre='"+usuario[0]+"'");
    }
    public String traerkcaldia(){
        String kcal1=null;
        dbcreator admin = new dbcreator(getApplicationContext(),"Concurso_Huawei.db",null,1);
        SQLiteDatabase BaseDeDatos=admin.getReadableDatabase();
        Cursor fila=BaseDeDatos.rawQuery("SELECT Dia, Id, KcalPerdida FROM Dias WHERE Id= '"+id+"'",null);
        if(fila.moveToFirst()){
            kcal1=fila.getString(2);
            BaseDeDatos.close();
        }
        return kcal1;
    }
    public String[] datosUsuario(){
        String[] usuario=new String[3];
        dbcreator admin = new dbcreator(getApplicationContext(),"Concurso_Huawei.db",null,1);
        SQLiteDatabase BaseDeDatos=admin.getReadableDatabase();
        Cursor fila=BaseDeDatos.rawQuery("SELECT UsuNombre, UsuEdad, UsuPeso, UsuTiempo, UsuKcals FROM Usuarios",null);
        if(fila.moveToFirst()){
            usuario[0]=fila.getString(0);
            usuario[1]=fila.getString(3);
            usuario[2]=fila.getString(4);
        }
        return usuario;
    }
}