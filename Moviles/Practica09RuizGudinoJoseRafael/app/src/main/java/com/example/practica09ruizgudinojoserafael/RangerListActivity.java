package com.example.practica09ruizgudinojoserafael;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RangerListActivity extends AppCompatActivity {
    ControladorBD admin;
    private TextView etRangerList;
    Button back;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranger_list);
        etRangerList = (TextView) findViewById(R.id.txtRangerListlst);
        back = (Button) findViewById(R.id.btnRangerListReturn);
        back.setOnClickListener(v -> goBack(v));
        admin = new ControladorBD(this,"parks.db",null,1);
        SQLiteDatabase bd = admin.getReadableDatabase();
        Cursor raws = bd.rawQuery("select * from ranger",null);
        int n = raws.getCount();
        int nr = 1;
        if(n>0){
            raws.moveToFirst();
            do{
                etRangerList.setText(etRangerList.getText() + "\nRegistro #"+nr);
                etRangerList.setText(etRangerList.getText() +"\nCódigo: "+raws.getString(0));
                etRangerList.setText(etRangerList.getText() +"\nNombre: "+raws.getString(1));
                etRangerList.setText(etRangerList.getText() +"\nApellido: "+raws.getString(2));
                etRangerList.setText(etRangerList.getText() +"\nTurno: "+raws.getString(3));
                etRangerList.setText(etRangerList.getText() +"\nSueldo: "+raws.getString(4));
                etRangerList.setText(etRangerList.getText() + "\n");
                nr++;
            }while (raws.moveToNext());
        }else{
            Toast.makeText(this, "Sin registros de guardabosques", Toast.LENGTH_SHORT).show();
        }
        bd.close();

    }//onCreate

    public void goBack(View view){
        Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
        //Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }//goBack
}