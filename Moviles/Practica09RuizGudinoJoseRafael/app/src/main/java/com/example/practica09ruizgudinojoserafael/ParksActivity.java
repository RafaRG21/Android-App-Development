package com.example.practica09ruizgudinojoserafael;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class ParksActivity extends AppCompatActivity {
    //Objetos de componenetes graficos
    EditText etParkCode, etParkName, etParkCapacity;
    Spinner spnParkLocation, spnParkType;
    //Obejto para gestión de la BD
    ControladorBD admin;
    ArrayAdapter<String> adapterLocation;
    ArrayAdapter<String> adapterType;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parks);
        //Asociar los objetos con componentes gráficos
        etParkCode = (EditText) findViewById(R.id.edtParkCode);
        etParkName = (EditText) findViewById(R.id.edtParkName);
        etParkCapacity = (EditText) findViewById(R.id.edtParkCapacity);
        spnParkLocation = (Spinner) findViewById(R.id.spnParkLocation);
        spnParkType = (Spinner) findViewById(R.id.spnParkType);
        //Definicion Arreglo de ciudades
        String[] lstLocation = {"Guadalajara", "Zapopan",
                "Tlaquepaque", "Tonalá", "Tlajomulco"};
        //Definicion Arreglo de tipos de parque
        String[] lstType = {"Parque Nacional","Parque Urbano","Parque lineal"};
        //Adapter para las listas
        adapterLocation = new ArrayAdapter<String>(this,
                androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, lstLocation);
        adapterType = new ArrayAdapter<>(this,
                androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, lstType);
        //Asociacion con los objetos
        spnParkType.setAdapter(adapterType);
        spnParkLocation.setAdapter(adapterLocation);
        admin = new ControladorBD(this,"parks.db",null,1);
    }//onCreate

    public void parkRegister(View view){
        SQLiteDatabase bd = admin.getWritableDatabase();
        String codep = etParkCode.getText().toString();
        String namep = etParkName.getText().toString();
        String capap = etParkCapacity.getText().toString();
        String locap = spnParkLocation.getSelectedItem().toString();
        String typep = spnParkType.getSelectedItem().toString();
        if(!codep.isEmpty() && !namep.isEmpty() && !capap.isEmpty() && !locap.isEmpty() && !typep.isEmpty()){
            ContentValues register = new ContentValues();
            register.put("parkCode",codep);
            register.put("parkName",namep);
            register.put("parkLocation",locap);
            register.put("parkCapacity",capap);
            register.put("parkType",typep);
            if (bd != null) {
                //Almacenar los valores en la tabla
                try {
                    bd.insert("park",null,register);
                }catch (SQLException e){
                    Log.e("Exception","Error"+String.valueOf(e.getMessage()));
                }
                //Cerrar la base de datos
                bd.close();
            }
            etParkCode.setText("");
            etParkName.setText("");
            etParkCapacity.setText("");
            spnParkLocation.setSelected(false);
            spnParkType.setSelected(false);
            spnParkLocation.setSelection(0);
            spnParkType.setSelection(0);
            etParkCode.requestFocus();
            //Confirmar la operación realizada
            Toast.makeText(this, "Parque Registrado", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Debes registrar primero los datos", Toast.LENGTH_SHORT).show();
        }//if-else
    }//parkRegister


    public void parkSearch(View view){
        SQLiteDatabase bd = admin.getReadableDatabase();
        String codep = etParkCode.getText().toString();
        if(!codep.isEmpty()){
            Cursor raw = bd.rawQuery("select parkName, parkLocation, parkCapacity, parkType from park"+" where parkCode="+codep,null);
            if(raw.moveToFirst()){
                etParkName.setText(raw.getString(0));
                int locationPosition = adapterLocation.getPosition(raw.getString(1));
                spnParkLocation.setSelection(locationPosition);
                etParkCapacity.setText(raw.getString(2));
                int typePosition = adapterType.getPosition(raw.getString(3));
                spnParkType.setSelection(typePosition);

            }else{
                Toast.makeText(this, "Código de parque no existe", Toast.LENGTH_SHORT).show();
                etParkCode.setText("");
                etParkCode.requestFocus();
            }
            bd.close();

        }else{
            Toast.makeText(this,"Ingresa código de parque",Toast.LENGTH_SHORT).show();
            etParkCode.requestFocus();
        }
    }//parkSearch


    public void parkUpdate(View view){
        SQLiteDatabase bd = admin.getWritableDatabase();
        String codep = etParkCode.getText().toString();
        String namep = etParkName.getText().toString();
        String capap = etParkCapacity.getText().toString();
        String locap = spnParkLocation.getSelectedItem().toString();
        String typep = spnParkType.getSelectedItem().toString();

        if(!codep.isEmpty() && !namep.isEmpty() && !capap.isEmpty() && !locap.isEmpty() && !typep.isEmpty()){
            ContentValues register = new ContentValues();
            register.put("parkCode",codep);
            register.put("parkName",namep);
            register.put("parkLocation",locap);
            register.put("parkCapacity",capap);
            register.put("parkType",typep);
            int amount = 0;
            amount = bd.update("park",register,"parkCode="+codep,null);
            bd.close();
            etParkCode.setText("");
            etParkName.setText("");
            etParkCapacity.setText("");
            spnParkLocation.setSelected(false);
            spnParkType.setSelected(false);
            spnParkLocation.setSelection(0);
            spnParkType.setSelection(0);
            etParkCode.requestFocus();

            if(amount > 0){
                Toast.makeText(this, "Datos del parque actualizados", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "El código  de parque no existe", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Debes registrar los datos primero", Toast.LENGTH_SHORT).show();
        }

    }//parkUpdate

    public void parkDelete(View view){
        SQLiteDatabase bd = admin.getWritableDatabase();
        String codep = etParkCode.getText().toString();
        if(!codep.isEmpty()){
            int amount = 0;
            amount = bd.delete("park","parkCode ="+codep,null);
            bd.close();
            etParkCode.setText("");
            etParkName.setText("");
            etParkCapacity.setText("");
            spnParkLocation.setSelected(false);
            spnParkLocation.setSelection(0);
            spnParkType.setSelection(0);
            spnParkType.setSelected(false);
            etParkCode.requestFocus();
            if(amount > 0){
                Toast.makeText(this, "Datos del parque eliminados", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "El código de empleado no existe", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Debes registrar los datos primero", Toast.LENGTH_SHORT).show();
        }

    }//parkDelete
}//class