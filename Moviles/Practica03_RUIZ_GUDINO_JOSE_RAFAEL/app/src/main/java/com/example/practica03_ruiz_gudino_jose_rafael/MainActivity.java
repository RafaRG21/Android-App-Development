package com.example.practica03_ruiz_gudino_jose_rafael;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button especifico, topico;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().subscribeToTopic("enviaratodos").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MainActivity.this, "Subscrito a enviar a todos!", Toast.LENGTH_SHORT).show();
            }
        });
        especifico = findViewById(R.id.btnNotifEsp);
        topico = findViewById(R.id.btnNotifTop);
        especifico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llamarEspecifico();
            }
        });
        topico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llamarTopico();
            }
        });
    }
    public void llamarEspecifico(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject jsonObject = new JSONObject();
        try {
            String token = "cZpDKITzTIqiHt8csJBvWH:APA91bHaK1YP0H8aO5G4SpZAK5VqQ_j8tOgTdV64Ec6kWQ3U-I08li_MlrpbxM1GNZGkPYyObFUZk-VM24YqQC-PwOcLMwRSJPcFj5Wi-K8IQuH79QhJaZd6TE6y4676CVVmRVxjPXC8";
            jsonObject.put("to",token);
            JSONObject notification =  new JSONObject();
            notification.put("titulo","Soy un titulo :)");
            notification.put("detalle","soy un detalle");

            jsonObject.put("data",notification);
            String URL = "https://fcm.googleapis.com/fcm/send";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,URL,jsonObject,null,null){
                @Override
                public Map<String, String> getHeaders()  {
                    Map<String,String> header = new HashMap<>();
                    header.put("content-type","application/json");
                    header.put("authorization","key=AAAAQ-U9hjA:APA91bHscTQZzv9FQk78csCrMEbdP-O9Mtdbn2CnK-Sr7gVQa6CVjjzUnwcOAxZUs6fFzoxMeaz2A5OTBDA1d7CkK88jT7iehOEdIC919eN9RYqjMa4zO6wkoQQVEnACpQ63IAT1x1qf");
                    return header;
                }
            };
            requestQueue.add(request);
        }catch (JSONException e){
            e.printStackTrace();
        }
        Toast.makeText(this, "Notificación Especifica realizada", Toast.LENGTH_SHORT).show();
    }//llamarEspecifico
    public void llamarTopico(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject jsonObject = new JSONObject();
        try {
            //String token = "cZpDKITzTIqiHt8csJBvWH:APA91bHaK1YP0H8aO5G4SpZAK5VqQ_j8tOgTdV64Ec6kWQ3U-I08li_MlrpbxM1GNZGkPYyObFUZk-VM24YqQC-PwOcLMwRSJPcFj5Wi-K8IQuH79QhJaZd6TE6y4676CVVmRVxjPXC8";
            jsonObject.put("to","/topics/"+"enviaratodos");
            JSONObject notification =  new JSONObject();
            notification.put("titulo","Soy un titulo para todos :)");
            notification.put("detalle","soy un detalle para todos");

            jsonObject.put("data",notification);
            String URL = "https://fcm.googleapis.com/fcm/send";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,URL,jsonObject,null,null){
                @Override
                public Map<String, String> getHeaders()  {
                    Map<String,String> header = new HashMap<>();
                    header.put("content-type","application/json");
                    header.put("authorization","key=AAAAQ-U9hjA:APA91bHscTQZzv9FQk78csCrMEbdP-O9Mtdbn2CnK-Sr7gVQa6CVjjzUnwcOAxZUs6fFzoxMeaz2A5OTBDA1d7CkK88jT7iehOEdIC919eN9RYqjMa4zO6wkoQQVEnACpQ63IAT1x1qf");
                    return header;
                }
            };
            requestQueue.add(request);
        }catch (JSONException e){
            e.printStackTrace();
        }
        Toast.makeText(this, "Notificación a Tópico realizada", Toast.LENGTH_SHORT).show();
    }//llamarEspecifico

}//class