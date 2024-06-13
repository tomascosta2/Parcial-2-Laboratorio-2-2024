package org.example;

public class Plato {
    
    private String nombre;
    private int costo;

    public Plato(String nombre, int costo) {
       
        this.nombre = nombre;
        this.costo = costo;
        
    }

    public Plato(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    @Override
    public String toString() {
        return "Plato{" +
                "nombre='" + nombre + '\'' +
                ", precio=" + costo +
                '}';
    }

}
