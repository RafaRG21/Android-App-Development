package com.example.rgjrsensores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.TextView;

public class GravityActivity extends AppCompatActivity implements SensorEventListener {
    TextView Xtvw, Ytvw, Ztvw;
    SensorManager sensorManager;
    Sensor sensorGravity;
    Vibrator vibrator;

    private AudioManager audioManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gravity);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        Xtvw = findViewById(R.id.tvwGravityAxisX);
        Ytvw = findViewById(R.id.tvwGravityAxisY);
        Ztvw = findViewById(R.id.tvwGravityAxisZ);
        vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorGravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

    }

    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this,sensorGravity,SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this,sensorGravity);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        Xtvw.setText("Axis X: "+event.values[0]+" m/s^2");
        Ytvw.setText("Axis Y: "+event.values[1]+" m/s^2");
        Ztvw.setText("Axis Z: "+event.values[2]+" m/s^2");
        if(event.values[2] < 9.9&&event.values[2]>9.7){
            getWindow().getDecorView().setBackgroundColor(Color.RED);
            vibrator.vibrate(5000);
        }else{
            getWindow().getDecorView().setBackgroundColor(Color.WHITE);
            vibrator.cancel();
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}