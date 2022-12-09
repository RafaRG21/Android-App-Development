package com.example.practica05rrg;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class ActivityLector extends AppCompatActivity {
    //instancias
    private EditText resultado, descripcion;
    ArrayList<Codigo> codigo = new ArrayList<Codigo>();
    int cont = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lector);
        //Asociar componentes
        resultado = findViewById(R.id.edtResultado);
        descripcion =  findViewById(R.id.edtDescripcion);
    }//onCreate

    public void escanearCodigoBarra(View v){
        IntentIntegrator intentIntegrator = new IntentIntegrator(ActivityLector.this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setPrompt("Lector - CDP");
        intentIntegrator.setCameraId(0);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setBarcodeImageEnabled(true);
        intentIntegrator.initiateScan();
    }//escanearCodigoBarra


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        //Validar que si recibe informacion
        if(intentResult!=null){
            if(intentResult.getContents()==null){
                Toast.makeText(this, "Lectura cancelada", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Datos leídos", Toast.LENGTH_SHORT).show();
                resultado.setText(intentResult.getContents());
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }//onActivityResult


    public void guardar(View view){
        if(!resultado.getText().toString().isEmpty() && !descripcion.getText().toString().isEmpty()){
            guardarCodigo(resultado.getText().toString(),descripcion.getText().toString());
            Toast.makeText(this, "Código agregado", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Llene los dos campos", Toast.LENGTH_SHORT).show();
        }
    }//guardar


    public void verCodigo(View view){
        Intent intent = new Intent(getApplicationContext(),ActivityListaCodigos.class);
        startActivity(intent);
    }

    private void guardarCodigo(String codigo,String descripcion){
        //Get list of internal files
        String file = "codigo.txt";
        String[] fileList = fileList();
        String[] myCodes = new String[20];
        int i = 0;

        //Validate file
        if(!verificarArchivo(fileList, file)){
            try{
                OutputStreamWriter internalFile = new OutputStreamWriter(openFileOutput(file, Activity.MODE_PRIVATE));
                internalFile.write(codigo+" - "+descripcion);
                internalFile.flush();
                internalFile.close();
                Toast.makeText(getApplicationContext(), codigo + " creado", Toast.LENGTH_SHORT).show();
            } catch(IOException e) {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
            }
        }else{
            try{
                InputStreamReader internalFileReader = new InputStreamReader(openFileInput(file));
                BufferedReader codesFile = new BufferedReader(internalFileReader);

                String line = codesFile.readLine();
                while (line != null){
                    myCodes[i] = line;
                    line = codesFile.readLine();
                    i++;
                }
                codesFile.close();
                internalFileReader.close();

                OutputStreamWriter internalFileWriter = new OutputStreamWriter(openFileOutput(file, Activity.MODE_PRIVATE));
                i = 0;

                while (myCodes[i] != null){
                    internalFileWriter.write(myCodes[i] + "\n");
                    internalFileWriter.flush();
                    i++;
                }
                internalFileWriter.write(codigo+" - "+descripcion);
                internalFileWriter.flush();
                internalFileWriter.close();
                Toast.makeText(getApplicationContext(), codigo + "agregado", Toast.LENGTH_SHORT).show();
            } catch(IOException e) {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
            }
        }
        resultado.setText("");
        this.descripcion.setText("");
    }//guardarCodigo

    private boolean verificarArchivo (String[] filesList, String fileSearch){
        //Loop through the list of files to validate that they exist
        for (String file : filesList) {
            if (fileSearch.equals(file)) {
                return true;
            }
        }
        return false;
    }//checkFiles
}//class