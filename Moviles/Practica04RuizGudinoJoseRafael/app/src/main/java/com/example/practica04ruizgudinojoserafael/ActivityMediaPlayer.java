package com.example.practica04ruizgudinojoserafael;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.media.MediaPlayer;
import android.widget.Toast;

import java.io.IOException;


public class ActivityMediaPlayer extends AppCompatActivity {
    Button play, stop;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        play = findViewById(R.id.btnPlay);
        stop = findViewById(R.id.btnStop);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                Uri uri = Uri.parse("android.resource://" +
                        getPackageName() + "/" + R.raw.copycat);
                try {
                    mediaPlayer.setDataSource(ActivityMediaPlayer.this,
                            uri);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    Toast.makeText(ActivityMediaPlayer.this, "Comienza COPYCAT by Billie Eilish", Toast.LENGTH_LONG).show();
                } catch (IOException e){
                    Toast.makeText(ActivityMediaPlayer.this, "Error al reproducir", Toast.LENGTH_LONG).show();
                }
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer != null && mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer = null;

                    Toast.makeText(ActivityMediaPlayer.this, "Se detiene reproducción ", Toast.LENGTH_LONG).show();
                }//if
            }
        });
    }//onCreate

}//class