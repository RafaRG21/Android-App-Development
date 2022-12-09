package com.example.parcial1ruizjose;

import static com.example.parcial1ruizjose.MainActivity.NOTIFICATION_ID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class BeneficiosActivity extends AppCompatActivity {
    protected VideoView videoView;
    protected MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficios);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NotificationManagerCompat notificationManagerCompat =
                NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.cancel(NOTIFICATION_ID);
        videoView = findViewById(R.id.vvVideoTarjeta);
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        //Reproducir un video en especifico
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.creeper);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();
        Toast.makeText(this, "Comienza video", Toast.LENGTH_LONG).show();
        videoView.setMediaController(mediaController);
    }//onCreate
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(BeneficiosActivity.this,
                        MainActivity.class);
                startActivity(intent);
                finish();
                return (true);
        }//switch
        return super.onOptionsItemSelected(item);
    }//onOptionsItemSelected
}