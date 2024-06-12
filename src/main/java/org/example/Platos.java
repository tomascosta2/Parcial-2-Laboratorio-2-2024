package org.example;

public class Platos {
    
    private String nombre;
    private int costo;

    public Platos(int costo, String nombre) {
        this.costo = costo;
        this.nombre = nombre;
    }

    public Platos(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }



}
