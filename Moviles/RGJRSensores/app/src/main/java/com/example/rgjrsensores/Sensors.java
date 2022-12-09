package com.example.rgjrsensores;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class Sensors extends AppCompatActivity {
    SensorManager sensorManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

        StringBuilder data = new StringBuilder();
        int count = 1;

        data.append("Lista de sensores\n");
        for(Sensor sensor : sensorList){
            data.append("====================\n");
            data.append("Sensor #"+count++ + "\n");
            data.append("====================\n");
            data.append(sensor.getName()+"\n");
            data.append(sensor.getVendor()+"\n");
            data.append(sensor.getVersion()+"\n\n");
        }//for
        TextView textView = findViewById(R.id.tvwSensors);
        textView.setText(data.toString());
    }//onCreate
}//class