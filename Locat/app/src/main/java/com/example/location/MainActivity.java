package com.example.location;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v){
        Intent intent = null;
        switch (v.getId()){
            case R.id.activity_main_maps:
                intent = new Intent(this, GoogleMapsActivity.class);
                break;
        }//switch
        startActivity(intent);
    }//onClick

}