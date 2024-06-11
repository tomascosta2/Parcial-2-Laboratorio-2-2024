package org.example;

public class Balance {
    private double ganancias;
    private double perdidas;

    public Balance() {
        this.ganancias = 0;
        this.perdidas = 0;
    }

    public void agregarGanancia(double monto) {
        this.ganancias += monto;
    }

    public void agregarPerdida(double monto) {
        this.perdidas += monto;
    }

    public double getGanancias() {
        return ganancias;
    }

    public double getPerdidas() {
        return perdidas;
    }

    public double calcularBalance() {
        return this.ganancias - this.perdidas;
    }
}