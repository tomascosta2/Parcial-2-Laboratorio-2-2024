package org.example;

public class Plato {
    private String nombre;
    private float precio;

    // Constructores
    public Plato() {
    }
    public Plato(String nombre, float precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public float getPrecio() {
        return precio;
    }
    public void setPrecio(float precio) {
        this.precio = precio;
    }

}
