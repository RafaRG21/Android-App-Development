package com.example.parcial1ruizjose;

import static com.example.parcial1ruizjose.MainActivity.NOTIFICATION_ID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class DateActivity extends AppCompatActivity {

    Button fecha, hora;
    private int idAlarma = 1;
    private int diaAlarma = 0;
    private int horaAlarma = 0;
    private int minutoAlarma =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.cancel(NOTIFICATION_ID);
        hora = findViewById(R.id.btnHora);
        fecha = findViewById(R.id.btnFecha);
    }//onCreate

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                //Instancia de la Activity a donde se regresarà la Activity Actual
                Intent intent = new Intent(DateActivity.this,
                        MainActivity.class);
                startActivity(intent);
                finish();
                return (true);
        }//switch
        return super.onOptionsItemSelected(item);
    }//onOptionsItemSelected

    public void registrarCita(View view){
        programarAlarmaSistema("Cita Ejecutivo ",horaAlarma,minutoAlarma);
        Toast.makeText(this, "Cita registrada", Toast.LENGTH_SHORT).show();

    }//registrarCita


    public void setFechaAlarma(View view){
        //Instancia para calendar
        Calendar calendarToday = Calendar.getInstance();
        //calendarToday.setTimeInMillis(System.currentTimeMillis());
        //Obtener los valores actuales del sistema
        int anio = calendarToday.get(Calendar.YEAR);
        int mes = calendarToday.get(Calendar.MONTH);
        int dia = calendarToday.get(Calendar.DAY_OF_MONTH);
        //Fecha para elegir la cita
        DatePickerDialog datePickerDialog  = new DatePickerDialog(DateActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                diaAlarma = dayOfMonth;
            }
        },anio,mes,dia);
        datePickerDialog.setTitle("Fecha de cita con ejecutivo: ");
        datePickerDialog.show();
    }//setFechaAlarma

    public void setHoraAlarma(View view){
        Calendar scheduleToday =  Calendar.getInstance();
        int hora = scheduleToday.get(Calendar.HOUR_OF_DAY);
        int minuto = scheduleToday.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog =  new TimePickerDialog(DateActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                horaAlarma = hourOfDay;
                minutoAlarma = minute;
            }
        },hora,minuto,true);
        //Definir titulo
        timePickerDialog.setTitle("Hora de cita con ejecutivo: ");
        timePickerDialog.show();
    }//setHoraAlarma


    public void programarAlarmaSistema(String message,int hour, int minute){
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE,message)
                .putExtra(AlarmClock.EXTRA_HOUR,hour)
                .putExtra(AlarmClock.EXTRA_MINUTES,minute);
        //if(intent.resolveActivity(getPackageManager()) != null){
        startActivity(intent);
        //}
    }
}