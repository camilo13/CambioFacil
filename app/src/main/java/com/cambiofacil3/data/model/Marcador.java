package com.cambiofacil3.data.model;

public class Marcador {

    private Double latitude;

    private Double longitud;

    private String nombre;

    private String distrito;

    public Marcador() {
    }

    public Marcador(Double latitude, Double longitud, String nombre, String distrito) {
        this.latitude = latitude;
        this.longitud = longitud;
        this.nombre = nombre;
        this.distrito = distrito;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitud() {
        return longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDistrito() {
        return distrito;
    }
}
