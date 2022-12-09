package com.example.practica09ruizgudinojoserafael;

public class Usuario {
    //Atributos
    private String nombreUsuario;
    private String contrasena;
    private boolean registrado;
    //Constructores

    public Usuario(String nombreUsuario, String contrasena, boolean registrado) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.registrado = registrado;
    }
    public Usuario() {
        this.nombreUsuario = "none";
        this.contrasena = "none";
        this.registrado = false;
    }
    //Getters y Setters
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean isRegistrado() {
        return registrado;
    }

    public void setRegistrado(boolean registrado) {
        this.registrado = registrado;
    }


}