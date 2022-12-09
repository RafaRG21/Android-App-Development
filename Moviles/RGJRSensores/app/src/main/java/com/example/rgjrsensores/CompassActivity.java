package com.example.rgjrsensores;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CompassActivity extends AppCompatActivity {
    private ImageView needle;
    private Button btnResetCompass;
    private SensorManager sensorManager;
    private Sensor sensorAccelerometer, sensorMagneticField;
    private float[] gravity = new float[3];
    private float[] geoMagnetic = new float[3];
    private float[] orientation = new float[3];
    private float[] rotationMatrix = new float[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        needle = (ImageView) findViewById(R.id.imgCompassNeedle);
        btnResetCompass = (Button) findViewById(R.id.btnCompassReset);
        sensorManager  = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMagneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        SensorEventListener sensorEventListenerAccelerometer = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                gravity = event.values;
                SensorManager.getRotationMatrix(rotationMatrix,null,gravity,geoMagnetic);
                SensorManager.getOrientation(rotationMatrix,orientation);
                needle.setRotation((float) (-orientation[0]*180/Math.PI));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };//sensorEventListenerAccelerometer

        SensorEventListener sensorEventListenerMagneticField = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                geoMagnetic = event.values;
                SensorManager.getRotationMatrix(rotationMatrix,null,gravity,geoMagnetic);
                SensorManager.getOrientation(rotationMatrix,orientation);
                needle.setRotation((float) (-orientation[0]*180/Math.PI));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };//sensorEventListenerMagneticField

        sensorManager.registerListener(sensorEventListenerAccelerometer,sensorAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListenerMagneticField,sensorMagneticField,SensorManager.SENSOR_DELAY_NORMAL);


        btnResetCompass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBtnResetCompass();
            }//onClick
        });//btnResetCompass clicklistener

    }//onCreate
    private void setBtnResetCompass(){
        needle.setRotation(180);
    }//setBtnResetCompass
}//class