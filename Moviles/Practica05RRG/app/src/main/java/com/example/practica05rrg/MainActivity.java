package com.example.practica05rrg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }//onCreate


    public void lector(View v){
        Intent intent = new Intent(getApplicationContext(),ActivityLector.class);
        startActivity(intent);
    }//lector


    public void camara(View v){
        Intent intent = new Intent(getApplicationContext(),ActivityFoto.class);
        startActivity(intent);
    }//Camara
}//Class