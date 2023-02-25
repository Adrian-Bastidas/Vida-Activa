package com.example.huaweic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class dbcreator extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NOMBRE="Concurso_Huawei.db";
    public static final String TABLE_USUARIOS = "Usuarios";
    public static final String TABLE_DIAS = "Dias";
    public static final String TABLE_EJERCICIOS="Ejercicios";

    public dbcreator(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }
    public dbcreator(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    public void crear(SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+ TABLE_USUARIOS+"(" +
                "UsuNombre TEXT PRIMARY KEY," +
                "UsuEdad TEXT NOT NULL," +
                "UsuPeso TEXT NOT NULL," +
                "UsuTiempo TEXT," +
                "UsuKcalS TEXT," +
                "UsuDist TEXT);");
        db.execSQL("CREATE TABLE "+ TABLE_DIAS+"(" +
                "Dia TEXT NOT NULL," +
                "Id TEXT PRIMARY KEY," +
                "KcalPerdida TEXT," +
                "Dist TEXT);");
        db.execSQL("Create TABLE "+ TABLE_EJERCICIOS+"(" +
                "EjNombre TEXT NOT NULL," +
                "EjTiempo TEXT NOT NULL," +
                "Ejnum TEXT PRIMARY KEY," +
                "EjMET TEXT NOT NULL," +
                "EjFuncionalidad TEXT NOT NULL," +
                "EjDia TEXT NOT NULL);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
