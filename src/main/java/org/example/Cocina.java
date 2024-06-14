package org.example;

import java.util.HashMap;
import java.util.Map;

public class Cocina {
    private final Map<Plato, Integer> pedidos;
    private final Map<String, Plato> menu;
    private final Balance balance;

    public Cocina(Balance balance) {
        pedidos = new HashMap<>();
        menu = new HashMap<>();
        this.balance = balance;
        inicializarMenu();
    }

    private void inicializarMenu() {
        Plato pizza = new Plato("Pizza", 1800);
        Plato hamburguesa = new Plato("Hamburguesa", 1600);
        Plato ensalada = new Plato("Ensalada", 1400);

        agregarPlatoAlMenu(pizza);
        agregarPlatoAlMenu(hamburguesa);
        agregarPlatoAlMenu(ensalada);
    }

    public void agregarPlatoAlMenu(Plato plato) {
        if (plato != null && plato.getNombre() != null) {
            menu.put(plato.getNombre(), plato);
        }
    }

    public Plato obtenerPlatoDelMenu(String nombre) {
        return menu.get(nombre);
    }

    public void pedirPlato(String nombre, int cantidad) {
        Plato plato = obtenerPlatoDelMenu(nombre);
        if (plato != null) {
            pedidos.put(plato, pedidos.getOrDefault(plato, 0) + cantidad);
            balance.agregarGanancia(plato.getCosto() * cantidad);
        } else {
            System.out.println("El plato " + nombre + " no está en el menú.");
        }
    }

    public void mostrarPedidos() {
        System.out.println("Pedidos:");
        for (Map.Entry<Plato, Integer> entry : pedidos.entrySet()) {
            System.out.println(entry.getKey().getNombre() + " - Cantidad: " + entry.getValue());
        }
    }

    public void mostrarMenu() {
        System.out.println("Menú:");
        for (Plato plato : menu.values()) {
            System.out.println(plato.getNombre() + " - Precio: $" + plato.getCosto());
        }
    }
}