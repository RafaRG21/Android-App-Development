package com.example.practica03ruizgudinojoserafael;

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

public class MainActivity extends AppCompatActivity {
    EditText score01, score02, score03;
    Spinner weightingType;
    FinalGrade grade;
    //Permiso para llamar a la Activity desde la notificacion y relacionar con otra Activity
    private PendingIntent pendingIntentSi;
    private PendingIntent pendingIntentNo;
    //Constante para canal de notificacion
    private final static String CHANNEL_ID = "NOTIFICATION";
    //Identificador de notificacion
    final static int NOTIFICATION_ID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        score01 = findViewById(R.id.txtScore1);
        score02 = findViewById(R.id.txtScore2);
        score03 = findViewById(R.id.txtScore3);
        weightingType = findViewById(R.id.spnWeighting);
        String[] weightingList = {"A","B","C"};
        ArrayAdapter<String> weightingAdapter = new ArrayAdapter<String>(
                this,
                androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
                weightingList);
        weightingType.setAdapter(weightingAdapter);
        grade = new FinalGrade();
    }//onCreate


    public void averageGenerator(View view){
        if(score01.getText().toString().isEmpty()||score02.getText().toString().isEmpty()||score03.getText().toString().isEmpty())
            Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show();
        else {
            grade.setWeightingType(weightingType.getSelectedItem().toString());
            grade.setScore01(Float.parseFloat(score01.getText().toString()));
            grade.setScore02(Float.parseFloat(score02.getText().toString()));
            grade.setScore03(Float.parseFloat(score03.getText().toString()));

            switch (grade.getWeightingType().toString()) {
                case "A":
                    grade.setGrade((grade.getScore01() * .20f) + (grade.getScore02() * .35f) + (grade.getScore03() * .45f));
                    break;
                case "B":
                    grade.setGrade((grade.getScore01() * .15f) + (grade.getScore02() * .35f) + (grade.getScore03() * .50f));
                    break;
                case "C":
                    grade.setGrade((grade.getScore01() * .33f) + (grade.getScore02() * .33f) + (grade.getScore03() * .34f));
                    break;
            }
            if(grade.getGrade()>=70) {
                Toast.makeText(this, "Felicidades has aprobado!\nPromedio: " + String.valueOf(grade.getGrade()), Toast.LENGTH_SHORT).show();

            }
            else{
                Toast.makeText(this, "Reprobaste :( \nPromedio: "+String.valueOf(grade.getGrade()),
                        Toast.LENGTH_SHORT).show();

                setPendingIntentSi();
                setPendingIntentNo();
                crearCanalNotificacion();
                crearNotificacion();
            }//else
            score01.setText("");
            score02.setText("");
            score03.setText("");
            weightingType.setSelection(0);
            score01.requestFocus();
        }//else

    }//averageGenerator
    //M??todo que permite abrir una Activity una vez que se presione la opci??n Si en la notificaci??n
    private void setPendingIntentSi(){
        //Crear la instancia del Intent para cambiar de una Activity a Otra
        Intent intent =  new Intent(MainActivity.this,FormularioActivity.class);
        //Instancia para acceder al historial de navegaci??n de MainActivity
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        //Establecer que la relaci??n de FormularioActivity con MainActivity
        stackBuilder.addParentStack(FormularioActivity.class);
        //Agregar el intent para enviar a la Activity especificada
        stackBuilder.addNextIntent(intent);
        pendingIntentSi = stackBuilder.getPendingIntent(1,
                PendingIntent.FLAG_CANCEL_CURRENT);
    }//setPendingIntentSi

    //M??todo que permite abrir una Activity una vez que se presione la opci??n No en la notificaci??n
    private void setPendingIntentNo() {
        //Crear la instancia del Intent para cambiar de una Activity a Otra
        Intent intent = new Intent(MainActivity.this, InfoActivity.class);
        //Instancia para acceder al historial de navegaci??n de MainActivity
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        //Establecer que la relaci??n de InfoActivity con MainActivity
        stackBuilder.addParentStack(InfoActivity.class);
        //Agregar el intent para enviar a la Activity especificada
        stackBuilder.addNextIntent(intent);
        //Relaionar el historial con el objeto con la variable
        pendingIntentNo = stackBuilder.getPendingIntent(1,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }//setPendingIntentSi
    //M??todo parar definir el canal de comunicaci??n para enviar la notificaci??n
    private void crearCanalNotificacion() {
        //Validar si es versi??n Android superior a O (API >=26 )
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //Nombre del canal
            CharSequence name = "Notificaci??n";
            //Instancia para gestionar el canal y el servicio de la notificaci??n
            NotificationChannel notificationChannel = new
                    NotificationChannel(CHANNEL_ID, name,
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager)
                    getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

        }//if
    }//crearCanalNotificacion
    //M??todo que permite especificar las caracter??sticas de la notificaci??n
    private void crearNotificacion() {
        //Instancia para generar la notificaci??n, especificando el contexto de la aplicaci??n y el
        //canal de comunicaci??n
        NotificationCompat.Builder builder = new
                NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
        //Caracter??sticas a incluir en la notificaci??n
        builder.setSmallIcon(R.drawable.ic_stat_notification);
        builder.setContentTitle("Servicios Escolares");
        builder.setContentText("??Agendar examen extraordinario?");
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.RED, 1000,1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);
        //Especifica la Activity que aparece al momento de elegir la notificaci??n
        builder.setContentIntent(pendingIntentSi);
        //Se agregan las opciones que aparecen en la notificaci??n
        builder.addAction(R.drawable.ic_stat_notification_happy,"Si",pendingIntentSi);
        builder.addAction(R.drawable.ic_stat_notification_sad,"No",pendingIntentNo);
        //Instancia que gestiona la notificaci??n con el dispositivo
        NotificationManagerCompat notificationManagerCompat =
                NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }//crearNotificacion
}