package com.example.practica03ruizgudinojoserafael;

import static com.example.practica03ruizgudinojoserafael.MainActivity.NOTIFICATION_ID;

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
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class FormularioActivity extends AppCompatActivity {
    EditText nom, materia;
    Button hora, fecha;
    //Identificador de alarma
    private int idAlarma = 1;
    //Valores para la alarma
    private int diaAlarma = 0;
    private int horaAlarma = 0;
    private int minutoAlarma = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        //habilitar la flecha hacia atras en la barra de estado
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Instancia para gestionar la notificación
        NotificationManagerCompat notificationManagerCompat =
                NotificationManagerCompat.from(getApplicationContext());
        //Eliminar la notificación de barra de estado
        notificationManagerCompat.cancel(NOTIFICATION_ID);
        nom = findViewById(R.id.txtNameForm);
        materia = findViewById(R.id.txtMateria);
        hora = findViewById(R.id.btnDate);
        fecha = findViewById(R.id.btnDate);
    }//onCreate


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                //Instancia de la Activity a donde se regresarà la Activity Actual
                Intent intent = new Intent(FormularioActivity.this,
                        MainActivity.class);
                startActivity(intent);
                finish();
                return (true);
        }//switch
        return super.onOptionsItemSelected(item);
    }


    public void registrarExamen(View view){
        String cita;
        cita = "Nombre: " + nom.getText().toString() + "\nMateria: " + materia.getText().toString()+"\nFecha: "+fecha.getText().toString()+"\nHora: "+hora.getText().toString();
        Toast.makeText(this, "Cita registrada a: \n" + cita,
                Toast.LENGTH_SHORT).show();
        programarAlarmaSistema("Examen "+materia.getText().toString(),horaAlarma,minutoAlarma);
        nom.setText("");
        materia.setText("");
        hora.setText("");
    }//registrarCita


    public void setFechaAlarma(View view){
        //Instancia para calendar
        Calendar calendarToday = Calendar.getInstance();
        //calendarToday.setTimeInMillis(System.currentTimeMillis());
        //Obtener los valores actuales del sistema
        int curretYear = calendarToday.get(Calendar.YEAR);
        int currentMonth = calendarToday.get(Calendar.MONTH);
        int currentDay = calendarToday.get(Calendar.DAY_OF_MONTH);
        //Fecha para elegir la cita
        DatePickerDialog datePickerDialog  = new DatePickerDialog(FormularioActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                diaAlarma = dayOfMonth;
            }
        },curretYear,currentMonth,currentDay);
        datePickerDialog.setTitle("Fecha de examen: ");
        datePickerDialog.show();
    }//setFechaAlarma


    public void setHoraAlarma(View view){
        Calendar scheduleToday =  Calendar.getInstance();
        //Obtener los valores actuales del sistema
        int curretHour = scheduleToday.get(Calendar.HOUR_OF_DAY);
        int currentMinute = scheduleToday.get(Calendar.MINUTE);
        //Horario para elegir la cita
        TimePickerDialog timePickerDialog =  new TimePickerDialog(FormularioActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                horaAlarma = hourOfDay;
                minutoAlarma = minute;
            }
        },curretHour,currentMinute,true);
        //Definir titulo
        timePickerDialog.setTitle("Hora de examen: ");
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