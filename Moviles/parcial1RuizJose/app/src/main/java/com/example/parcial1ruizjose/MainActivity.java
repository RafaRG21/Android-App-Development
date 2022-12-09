package com.example.parcial1ruizjose;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText curp, nombre, apellidos, domicilio, cantidadIngreso;
    Spinner tipoTarjeta;
    Solicitud solicitud;
    private PendingIntent pendingIntentCita;
    private PendingIntent pendingIntentBeneficios;
    public final static String CHANNEL_ID = "NOTIFICATION";
    public final static int NOTIFICATION_ID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        curp = findViewById(R.id.txtCurp);
        nombre = findViewById(R.id.txtNombre);
        apellidos = findViewById(R.id.txtApellidos);
        domicilio = findViewById(R.id.txtDomicilio);
        tipoTarjeta = findViewById(R.id.spnTarjeta);
        cantidadIngreso = findViewById(R.id.txtCantidadIngreso);
        String[] cardList = {"Tradicional","Oro","Platino"};
        ArrayAdapter<String> cardAdapter = new ArrayAdapter<String>(
                this,
                androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
                cardList);
        tipoTarjeta.setAdapter(cardAdapter);
        solicitud = new Solicitud();

    }
    public void registrar(View v){
        if(curp.getText().toString().isEmpty()||nombre.getText().toString().isEmpty()||apellidos.getText().toString().isEmpty()||
           domicilio.getText().toString().isEmpty()||cantidadIngreso.getText().toString().isEmpty())
            Toast.makeText(this, "Completa todo los campos", Toast.LENGTH_SHORT).show();
        else{
            solicitud.setCurp(curp.getText().toString());
            solicitud.setNombre(nombre.getText().toString());
            solicitud.setApellidos(apellidos.getText().toString());
            solicitud.setDomicilio(domicilio.getText().toString());
            solicitud.setTipo_tarjeta(tipoTarjeta.getSelectedItem().toString().toLowerCase(Locale.ROOT));
            solicitud.setCantidad_ingreso(Integer.parseInt(cantidadIngreso.getText().toString()));

            if(solicitud.validar()==false)
                Toast.makeText(this,"Cantidad de Ingreso insuficiente para la tarjeta",Toast.LENGTH_SHORT).show();
            else{
                Toast.makeText(this,"Tarjeta Aceptada",Toast.LENGTH_SHORT).show();
                setPendingIntentCita();
                setPendingIntentBeneficios();
                crearCanalNotificacion();
                crearNotificacion();

            }
        }
    }//registrar
    public void limpiar(View v){
        curp.setText("");
        nombre.setText("");
        apellidos.setText("");
        domicilio.setText("");
        tipoTarjeta.setSelection(0);
        cantidadIngreso.setText("");
        curp.requestFocus();
    }//limpiar

    private void setPendingIntentCita(){
        Intent intent =  new Intent(MainActivity.this,DateActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(DateActivity.class);
        stackBuilder.addNextIntent(intent);
        pendingIntentCita = stackBuilder.getPendingIntent(1,
                PendingIntent.FLAG_CANCEL_CURRENT);
    }//setPendingIntentSi


    private void setPendingIntentBeneficios() {
        Intent intent = new Intent(MainActivity.this, BeneficiosActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(BeneficiosActivity.class);
        stackBuilder.addNextIntent(intent);
        pendingIntentBeneficios = stackBuilder.getPendingIntent(1,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }//setPendingIntentSi


    private void crearCanalNotificacion() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Notificación";
            NotificationChannel notificationChannel = new
                    NotificationChannel(CHANNEL_ID, name,
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager)
                    getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

        }//if
    }//crearCanalNotificacion


    private void crearNotificacion() {
        NotificationCompat.Builder builder = new
                NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_bank);
        builder.setContentTitle("Servicios Bancarios");
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.RED, 1000,1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setContentIntent(pendingIntentCita);
        builder.addAction(R.drawable.ic_date,"Cita",pendingIntentCita);
        builder.addAction(R.drawable.ic_card_foreground,"Beneficios",pendingIntentBeneficios);
        NotificationManagerCompat notificationManagerCompat =
                NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }//crearNotificacion
}