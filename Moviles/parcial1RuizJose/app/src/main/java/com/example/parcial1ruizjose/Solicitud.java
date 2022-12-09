package com.example.parcial1ruizjose;

public class Solicitud {
    private String curp;
    private String nombre;
    private String apellidos;
    private String domicilio;
    private int cantidad_ingreso;
    private String tipo_tarjeta;

    public Solicitud(String curp, String nombre, String apellidos, String domicilio, int cantidad_ingreso, String tipo_tarjeta) {
        this.curp = curp;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.domicilio = domicilio;
        this.cantidad_ingreso = cantidad_ingreso;
        this.tipo_tarjeta = tipo_tarjeta;
    }
    public Solicitud(){
        this.curp = "curp";
        this.nombre = "nombre";
        this.apellidos = "apellidos";
        this.domicilio = "domicilio";
        this.cantidad_ingreso = -1;
        this.tipo_tarjeta = "tradicional";
    }
    public boolean validar(){
        String tipo = getTipo_tarjeta();
        Boolean validacion = false;
        switch (tipo){
            case "tradicional":
                if(getCantidad_ingreso()>=6000)
                    validacion = true;
                break;
            case "oro":
                if(getCantidad_ingreso()>=15000)
                    validacion = true;
                break;
            case "platino":
                if(getCantidad_ingreso()>=24000)
                    validacion = true;
                break;
        }
        return validacion;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public int getCantidad_ingreso() {
        return cantidad_ingreso;
    }

    public void setCantidad_ingreso(int cantidad_ingreso) {
        this.cantidad_ingreso = cantidad_ingreso;
    }

    public String getTipo_tarjeta() {
        return tipo_tarjeta;
    }

    public void setTipo_tarjeta(String tipo_tarjeta) {
        this.tipo_tarjeta = tipo_tarjeta;
    }
}//class
