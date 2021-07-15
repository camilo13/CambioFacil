package com.cambiofacil3.data.model;

import java.util.List;

public class Usuario {

    private String usuario;

    private String nombre;

    private String apellido;

    private String clave;

    private int edad;

    private List<Marcador> marcadores;

    public Usuario() {
    }

    public String getUsuario() {
        return usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getClave() {
        return clave;
    }

    public int getEdad() {
        return edad;
    }

    public List<Marcador> getMarcadores() {
        return marcadores;
    }

    public Usuario(String usuario, String nombre, String apellido, String clave, int edad, List<Marcador> marcadores) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.clave = clave;
        this.edad = edad;
        this.marcadores = marcadores;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setMarcadores(List<Marcador> marcadores) {
        this.marcadores = marcadores;
    }
}
