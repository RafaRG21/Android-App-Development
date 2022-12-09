package com.example.practicabdruizgudino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ListadoActivity extends AppCompatActivity {
    //Instancia del controlador
    ControladorBD admin;
    //Instancias de componentes
    private TextView etListado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
        //Asociar la instancia con el componente
        etListado = (TextView)  findViewById(R.id.txtDetalle);
        //Creación de la base de datos, de manera local, cuyo parametros son:
        //contexto de la aplicación, nombre de la BD, versión
        admin = new ControladorBD(this,"empresapatito.db",null,1);
        //Define el modo de acceso a la BD
        SQLiteDatabase bd = admin.getReadableDatabase();
        //Instancia del apuntador al registro de busqueda
        Cursor registros = bd.rawQuery("select * from empleado",null);
        //Variable para la cantidad de registro obtenidos
        int n = registros.getCount();
        //Variable para control de datos en el TextView
        int nr = 1;
        //Valido que existan registros de la BD
        if(n>0){
            //Mover el cursor al inicio de los registro obtenidos
            registros.moveToFirst();
            //Ciclo repetitivo para colocar la información dentro del TextView
            do{
                etListado.setText(etListado.getText() + "\nRegistro #"+nr);
                etListado.setText(etListado.getText() +"\nNúmero: "+registros.getString(0));
                etListado.setText(etListado.getText() +"\nNombre: "+registros.getString(1));
                etListado.setText(etListado.getText() +"\nApellidos: "+registros.getString(2));
                etListado.setText(etListado.getText() +"\nSueldo: "+registros.getString(3));
                etListado.setText(etListado.getText() + "\n");
                nr++;
            }while(registros.moveToNext()); //Si existen más registros
        }else{
            //Mensaje informativo que no hay campos
            Toast.makeText(this, "Sin registros de empleados", Toast.LENGTH_SHORT).show();
        }
        //Cerrando la BD
        bd.close();
    }//onCreate
    public void regresarPrincipal(View view){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        //Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }//regresarPrincipal


}//class