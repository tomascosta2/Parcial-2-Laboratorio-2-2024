package org.example;

import java.util.HashMap;
import java.util.Map;

public class Cocina {
    private Map<Plato, Integer> pedidos;
    private Map<String, Plato> menu;

    public Cocina() {
        pedidos = new HashMap<>();
        menu = new HashMap<>();
        inicializarMenu();
    }

    private void inicializarMenu() {
        agregarPlatoAlMenu(new Plato("Pizza", 8.99));
        agregarPlatoAlMenu(new Plato("Hamburguesa", 6.99));
        agregarPlatoAlMenu(new Plato("Ensalada", 5.99));
    }

    public void agregarPlatoAlMenu(Plato plato) {
        menu.put(plato.getNombre(), plato);
    }

    public Plato obtenerPlatoDelMenu(String nombre) {
        return menu.get(nombre);
    }

    public void pedirPlato(String nombre, int cantidad) {
        Plato plato = obtenerPlatoDelMenu(nombre);
        if (plato != null) {
            pedidos.put(plato, pedidos.getOrDefault(plato, 0) + cantidad);
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


