package org.example;

import java.util.ArrayList;

public class ClientesHabituales {

    private ArrayList<Clientes> clientes;

    public ClientesHabituales(){}

    public ClientesHabituales(ArrayList<Clientes> clientes) {

        this.clientes = clientes;

    }

    public ArrayList<Clientes> getClientes() {

        return clientes;

    }

    public void setClientes(ArrayList<Clientes> clientes) {

        this.clientes = clientes;

    }

    public void agregarCliente(Clientes cliente) {

        this.clientes.add(cliente);

    }

    public void eliminarCliente(Clientes cliente) {

        this.clientes.remove(cliente);

    }

    public int obtenerIdUltimoCliente() {
        if (clientes.isEmpty()) {
            return -1; // Retorna -1 si no hay clientes en la lista
        } else {
            return clientes.get(clientes.size() - 1).getIdCliente();
        }
    }
    
}