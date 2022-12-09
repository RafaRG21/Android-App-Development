package com.example.practica09ruizgudinojoserafael;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    //Instancias de objetos
    private EditText email, password;
    private CheckBox saveData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //crear un objeto de la clase TimerTask que genera un hilo para determinar cual
        //activity se ejecutara, dependiendo de las preferencias de datos almacenados
        TimerTask timerTask =  new TimerTask() {
            @Override
            public void run() {
                //Crear un objeto intent para asignarle la Activity correspondiente
                Intent intentMenu;
                //Evaluar si existen datos de preferencia almacenados en el dispositivo
                if(nuevoUsuario()){
                    //hay datos almacenados y se va al activity menu
                    intentMenu = new Intent(MainActivity.this,MenuActivity.class);
                    startActivity(intentMenu);
                    finish();
                }//if
            }//run
        };//Timertask
        Timer tiempo = new Timer();
        tiempo.schedule(timerTask,1000);
        //Asociar los componentes gráficos con las instancias
        email = (EditText) findViewById(R.id.edtUser);
        password = (EditText) findViewById(R.id.edtPassword);
        saveData = (CheckBox) findViewById(R.id.chkSaveData);
    }//onCreate
    //Método para conocer si hay datos almacenados de correo y contraseña
    //dentro del dispositivo. Para ello se utiliza la clase SharedPreferences
    //que permite almacenar valores de configuracion dentro del dispositivo movil
    private boolean nuevoUsuario(){
        //Definir el objeto donde se almacenan las preferencias
        SharedPreferences preferencias = getSharedPreferences("user.dat",MODE_PRIVATE);
        //Devuelve el estatus de existencia de informacion (true), devuelve falso en caso de no existir
        //algo almacenado previamente (sin datos almacendados previamnete)
        //registrado es el nombre del campo donde se almacena el valor lógico
        return preferencias.getBoolean("registrado",false);
    }//nuevoUsuario

    //Metodos de Login

    //Metodo para acceder al menu
    public void ingresarMenu(View view){
        //Instancia de la clase Usuario
        Usuario usuario = new Usuario(email.getText().toString().trim(),password.getText().toString().trim(),true);
        //Validar si guarda los datos como preferencia
        if(saveData.isChecked()){
            //Metodo para guardar los datos en el dispositivo
            guardarPreferencias(usuario);
        }
        //Llamada al activity principal
        Intent intent = new Intent(MainActivity.this,MenuActivity.class);
        startActivity(intent);
        finish();
    }//ingresarMenu

    public void guardarPreferencias(Usuario usuario){
        //Crear un objeto donde se almacenen las preferencias de usuario y contraseña
        SharedPreferences preferencias = getSharedPreferences("user.dat",MODE_PRIVATE);
        //Se cambia a modo edicion para guardar los valores
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("usuario",usuario.getNombreUsuario());
        editor.putString("contrasena",usuario.getContrasena());
        editor.putBoolean("registrado",usuario.isRegistrado());
        editor.apply();//Guardar los cambios
    }//guardarPreferencias

    public void salirMenu(View view){
        finish();
    }//SalirMenu
}//class