package com.example.practica05rrg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ActivityListaCodigos extends AppCompatActivity {
    TextView listCodes;
    List<String> myCodes = new ArrayList<>();
    Set<String> set = new LinkedHashSet<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_codigos);
        listCodes = findViewById(R.id.tvwLista);

        myCodes.clear();
        set.clear();
        getCodes();
        set.addAll(myCodes);
        myCodes.clear();
        myCodes.addAll(set);

    }
    public void getCodes(){

        //Get list of internal files
        String[] fileList = fileList();
        String file = "codigo.txt";
        String codigos = "";

        //Validate file
        if(checkFiles(fileList, file)){
            try{
                //Associate file to instance
                InputStreamReader internalFileReader = new InputStreamReader(openFileInput(file));
                //Instance to read file
                BufferedReader plateFile = new BufferedReader(internalFileReader);
                //Read the content of the file and put it in a variable
                String line = plateFile.readLine();
                while (line != null){
                    codigos += line + "\n";
                    line = plateFile.readLine();
                }
                plateFile.close();
                internalFileReader.close();
                listCodes.setText(codigos);
                Toast.makeText(getApplicationContext(), "Código obtenido",Toast.LENGTH_LONG).show();

            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "Error al obtener código",Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "El código no existe",Toast.LENGTH_LONG).show();
        }

    }//getFolios

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
}//Class