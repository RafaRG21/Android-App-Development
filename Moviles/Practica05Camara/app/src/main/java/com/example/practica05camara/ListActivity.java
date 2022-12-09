package com.example.practica05camara;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ListActivity extends AppCompatActivity {

    ListView listCodes;
    List<String> myCodes = new ArrayList<>();
    Set<String> set = new LinkedHashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listCodes = findViewById(R.id.lstCodes);

        myCodes.clear();
        set.clear();
        getCodes();
        set.addAll(myCodes);
        myCodes.clear();
        myCodes.addAll(set);

        //ArrayAdapter<String> ticketsAdapter = new ArrayAdapter<>(this,  R.layout.list_item, myCodes);
        ArrayAdapter<String> codesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myCodes);
        listCodes.setAdapter(codesAdapter);
    }//onCreate

    public void getCodes(){

        //Get list of internal files
        String[] fileList = fileList();
        String file = "codes.txt";

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
                    myCodes.add(line);
                    line = plateFile.readLine();
                }
                plateFile.close();
                internalFileReader.close();
                Toast.makeText(getApplicationContext(), "CODIGOS OBTENIDOS CORRECTAMENTE",Toast.LENGTH_LONG).show();

            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "ERROR loading codes",Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "ERROR the code doesn't exist",Toast.LENGTH_LONG).show();
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

}//List