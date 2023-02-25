package com.example.huaweic;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LabelFormatter;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class MenuPrincipal extends AppCompatActivity {
    TextView nombre, peso, edad,Ks,Kh,Ts, Pesotxt, Edadtxt, Dist;
    EditText c, e;
    Button b;
    GraphView grafico;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        nombre=(TextView) findViewById(R.id.name);
        peso=(TextView) findViewById(R.id.pesoinfo);
        edad=(TextView) findViewById(R.id.edadinfo);
        Ks=(TextView) findViewById(R.id.caloriasemana);
        Ts=(TextView) findViewById(R.id.tiemposemana);
        c=(EditText) findViewById(R.id.peso2);
        b=(Button) findViewById(R.id.button4);
        e=(EditText) findViewById(R.id.nuevaedad);
        Pesotxt=(TextView) findViewById(R.id.pesotxt);
        Edadtxt=(TextView) findViewById(R.id.edadtxt);
        Dist=(TextView) findViewById(R.id.Distancia);
        grafico=findViewById(R.id.grafico);
        rellenarUsuario();
    }
    public void cambio(View ver){
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
    private void graph(SQLiteDatabase BaseDeDatos){
        String[] datos=new String[5];
        Double[] cal=new Double[5];
        for(int i=0; i<=5;i++){
            int ide=i+1;
            Cursor fila=BaseDeDatos.rawQuery("SELECT Dia, Id,KcalPerdida, Dist FROM Dias WHERE Id='"+ide+"'",null);
            if(fila.moveToFirst()){
                datos[i]=fila.getString(0);
                cal[i]=Double.parseDouble(fila.getString(2));
            }
        }
        grafico.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if(isValueX){
                    return "Dia "+super.formatLabel(value, isValueX);
                }
                else{
                    return super.formatLabel(value, isValueX);
                }
            }
        });
        grafico.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
        grafico.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
        grafico.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
        grafico.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
        grafico.getGridLabelRenderer().reloadStyles();
        LineGraphSeries<DataPoint> series=new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(1,cal[0]),
            new DataPoint(2,cal[1]),
            new DataPoint(3,cal[2]),
            new DataPoint(4,cal[3]),
            new DataPoint(5,cal[4])
        });
        series.setAnimated(true);
        series.setDrawBackground(true);
        series.setDrawDataPoints(true);
        grafico.addSeries(series);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void rellenarUsuario(){
        dbcreator admin = new dbcreator(getApplicationContext(),"Concurso_Huawei.db",null,1);
        SQLiteDatabase BaseDeDatos=admin.getReadableDatabase();
        graph(BaseDeDatos);
        String dia=diaactual();
        Cursor fila=BaseDeDatos.rawQuery("SELECT UsuNombre,UsuEdad,UsuPeso,UsuTiempo,UsuKcalS, UsuDist FROM Usuarios",null);
        if(fila.moveToFirst()){
            nombre.setText(fila.getString(0));
            peso.setText(fila.getString(2));
            edad.setText(fila.getString(1));
            Ks.setText(fila.getString(4));
            Ts.setText(fila.getString(3)+" min");
            Dist.setText(fila.getString(5)+ " Km");
            BaseDeDatos.close();
        }
    }
    public void aparecer(View ver){
        c.setVisibility(View.VISIBLE);
        b.setVisibility(View.VISIBLE);
        e.setVisibility(View.VISIBLE);
        Pesotxt.setVisibility(View.VISIBLE);
        Edadtxt.setVisibility(View.VISIBLE);
    }
    public void actu(View ver){
        dbcreator admin = new dbcreator(getApplicationContext(),"Concurso_Huawei.db",null,1);
        SQLiteDatabase BaseDeDatos=admin.getWritableDatabase();
        String[] parametros={nombre.getText().toString()};
        String prueba1=c.getText().toString();
        String prueba2=e.getText().toString();
        if(!(c.getText().toString().isEmpty()) && !(e.getText().toString().isEmpty())){
            ContentValues peso2=new ContentValues();
            peso2.put("UsuPeso",c.getText().toString());
            peso2.put("UsuEdad",e.getText().toString());
            BaseDeDatos.update("Usuarios",peso2,"UsuNombre=?",parametros);
            BaseDeDatos.close();
            c.setVisibility(View.INVISIBLE);
            b.setVisibility(View.INVISIBLE);
            e.setVisibility(View.INVISIBLE);
            Pesotxt.setVisibility(View.INVISIBLE);
            Edadtxt.setVisibility(View.INVISIBLE);
            Intent siguiente = new Intent(this, MenuPrincipal.class);
            startActivity(siguiente);
        }
        if(c.getText().toString().isEmpty() && !(e.getText().toString().isEmpty())){
            ContentValues peso2=new ContentValues();
            peso2.put("UsuEdad",e.getText().toString());
            BaseDeDatos.update("Usuarios",peso2,"UsuNombre=?",parametros);
            BaseDeDatos.close();
            c.setVisibility(View.INVISIBLE);
            b.setVisibility(View.INVISIBLE);
            e.setVisibility(View.INVISIBLE);
            Pesotxt.setVisibility(View.INVISIBLE);
            Edadtxt.setVisibility(View.INVISIBLE);
            Intent siguiente = new Intent(this, MenuPrincipal.class);
            startActivity(siguiente);
        }
        if(!(c.getText().toString().isEmpty()) && e.getText().toString().isEmpty()){
            ContentValues peso2=new ContentValues();
            peso2.put("UsuPeso",c.getText().toString());
            BaseDeDatos.update("Usuarios",peso2,"UsuNombre=?",parametros);
            BaseDeDatos.close();
            c.setVisibility(View.INVISIBLE);
            b.setVisibility(View.INVISIBLE);
            e.setVisibility(View.INVISIBLE);
            Pesotxt.setVisibility(View.INVISIBLE);
            Edadtxt.setVisibility(View.INVISIBLE);
            Intent siguiente = new Intent(this, MenuPrincipal.class);
            startActivity(siguiente);
        }
        if(c.getText().toString().isEmpty() && (e.getText().toString().isEmpty())){
            BaseDeDatos.close();
            c.setVisibility(View.INVISIBLE);
            b.setVisibility(View.INVISIBLE);
            e.setVisibility(View.INVISIBLE);
            Pesotxt.setVisibility(View.INVISIBLE);
            Edadtxt.setVisibility(View.INVISIBLE);
            Intent siguiente = new Intent(this, MenuPrincipal.class);
            startActivity(siguiente);
        }
    }
}