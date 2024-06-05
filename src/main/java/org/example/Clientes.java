package org.example;

public class Clientes{

    String nombre;
    int idCliente;

    public Clientes(){
    }

    public Clientes(String nombre, int idCliente){

        this.nombre = nombre;
        this.idCliente = idCliente;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }


    
}