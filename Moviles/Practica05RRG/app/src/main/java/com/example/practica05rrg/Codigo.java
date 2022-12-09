package com.example.practica05rrg;

import java.io.Serializable;

public class Codigo implements Serializable {
    private String code;
    private String descripcion;

    Codigo(){
        this.code = "0";
        this.descripcion = "0";
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
