package com.example.practica09ruizgudinojoserafael;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }//onCreate
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Asociar el XML del menuoverflow con el Activity
        getMenuInflater().inflate(R.menu.menu_overflow,menu);
        return true;
    }//onCreateOptionsMenu


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int option = item.getItemId();
        switch (option){
            case R.id.itmParks:
                Intent intentParks = new Intent(this,ParksActivity.class);
                startActivity(intentParks);
                break;
            case R.id.itmParkLst:
                Intent intentParkList = new Intent(this,ParkListActivity.class);
                startActivity(intentParkList);
                break;
            case R.id.itmRanger:
                Intent intentRanger = new Intent(this,RangerActivity.class);
                startActivity(intentRanger);
                break;
            case R.id.itmRangerLst:
                Intent intentRangerList = new Intent(this,RangerListActivity.class);
                startActivity(intentRangerList);
                break;
            case R.id.itmSignOut:
                signOut();
                break;
        }//switch
        return super.onOptionsItemSelected(item);
    }//onOptionsItemSelected


    public void signOut(){
        SharedPreferences preferencias = getSharedPreferences("user.dat",MODE_PRIVATE);
        //Poner en modo editor para borrar los datos
        SharedPreferences.Editor editor = preferencias.edit();
        editor.clear(); //Se borran los datos almacenados
        editor.apply(); // Se guardan los cambios
        //Regresar a Activity Login
        Intent intent =  new Intent(this,MainActivity.class);
        //Inicializar las banderas para comenzar la Activity, como si fuera la primera vez
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }//cerrarSesion


}//class