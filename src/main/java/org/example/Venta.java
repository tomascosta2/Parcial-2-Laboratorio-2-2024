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
}
