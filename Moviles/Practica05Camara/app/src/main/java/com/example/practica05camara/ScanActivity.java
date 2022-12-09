package com.example.practica05camara;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ScanActivity extends AppCompatActivity {

    Button scanear, save, list;
    EditText code, description;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        scanear = findViewById(R.id.btnScanear);
        save = findViewById(R.id.btnSave);
        list = findViewById(R.id.btnView);
        code = findViewById(R.id.txtCodigo);
        description = findViewById(R.id.txtDes);

        scanear.setOnClickListener(view -> {

            IntentIntegrator intentIntegrator = new IntentIntegrator(ScanActivity.this);
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            intentIntegrator.setPrompt("Lector de Codigos");
            intentIntegrator.setCameraId(0);
            intentIntegrator.setBeepEnabled(true);
            intentIntegrator.setBarcodeImageEnabled(true);
            intentIntegrator.initiateScan();

        });

        save.setOnClickListener(view -> {

            if(code.getText().toString().isEmpty()){
                Toast.makeText(this, "Codigo Necesario", Toast.LENGTH_SHORT).show();
            }else{
                addTicket(code.getText().toString());
            }

        });

        list.setOnClickListener(view -> {

            intent = new Intent(getApplicationContext(), ListActivity.class);
            startActivity(intent);

        });

    }//onCreate

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(intentResult != null ){
            if(intentResult.getContents() == null){
                Toast.makeText(this, "Lectura cancelada.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Datos leídos.", Toast.LENGTH_SHORT).show();
                code.setText(intentResult.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }//onActivityResult

    private void addTicket(String code) {

        //Get list of internal files
        String file = "codes.txt";
        String[] fileList = fileList();
        String[] myCodes = new String[20];
        int i = 0;

        //Validate file
        if(!checkFiles(fileList, file)){
            try{
                OutputStreamWriter internalFile = new OutputStreamWriter(openFileOutput(file, Activity.MODE_PRIVATE));
                internalFile.write(code);
                internalFile.flush();
                internalFile.close();
                Toast.makeText(getApplicationContext(), code + " added in a new file", Toast.LENGTH_SHORT).show();
            } catch(IOException e) {
                Toast.makeText(getApplicationContext(), "ERROR adding code", Toast.LENGTH_LONG).show();
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
                internalFileWriter.write(code);
                internalFileWriter.flush();
                internalFileWriter.close();
                Toast.makeText(getApplicationContext(), code + " added in a exist file", Toast.LENGTH_SHORT).show();
            } catch(IOException e) {
                Toast.makeText(getApplicationContext(), "ERROR adding code", Toast.LENGTH_LONG).show();
            }
        }

    }//addTicket

    //Validate files within the device
    private boolean checkFiles (String[] filesList, String fileSearch){
        //Loop through the list of files to validate that they exist
        for (String file : filesList) {
            if (fileSearch.equals(file)) {
                return true;
            }
        }
        return false;
    }//checkFiles

}//scan