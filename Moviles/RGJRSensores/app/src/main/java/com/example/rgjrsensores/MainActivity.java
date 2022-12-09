package com.example.rgjrsensores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
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
            case R.id.btnSensorList:
                intent = new Intent(this,Sensors.class);
                break;
            case R.id.btnSensorsProximity:
                intent = new Intent(this,ProximityActivity.class);
                break;
            case R.id.btnSensorsEnvironment:
                intent = new Intent(this,EnviromenntActivity.class);
                break;
        }//switch
        startActivity(intent);

    }//onClick

}//class