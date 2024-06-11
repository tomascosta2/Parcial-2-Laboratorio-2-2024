package org.example;

import java.util.ArrayList;

public class Minimarket {

    private String nombre;
    private ArrayList<Venta> ventas;
    private ArrayList<Producto> mercaderia;

    public Minimarket(String nombre) {
        this.nombre = nombre;
    }

    public void addVenta(Venta venta) {
        this.ventas.add(venta);
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Venta> getVentas() {
        return ventas;
    }
    public void setVentas(ArrayList<Venta> ventas) {
        this.ventas = ventas;
    }

    public ArrayList<Producto> getMercaderia() {
        return mercaderia;
    }
    public void setMercaderia(ArrayList<Producto> mercaderia) {
        this.mercaderia = mercaderia;
    }

    public void addMercaderia(ArrayList<Producto> productos) {
        System.out.println(productos.size());
        for (Producto producto : productos) {
            System.out.println("Producto agregado: " + producto.getNombre());
            this.mercaderia.add(producto);
        }
    }

    public Producto getProducto(int idAVender) {
        for (Producto producto : this.mercaderia) {
            if (producto.getId() == idAVender) {
                return producto;
            }
        }
        return null;
    }

    public void venderMercaderia(Producto productoAVender) {
        for (Producto producto : this.mercaderia) {
            if (producto.getId() == productoAVender.getId()) {
                productoAVender.setCantidad(productoAVender.getCantidad() - 1);
            }
        }
    }

    public void venderMercaderia(Producto producto, int cantidadAVender) {
        producto.setCantidad(producto.getCantidad() - cantidadAVender);
    }
}
