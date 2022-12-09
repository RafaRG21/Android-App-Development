package com.example.practica03_ruiz_gudino_jose_rafael;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.Random;

public class FCM extends FirebaseMessagingService {
    //Da el token (id) del dispositivo con el cual podemos enviar notificaciones
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.e("Token","El token es: "+token);
        guardarToken(token);
    }//onNewToken

    private void guardarToken(String token) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("token");
        //se guarda el token con el nombre de usuario
        reference.child("rafael").setValue(token);
    }//guardarToken

    //Se reciben todos los mensajes y se pueden validar, verificar, version del disposotivo
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        String from = message.getFrom();

       // Log.e("TAG","Mensaje recibido de: "+from);
        /*if(message.getNotification()!=null) {
            Log.e("TAG", "el titulo es: " + message.getNotification().getTitle());
            Log.e("TAG", "el titulo es: " + message.getNotification().getBody());
        }*/

        if(message.getData().size()>0){
           // Log.e("TAG", "Titulo es: " + message.getData().get("titulo"));
            //Log.e("TAG", "detalle es: " + message.getData().get("detalle"));
            //Log.e("TAG", "el color es: " + message.getData().get("color"));
            String titulo = message.getData().get("titulo");
            String detalle = message.getData().get("detalle");
            mayorOreo(titulo, detalle);


        }

    }//onMessageReceived

    private void mayorOreo(String titulo, String detalle) {
        String id = "mensaje";
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,id);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(id, "nuevo", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setShowBadge(true);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }
        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(titulo)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(detalle)
                .setContentIntent(clickNotification())
                .setContentInfo("nuevo");
        Random random = new Random();
        int idNotify  = random.nextInt(8000);
        assert notificationManager != null;

        notificationManager.notify(idNotify,builder.build());
    }//mayorOreo

    private PendingIntent clickNotification() {
        Intent nf =  new Intent(getApplicationContext(),MainActivity.class);
        nf.putExtra("color","red");
        nf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(this,0,nf,0);
    }
}
