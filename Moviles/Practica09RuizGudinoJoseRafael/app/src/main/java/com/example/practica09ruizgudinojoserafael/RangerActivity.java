package com.example.practica09ruizgudinojoserafael;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RangerActivity extends AppCompatActivity {
    EditText etRangerCode, etRangerName, etRangerLastname, etRangerSalary;
    Spinner spnRangerShift;
    ControladorBD admin;
    ArrayAdapter<String> adapterShifts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranger);
        etRangerCode = (EditText) findViewById(R.id.edtRangerCode);
        etRangerName = (EditText) findViewById(R.id.edtRangerName);
        etRangerLastname = (EditText) findViewById(R.id.edtRangerLastname);
        etRangerSalary = (EditText) findViewById(R.id.edtRangerSalary);
        spnRangerShift = (Spinner) findViewById(R.id.spnRangerShift);
        String[] lstShifts = {"Matutino","Vespertino","Nocturno"};
        adapterShifts = new ArrayAdapter<String>(this,
                androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, lstShifts);
        spnRangerShift.setAdapter(adapterShifts);
        admin = new ControladorBD(this,"parks.db",null,1);
    }//onCreate

    public void rangerRegister(View view){
        SQLiteDatabase bd = admin.getWritableDatabase();
        String codep = etRangerCode.getText().toString();
        String namep = etRangerName.getText().toString();
        String lastp = etRangerLastname.getText().toString();
        String shiftp = spnRangerShift.getSelectedItem().toString();
        String salarp = etRangerSalary.getText().toString();
        if(!codep.isEmpty() && !namep.isEmpty() && !lastp.isEmpty() && !shiftp.isEmpty() && !salarp.isEmpty()){
            ContentValues register = new ContentValues();
            register.put("rangerCode",codep);
            register.put("rangerName",namep);
            register.put("rangerLastName",lastp);
            register.put("rangerShift",shiftp);
            register.put("rangerSalary",salarp);
            if (bd != null) {
                //Almacenar los valores en la tabla
                try {
                    bd.insert("ranger",null,register);
                }catch (SQLException e){
                    Log.e("Exception","Error"+String.valueOf(e.getMessage()));
                }
                //Cerrar la base de datos
                bd.close();
            }
            etRangerCode.setText("");
            etRangerName.setText("");
            etRangerLastname.setText("");
            etRangerSalary.setText("");
            spnRangerShift.setSelected(false);
            spnRangerShift.setSelection(0);
            etRangerCode.requestFocus();
            //Confirmar la operación realizada
            Toast.makeText(this, "Guardabosques Registrado", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Debes registrar primero los datos", Toast.LENGTH_SHORT).show();
        }//if-else
    }//rangerRegister


    public void rangerSearch(View view){
        SQLiteDatabase bd = admin.getReadableDatabase();
        String codep = etRangerCode.getText().toString();
        if(!codep.isEmpty()){
            Cursor raw = bd.rawQuery("select rangerName, rangerLastName, rangerShift, rangerSalary from ranger"+" where rangerCode="+codep,null);
            if(raw.moveToFirst()){
                etRangerName.setText(raw.getString(0));
                etRangerLastname.setText(raw.getString(1));
                int locationPosition = adapterShifts.getPosition(raw.getString(2));
                spnRangerShift.setSelection(locationPosition);
                etRangerSalary.setText(raw.getString(3));

            }else{
                Toast.makeText(this, "Código de guardabosque no existe", Toast.LENGTH_SHORT).show();
                etRangerCode.setText("");
                etRangerCode.requestFocus();
            }
            bd.close();

        }else{
            Toast.makeText(this,"Ingresa código de guardabosque",Toast.LENGTH_SHORT).show();
            etRangerCode.requestFocus();
        }
    }//rangerSearch


    public void rangerUpdate(View view){
        SQLiteDatabase bd = admin.getWritableDatabase();
        String codep = etRangerCode.getText().toString();
        String namep = etRangerName.getText().toString();
        String lastp = etRangerLastname.getText().toString();
        String shiftp = spnRangerShift.getSelectedItem().toString();
        String salarp = etRangerSalary.getText().toString();

        if(!codep.isEmpty() && !namep.isEmpty() && !lastp.isEmpty() && !shiftp.isEmpty() && !salarp.isEmpty()){
            ContentValues register = new ContentValues();
            register.put("rangerCode",codep);
            register.put("rangerName",namep);
            register.put("rangerLastName",lastp);
            register.put("rangerShift",shiftp);
            register.put("rangerSalary",salarp);
            int amount = 0;
            amount = bd.update("ranger",register,"rangerCode="+codep,null);
            bd.close();
            etRangerCode.setText("");
            etRangerName.setText("");
            etRangerLastname.setText("");
            etRangerSalary.setText("");
            spnRangerShift.setSelected(false);
            spnRangerShift.setSelection(0);
            etRangerCode.requestFocus();

            if(amount > 0){
                Toast.makeText(this, "Datos del guardabosque actualizados", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "El código  de guardabosque no existe", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Debes registrar los datos primero", Toast.LENGTH_SHORT).show();
        }

    }//rangerUpdate

    public void rangerDelete(View view){
        SQLiteDatabase bd = admin.getWritableDatabase();
        String codep = etRangerCode.getText().toString();
        if(!codep.isEmpty()){
            int amount = 0;
            amount = bd.delete("ranger","rangerCode ="+codep,null);
            bd.close();
            etRangerCode.setText("");
            etRangerName.setText("");
            etRangerLastname.setText("");
            etRangerSalary.setText("");
            spnRangerShift.setSelected(false);
            spnRangerShift.setSelection(0);
            etRangerCode.requestFocus();
            if(amount > 0){
                Toast.makeText(this, "Datos del guardabosque eliminados", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "El código de guardabosque no existe", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Debes registrar los datos primero", Toast.LENGTH_SHORT).show();
        }

    }//rangerDelete
}//RangerActivity