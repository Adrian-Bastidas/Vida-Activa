package com.example.huaweic;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.huaweic.services.MyHuaweiMessageServices;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.push.HmsMessaging;

public class PreparacionCasa extends AppCompatActivity{
    CheckBox lunes, martes, miercoles, jueves, viernes, sabado, domingo;
    int diasCont=0, id=0;
    public static final String TAG="PreparacionCasa";
    EditText mensaje;
    private int hora,minutos;
    SwitchCompat sports, salud;
    String Posibles[]=new String[]{null,null,null,null,null,null,null};
    ContentValues Dias= new ContentValues();
    SharedPreferences preferences;
    AlertDialog loadingDialog;
    ContentValues Usuario= new ContentValues();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preparacion_casa);
        sports = findViewById(R.id.motivacion);
        salud = findViewById(R.id.salud);
        preferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        lunes=(CheckBox) findViewById(R.id.Lunes);
        martes=(CheckBox) findViewById(R.id.Martes);
        miercoles=(CheckBox) findViewById(R.id.Miercoles);
        jueves=(CheckBox) findViewById(R.id.Jueves);
        viernes=(CheckBox) findViewById(R.id.Viernes);
        sabado=(CheckBox) findViewById(R.id.Sabado);
        domingo=(CheckBox) findViewById(R.id.Domingo);
    }

    public void verificar(View v){
        if(v==lunes){
            if(dias(lunes)==false){
                Toast.makeText(this,"Necesitas 2 días para descansar",Toast.LENGTH_SHORT).show();
                lunes.setChecked(false);
            }
        }
        if(v==martes){
            if(dias(martes)==false){
                Toast.makeText(this,"Necesitas 2 días para descansar",Toast.LENGTH_SHORT).show();
                martes.setChecked(false);
            }
        }
        if(v==miercoles){
            if(dias(miercoles)==false){
                Toast.makeText(this,"Necesitas 2 días para descansar",Toast.LENGTH_SHORT).show();
                miercoles.setChecked(false);
            }
        }
        if(v==jueves){
            if(dias(jueves)==false){
                Toast.makeText(this,"Necesitas 2 días para descansar",Toast.LENGTH_SHORT).show();
                jueves.setChecked(false);
            }
        }
        if(v==viernes){
            if(dias(viernes)==false){
                Toast.makeText(this,"Necesitas 2 días para descansar",Toast.LENGTH_SHORT).show();
                viernes.setChecked(false);
            }
        }
        if(v==sabado){
            if(dias(sabado)==false){
                Toast.makeText(this,"Necesitas 2 días para descansar",Toast.LENGTH_SHORT).show();
                sabado.setChecked(false);
            }
        }
        if(v==domingo){
            if(dias(domingo)==false){
                Toast.makeText(this,"Necesitas 2 días para descansar",Toast.LENGTH_SHORT).show();
                domingo.setChecked(false);
            }
        }
    }
    private boolean dias(CheckBox d){
        boolean resp=true;
        if(diasCont<5&&d.isChecked()==true) {
            diasCont += 1;
        }
        else if(diasCont>0&&d.isChecked()==false){
            diasCont-=1;

        }
        else if(diasCont==5){
            resp=false;
        }
        return resp;
    }
    private void sacardatos(){
        if(dias(lunes)==false){
            Posibles[0]=lunes.getText().toString();
        }
        if(dias(martes)==false){
            Posibles[1]=martes.getText().toString();
        }
        if(dias(miercoles)==false){
            Posibles[2]=miercoles.getText().toString();
        }
        if(dias(jueves)==false){
            Posibles[3]=jueves.getText().toString();
        }
        if(dias(viernes)==false){
            Posibles[4]=viernes.getText().toString();
        }
        if(dias(sabado)==false){
            Posibles[5]=sabado.getText().toString();
        }
        if(dias(domingo)==false){
            Posibles[6]=domingo.getText().toString();
        }
    }
