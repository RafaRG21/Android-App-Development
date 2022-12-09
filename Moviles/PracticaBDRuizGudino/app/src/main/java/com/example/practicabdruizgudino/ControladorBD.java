package com.example.practicabdruizgudino;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ControladorBD extends SQLiteOpenHelper {


    public ControladorBD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }//Constructor

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Query sql
        String sql = "create table empleado (numemp int primary key, nombre text, apellidos text, sueldo real)";
        try{
            //Creacion de la tabla con campos y clave primaria
            db.execSQL(sql);
        }catch (SQLException e){
            Log.e("ERROR","ERROR"+ e.getMessage());
            Toast.makeText(null, "Error al crear la base de datos", Toast.LENGTH_SHORT).show();
        }//catch
    }//onCreate

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
