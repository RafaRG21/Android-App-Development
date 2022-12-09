package com.example.practica05camara;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button scan, photo;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scan = findViewById(R.id.btnScan);
        photo = findViewById(R.id.btnPhoto);

        scan.setOnClickListener(view -> {

            intent = new Intent(getApplicationContext(), ScanActivity.class);
            startActivity(intent);

        });

        photo.setOnClickListener(view -> {

            intent = new Intent(getApplicationContext(), PhotoActivity.class);
            startActivity(intent);

        });

    }//onCreate

}//main