public int rellenar1(int j){
    dbcreator db=new dbcreator(this);
    SQLiteDatabase BaseDeDatos=db.getWritableDatabase();
    String[] Ejercicios1={"Flexiones","Flexiones con pierna levantada","Flexiones con rodilla al pecho","Flexiones con brazos abiertos",
            "Superman", "Rema", "Levantamiento de cadera"};
    String Tiempos1="15";
    String[] MET1={"8.0","8.0","8.0","8.0","4.5","3.5","4.5"};
    for (int i=0;i<Ejercicios1.length;i++){
        ContentValues Ejercicios=new ContentValues();
        Ejercicios.put("EjNombre",Ejercicios1[i]);
        Ejercicios.put("EjTiempo",Tiempos1);
        String Temp=String.valueOf(j+1);
        j++;
        Ejercicios.put("Ejnum",Temp);
        Ejercicios.put("EjMET",MET1[i]);
        Ejercicios.put("EjFuncionalidad","Pecho-Espalda");
        Ejercicios.put("EjDia","1");
        BaseDeDatos.insert("Ejercicios",null,Ejercicios);
    }
return j;
}
    public int rellenar2(int j){
        dbcreator db=new dbcreator(this);
        SQLiteDatabase BaseDeDatos=db.getWritableDatabase();
        String[] Ejercicios2={"Fondos","Kurl con toalla","Flexiones elevadas","Flexiones diamante","Elevaciones laterales",
                "Flexiones con brazo al hombro"};
        String Tiempo2="30";
        String[] MET2={"8.0","6.0","8.0","8.0","4.5","4.5"};
        for (int i=0;i<Ejercicios2.length;i++){
            ContentValues Ejercicios=new ContentValues();
            Ejercicios.put("EjNombre",Ejercicios2[i]);
            Ejercicios.put("EjTiempo",Tiempo2);
            String Temp=String.valueOf(j+1);
            j++;
            Ejercicios.put("Ejnum",Temp);
            Ejercicios.put("EjMET",MET2[i]);
            Ejercicios.put("EjFuncionalidad","Brazos-Hombros");
            Ejercicios.put("EjDia","2");
            BaseDeDatos.insert("Ejercicios",null,Ejercicios);
        }
return j;
    }
    public int rellenar3(int j){
        dbcreator db=new dbcreator(this);
        SQLiteDatabase BaseDeDatos=db.getWritableDatabase();
        String[] Ejercicios3={"Abdominales con rodilla al pecho", "Abdominal codo a rodilla", "Abdominal crunch","Tabla",
                "Abdominal toque al talón","Levantamiento de rodillas"};
        String Tiempo3="60";
        String[] MET3={"8.0","8.0","8.0","8.0","8.0","4.5"};
        for (int i=0;i<Ejercicios3.length;i++){
            ContentValues Ejercicios=new ContentValues();
            Ejercicios.put("EjNombre",Ejercicios3[i]);
            Ejercicios.put("EjTiempo",Tiempo3);
            String Temp=String.valueOf(j+1);
            j++;
            Ejercicios.put("Ejnum",Temp);
            Ejercicios.put("EjMET",MET3[i]);
            Ejercicios.put("EjFuncionalidad","Abdominales");
            Ejercicios.put("EjDia","3");
            BaseDeDatos.insert("Ejercicios",null,Ejercicios);
        }
        return j;
    }
    public int rellenar4(int j){
        dbcreator db=new dbcreator(this);
        SQLiteDatabase BaseDeDatos=db.getWritableDatabase();
        String[] Ejercicios4={"Sentadillas","Zancadas","Sentadillas explosivas", "Elevaciones de talones de pie", "Sentadilla con elevación de pierna",
                "Skipping bajo"};
        String Tiempo4="60";
        String[] MET4={"4.5","4.5","8.0","4.5","4.5","8.0"};
        for (int i=0;i<Ejercicios4.length;i++){
            ContentValues Ejercicios=new ContentValues();
            Ejercicios.put("EjNombre",Ejercicios4[i]);
            Ejercicios.put("EjTiempo",Tiempo4);
            String Temp=String.valueOf(j+1);
            j++;
            Ejercicios.put("Ejnum",Temp);
            Ejercicios.put("EjMET",MET4[i]);
            Ejercicios.put("EjFuncionalidad","Piernas");
            Ejercicios.put("EjDia","4");
            BaseDeDatos.insert("Ejercicios",null,Ejercicios);
        }
        return j;
    }
    public int rellenar5(int j){
        dbcreator db=new dbcreator(this);
        SQLiteDatabase BaseDeDatos=db.getWritableDatabase();
        String[] Ejercicio5={"Burpee","Tabla","Fondos","Tabla con cambios de apoyo","Tabla frontal con elevación de brazo y pierna"};
        String Tiempo5="60";
        String[] MET5={"8.0","8.0","8.0","8.0","8.0","8.0"};
        String Funcionalidad7="Calistemia";
        for (int i=0;i<Ejercicio5.length;i++){
            ContentValues Ejercicios=new ContentValues();
            Ejercicios.put("EjNombre",Ejercicio5[i]);
            Ejercicios.put("EjTiempo",Tiempo5);
            String Temp=String.valueOf(j+1);
            j++;
            Ejercicios.put("Ejnum",Temp);
            Ejercicios.put("EjMET",MET5[i]);
            Ejercicios.put("EjFuncionalidad","Calistemia");
            Ejercicios.put("EjDia","5");
            BaseDeDatos.insert("Ejercicios",null,Ejercicios);
        }
        return j;
    }
    public void terminar(View view){
        if(validar()==true){
            String nombre=getIntent().getStringExtra("nombre");
            String edad=getIntent().getStringExtra("edad");
            String peso=getIntent().getStringExtra("peso");
            ContentValues dias=new ContentValues();
            dbcreator db=new dbcreator(this);
            SQLiteDatabase BaseDeDatos=db.getWritableDatabase();
            db.crear(BaseDeDatos);
            sacardatos();
            for (int i=0;i<Posibles.length;i++){
                if(Posibles[i]!=null){
                    Dias = new ContentValues();
                    Dias.put("Dia",Posibles[i]);
                    String Temp=String.valueOf(i+1);
                    Dias.put("Id",Temp);
                    Dias.put("KcalPerdida","0");
                    Dias.put("Dist","0");
                    BaseDeDatos.insert("Dias",null,Dias);
                }
            }
            Usuario.put("UsuNombre",nombre);
            Usuario.put("UsuEdad",edad);
            Usuario.put("UsuPeso",peso);
            Usuario.put("UsuTiempo","0");
            Usuario.put("UsuKcalS","0");
            Usuario.put("UsuDist","0");
            BaseDeDatos.insert("Usuarios",null,Usuario);
            int j=0;
            j=rellenar1(j);
            j=rellenar2(j);
            j=rellenar3(j);
            j=rellenar4(j);
            j=rellenar5(j);
            BaseDeDatos.close();
            enviarSuscripción();
            Intent siguiente = new Intent(this, Personal.class);
            startActivity(siguiente);
        }
        else{
            Toast.makeText(this,"No pueden quedar campos vacios",Toast.LENGTH_SHORT).show();
        }
    }
