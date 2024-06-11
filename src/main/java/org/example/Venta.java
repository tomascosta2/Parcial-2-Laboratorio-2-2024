package org.example;

import java.util.ArrayList;

public class Venta {
    // Lista de productos vendidos
    private ArrayList<Producto> productos;
    private int total;

    public Venta() {
    }

    public Venta(ArrayList<Producto> productos, int total) {
        this.productos = productos;
        this.total = total;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
