package com.example.practica09ruizgudinojoserafael;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ParkListActivity extends AppCompatActivity {
    ControladorBD admin;
    private TextView etParkList;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_list);
        etParkList = (TextView) findViewById(R.id.txtParkListlst);
        back = (Button) findViewById(R.id.btnParkListReturn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack(v);
            }
        });
        admin = new ControladorBD(this,"parks.db",null,1);
        SQLiteDatabase bd = admin.getReadableDatabase();
        Cursor raws = bd.rawQuery("select * from park",null);
        int n = raws.getCount();
        int nr = 1;
        if(n>0){
            raws.moveToFirst();
            do{
                etParkList.setText(etParkList.getText() + "\nRegistro #"+nr);
                etParkList.setText(etParkList.getText() +"\nCódigo: "+raws.getString(0));
                etParkList.setText(etParkList.getText() +"\nNombre: "+raws.getString(1));
                etParkList.setText(etParkList.getText() +"\nCiudad: "+raws.getString(2));
                etParkList.setText(etParkList.getText() +"\nCapacidad: "+raws.getString(3));
                etParkList.setText(etParkList.getText() +"\nTipo: "+raws.getString(4));
                etParkList.setText(etParkList.getText() + "\n");
                nr++;
            }while (raws.moveToNext());
        }else{
            Toast.makeText(this, "Sin registros de parques", Toast.LENGTH_SHORT).show();
        }
        bd.close();


    }//onCreate
    public void goBack(View view){
        Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
        //Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }//goBack

}//ParkListActivity