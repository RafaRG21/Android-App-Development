package com.example.practica04ruizgudinojoserafael;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class ActivityVideoView extends AppCompatActivity {
    protected VideoView videoView;
    protected MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        videoView = findViewById(R.id.Video_my_video);
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        //Reproducir un video en especifico
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.taquero);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();
        Toast.makeText(this, "Comienza video", Toast.LENGTH_LONG).show();
        videoView.setMediaController(mediaController);
    }//onCreate
}