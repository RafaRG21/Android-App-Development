package com.example.practica03ruizgudinojoserafael;

import static com.example.practica03ruizgudinojoserafael.MainActivity.NOTIFICATION_ID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        //habilitar la flecha hacia atras en la barra de estado
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Eliminar la notificación de barra de estado
        NotificationManagerCompat notificationManagerCompat =
                NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.cancel(NOTIFICATION_ID);

    }//onCreate
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                //Instancia de la Activity a donde se regresarà la Activity Actual
                Intent intent = new Intent(InfoActivity.this,
                        MainActivity.class);
                startActivity(intent);
                finish();
                return (true);
        }//switch
        return super.onOptionsItemSelected(item);
    }
}