package com.example.practica04ruizgudinojoserafael;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }//onCreate


    public void onClick(View v){
        Intent intent = null;
        switch (v.getId()){
            case R.id.btnMediaP:
                intent = new Intent(this, ActivityMediaPlayer.class);
                break;
            case R.id.btnMusicP:
                intent = new Intent(this,MusicPlayer.class);
                break;
            case R.id.btnVideoV:
                intent = new Intent(this,ActivityVideoView.class);
                break;
        }
        startActivity(intent);
    }//ONcLICK
}//Class