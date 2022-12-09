package com.example.rgjrsensores;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ProximityActivity extends AppCompatActivity implements SensorEventListener {
    ImageView near, far;
    SensorManager sensorManager;
    Sensor sensor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity);
        near = findViewById(R.id.imgProximityNear);
        far = findViewById(R.id.imgProximityFar);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(sensor.TYPE_PROXIMITY);

    }//onCreate

    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_FASTEST);

    }//onResume

    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.values[0] == 0){
            near.setVisibility(View.VISIBLE);
            far.setVisibility(View.INVISIBLE);
        }else{
            near.setVisibility(View.INVISIBLE);
            far.setVisibility(View.VISIBLE);
        }
    }//onSensorChanged

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }//onAccuracyChanged
}//class