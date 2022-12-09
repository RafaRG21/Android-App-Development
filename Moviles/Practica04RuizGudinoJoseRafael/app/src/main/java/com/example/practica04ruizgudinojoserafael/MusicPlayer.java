package com.example.practica04ruizgudinojoserafael;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.media.MediaPlayer;

import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class MusicPlayer extends AppCompatActivity {
    private ImageButton play, pause, forward, backward;
    private TextView title, duration, current;
    int finalTime;
    private SeekBar seekBar;
    final int TIME = 5000;
    int currentPosition = 0;
    MediaPlayer mediaPlayer;
    //Clase manejadora de hilos, que se utilizar para la comunicación entre hilos
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        play = findViewById(R.id.activity_music_play);
        pause = findViewById(R.id.activity_music_pause);
         forward = findViewById(R.id.activity_music_forward);
        backward = findViewById(R.id.activity_music_back);
        title = findViewById(R.id.activity_music_song);
        duration = findViewById(R.id.activity_music_duration);
        current = findViewById(R.id.activity_music_time);
        seekBar = findViewById(R.id.activity_music_seekBar);
        //Reproducción de la canción y deshabilita el boton pause
        mediaPlayer = MediaPlayer.create(this,R.raw.copycat);
        //mediaPlayer = new MediaPlayer();

        handler = new Handler();
        pause.setEnabled(false);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Comienza la reproducción de la canción
                mediaPlayer.start();
                //Muestra el título
                title.setText("Billie Eilish");
                //Calcula la duración de la canción
                finalTime = mediaPlayer.getDuration();
                seekBar.setMax(finalTime);
                duration.setText(String.format(Locale.getDefault(),
                        "%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime)
                                % 60
                ));
                //Cada segundo va al metodo UpdateTime
                handler.postDelayed(UpdateTime, 100);
                pause.setEnabled(true);
                play.setEnabled(false);
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
                pause.setEnabled(false);
                play.setEnabled(true);
            }
        });
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentPosition + TIME <= finalTime){
                    mediaPlayer.seekTo((int)(currentPosition + TIME));
                } else {
                    mediaPlayer.seekTo(finalTime);
                }
            }
        });
        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentPosition - TIME >= 0){
                    mediaPlayer.seekTo((int)(currentPosition - TIME));
                } else {
                    mediaPlayer.seekTo(0);
                }
            }
        });

    }//onCreate


    private Runnable UpdateTime = new Runnable() {
        @Override
        public void run() {
            currentPosition = mediaPlayer.getCurrentPosition();
            current.setText(String.format(Locale.getDefault(),
                    "%d min, %d sec",

                    TimeUnit.MILLISECONDS.toMinutes((long)

                            currentPosition),
                    TimeUnit.MILLISECONDS.toSeconds((long)
                            currentPosition) % 60
            ));
            seekBar.setProgress((int) currentPosition);
            //Indica que ha concluido el hilo, debe ser la ultima linea del metodo
            handler.postDelayed(this, 100);
        }
    };//UpdateTime
}//class