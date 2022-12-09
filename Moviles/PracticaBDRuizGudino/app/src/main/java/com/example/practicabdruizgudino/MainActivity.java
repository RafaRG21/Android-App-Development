package com.example.practicabdruizgudino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Objetos de componenetes graficos
    EditText etNumEp, etNombre, etApellido, etSueldo;
    //Obejto para gestión de la BD
    ControladorBD admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Asociar los objetos con componentes gráficos
        etNumEp = (EditText) findViewById(R.id.txtNumEp);
        etNombre = (EditText) findViewById(R.id.txtNombre);
        etApellido = (EditText) findViewById(R.id.txtApellido);
        etSueldo = (EditText) findViewById(R.id.txtSueldo);
        //Creación de objeto de clase ControladorBD para crear la base de datos
        //Sus parámetros son: contexto, nombre de la Base de Datos, null y versión de la BD
        //La ruta de creación de la base de datos en el dispositivo es:
        // android/data/<paquete>/databases/<nombre_bd>
        admin = new ControladorBD(this,"empresapatito.db",null,1);
    }//onCreate

    public void registrarEmpleado(View view){
        //Establecer el modo de apertura de la base de datos en modo Escritura
        SQLiteDatabase bd = admin.getWritableDatabase();
        //Variables para obtener los valores de componentes gráficos
        String nump = etNumEp.getText().toString();
        String nomp = etNombre.getText().toString();
        String apep = etApellido.getText().toString();
        String suep = etSueldo.getText().toString();

        //Validar que exista información registrada
        if(!nump.isEmpty() && !nomp.isEmpty() && !apep.isEmpty() && !suep.isEmpty()){
            //Objeto que almacena los valores para enviar a la tabla
            ContentValues registro = new ContentValues();
            //Referencias a los datos que pasar a la BD
            //indicando como parámetos de put el nombre del campo y el valor a insertar
            //El segundo proviene de los campos de texto
            registro.put("numemp21645",nump);
            registro.put("nombre",nomp);
            registro.put("apellidos",apep);
            registro.put("sueldo",suep);

            if (bd != null) {
                //Almacenar los valores en la tabla
                try {
                    bd.insert("empleado",null,registro);
                }catch (SQLException e){
                    Log.e("Exception","Error"+String.valueOf(e.getMessage()));
                }
                //Cerrar la base de datos
                bd.close();
            }
            //Limpiar los campos de texto
            etNumEp.setText("");
            etNombre.setText("");
            etApellido.setText("");
            etSueldo.setText("");
            etNumEp.requestFocus();
            //Confirmar la operación realizada
            Toast.makeText(this, "Empleado Registrado", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Debes registrar primero los datos", Toast.LENGTH_SHORT).show();
        }
    }//registrarEmpleado

    public void buscarEmpleado(View view){
        //Establecer el modo de apertura de la base de datos en modo Escritura
       SQLiteDatabase bd = admin.getReadableDatabase();
        //Variable para busqueda de dato para obtener información
       String nump = etNumEp.getText().toString();
        //Validar que el campo no este vacio
       if(!nump.isEmpty()){
           //Objeto apunta al registro donde localice el dato, se le envia la instrucción sql de busqueda
           Cursor fila = bd.rawQuery("select nombre, apellidos, sueldo from empleado"+" where numemp="+nump,null);
           //Valida
           if(fila.moveToFirst()){
               //Se coloca en los componentes los valores encontrados
               etNombre.setText(fila.getString(0));
               etApellido.setText(fila.getString(1));
               etSueldo.setText(fila.getString(2));
               //Se cierra la base de datos
           }else{
               Toast.makeText(this, "Número de empleado no existe", Toast.LENGTH_SHORT).show();
               etNumEp.setText("");
               etNumEp.requestFocus();
           }
           bd.close();
       }else {
           Toast.makeText(this,"Ingresa número de empleado",Toast.LENGTH_SHORT).show();
           etNumEp.requestFocus();
       }//else
    }//buscarEmpleado

    public void actualizarEmpleado(View view){
        //Establecer el modo de apertura de la base de datos en modo Escritura
        SQLiteDatabase bd = admin.getWritableDatabase();
        //Variables para obtener los valores de componentes gráficos
        String nump = etNumEp.getText().toString();
        String nomp = etNombre.getText().toString();
        String apep = etApellido.getText().toString();
        String suep = etSueldo.getText().toString();
        //Validar que exista información registrada
        if(!nump.isEmpty() && !nomp.isEmpty() && !apep.isEmpty() && !suep.isEmpty() ){
            //Objeto que almacena los valores para enviar a la tabla
            ContentValues registro = new ContentValues();
            //Referencias a los datos que pasar a la BD
            //indicando como parámetos de put el nombre del campo y el valor a insertar
            //El segundo proviene de los campos de texto
            registro.put("numemp",nump);
            registro.put("nombre",nomp);
            registro.put("apellidos",apep);
            registro.put("sueldo",suep);
            //variable que indica el número de registros actualizados
            int cantidad = 0;
            //La instruccion update requiere parametros para realizar la actualización de datos, estos son:
            //tabla, información por actualizar, clasula whete (condición) y sin parámetros para la clausula
            cantidad = bd.update("empleado",registro,"numemp="+nump,null);
            //Cerrar la base de datos
            bd.close();
            //Limpiar los campos de texto
            etNumEp.setText("");
            etNombre.setText("");
            etApellido.setText("");
            etSueldo.setText("");
            etNumEp.requestFocus();
            //Validar si existieron registros a borrar
            if(cantidad > 0){
                Toast.makeText(this, "Datos del empleado actualizados", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "El número  de empleado no existe", Toast.LENGTH_SHORT).show();
            }


        }else{
            Toast.makeText(this, "Debes registrar los datos primero", Toast.LENGTH_SHORT).show();
        }
    }//actualizarEmpleado


    public void eliminarEmpleado(View view){
        //Establecer el modo de apertura de la base de datos en modo escritura
        SQLiteDatabase bd = admin.getWritableDatabase();
        //Variable para busqueda de dato para eliminar
        String nump = etNumEp.getText().toString();
        //Validar que exista el campo no este vacío
        if(!nump.isEmpty()){
            //variable que almacena el número de registros borrados
            int cantidad = 0;
            //La instruccion delete requiere parametros para realizar el borrado, estos son:
            //tabla, clasula whete (condición) y sin parámetros para la clausula
            cantidad = bd.delete("empleado","numemp ="+nump,null);
            //Cerrar la base de datos
            bd.close();
            //limpiar los campos del formulario
            etNumEp.setText("");
            etNombre.setText("");
            etApellido.setText("");
            etSueldo.setText("");
            etNumEp.requestFocus();
            //Validar si existieron registros a borrar
            if(cantidad > 0){
                Toast.makeText(this, "Datos del empleado eliminados", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "El número  de empleado no existe", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Debes registrar los datos primero", Toast.LENGTH_SHORT).show();
        }

    }//eliminarEmpleado


    public void listarEmpleados(View view){
        //Objeto para conectar a otra Activity
        Intent intent = new Intent(this, ListadoActivity.class);
        //Iniciar la Activity
        startActivity(intent);
    }//listarEmpleados
 }//class