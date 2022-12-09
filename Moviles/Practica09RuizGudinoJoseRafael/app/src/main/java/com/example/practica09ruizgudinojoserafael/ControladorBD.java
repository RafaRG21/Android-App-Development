package com.example.practica09ruizgudinojoserafael;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ControladorBD  extends SQLiteOpenHelper {
    public ControladorBD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Query sql
        String sqlRanger = "create table ranger (rangerCode int primary key, rangerName text, rangerLastName text, rangerShift text, rangerSalary real)";
        String sqlPark = "create table park (parkCode int primary key, parkName text, parkLocation text, parkCapacity int, parkType text)";
        try{
            //Creacion de la tabla con campos y clave primaria
            db.execSQL(sqlRanger);
            db.execSQL(sqlPark);
        }catch (SQLException e){
            Log.e("ERROR","ERROR"+ e.getMessage());
            Toast.makeText(null, "Error al crear la base de datos", Toast.LENGTH_SHORT).show();
        }//catch
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
