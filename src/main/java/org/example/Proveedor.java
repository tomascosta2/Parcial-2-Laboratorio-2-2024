package org.example;

public class Proveedor {
    private int id;
    private String nombre;

    public Proveedor(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