public void enviarSuscripción(){
        if(sports.isChecked()){
            subscribe("Sports");
        }
        else{
            unsubscribe("Sports");
        }
    if(salud.isChecked()){
        subscribe("salud");
    }
    else{
        unsubscribe("salud");
    }
}
    public boolean validar(){
        boolean retorno=true;
        if(lunes.isChecked()==false && martes.isChecked()==false && miercoles.isChecked()==false && jueves.isChecked()==false && viernes.isChecked()==false && sabado.isChecked()==false && domingo.isChecked()==false){
            lunes.setError("Debes entrenar almenos 1 día");
            retorno=false;
        }
        return retorno;
    }
    public void getToken() {
        // Create a thread.
        new Thread() {
            @Override
            public void run() {
                try {
                    // Obtain the app ID from the agconnect-services.json file.
                    String appId = AGConnectServicesConfig.fromContext(getApplicationContext()).getString("/client/app_id");

                    // Set tokenScope to HCM.
                    String tokenScope = "HCM";
                    String token = HmsInstanceId.getInstance(getApplicationContext()).getToken(appId, tokenScope);
                    Log.i(TAG, "get token: " + token);

                    // Check whether the token is null.
                    if(!TextUtils.isEmpty(token)) {
                        sendRegTokenToServer(token);
                    }
                } catch (ApiException e) {
                    Log.e(TAG, "get token failed, " + e);
                }
            }
        }.start();
    }
    private void sendRegTokenToServer(String token) {
        Log.i(TAG, "sending token to server. token:" + token);
    }
    public void subscribe(String topic) {
        try {
            getToken();
            // Subscribe to a topic.
            HmsMessaging.getInstance(getApplicationContext()).subscribe(topic)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            // Obtain the topic subscription result.
                            if (task.isSuccessful()) {
                                Log.i(TAG, "subscribe topic successfully");
                            } else {
                                Log.e(TAG, "subscribe topic failed, return value is " + task.getException().getMessage());
                            }
                        }
                    });
        } catch (Exception e) {
            Log.e(TAG, "subscribe failed, catch exception : " + e.getMessage());
        }
    }

    public void unsubscribe(String topic) {
        try {
            // Unsubscribe from a topic.
            HmsMessaging.getInstance(this).unsubscribe(topic)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            // Obtain the topic unsubscription result.
                            if (task.isSuccessful()) {
                                Log.i(TAG, "unsubscribe topic successfully");
                            } else {
                                Log.e(TAG, "unsubscribe topic failed, return value is " + task.getException().getMessage());
                            }
                        }
                    });
        } catch (Exception e) {
            Log.e(TAG, "unsubscribe failed, catch exception : " + e.getMessage());
        }
    }
    /*
    public void showLoadingDialog(){
        loadingDialog=LoadingDialog.createDialog(this);
        loadingDialog.show();
    }

    public void hideLoadingDialog(){
        if(loadingDialog!=null&&loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void loadSubscriptionsState() {
        sports.setOnCheckedChangeListener(null);
        sports.setChecked(preferences.getBoolean(SPORTS_SUBSCRIPTION, false));
        hideLoadingDialog();
    }
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        showLoadingDialog();
        changeSubscriptionState(SPORTS_SUBSCRIPTION, isChecked);
    }

    public void changeSubscriptionState(String topic, boolean subscription) {
        if(subscription){
            //subscribe
            subscribe(topic);
        }
        else{
            //unsibscribe
            unsubscribe(topic);
        }
    }



    private void saveSubscriptionState(String topic, boolean b) {
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean(topic,b);
        editor.apply();
        hideLoadingDialog();
    }

     */
}