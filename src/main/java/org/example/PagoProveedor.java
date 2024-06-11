package org.example;

public class PagoProveedor {

    private Proveedor proveedor;
    private double monto;

    public PagoProveedor(Proveedor proveedor, double monto) {
        this.proveedor = proveedor;
        this.monto = monto;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public double getMonto() {
        return monto;
    }

}
