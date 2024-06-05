package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // Creamos el primer minimarket del sistema
        Minimarket mauros = crearMinimarket("Mauros");

        do {

            System.out.println("Ingrese la accion a realizar: \n" +
                    "1-Vender/Cobrar un producto\n" +
                    "2-Ingreso de mercadería\n" +
                    "3-Pago a proveedor\n" +
                    "4-Consulta de ventas\n" +
                    "      a- Diaria\n" +
                    "      b- Mensual\n" +
                    "5-Balance (mostrar ganancias y pérdidas)\n" +
                    "6-Solicitar una comanda a la cocina.\n" +
                    "7-Pagar cuenta\n" +
                    "8-Informacion estadística de platos más pedidos.\n" +
                    "9-Salir"
            );

            //  Leemos la accion que el usuario quiere realizar
            int accion = Integer.parseInt(sc.next());

            switch (accion) {
                case 1:
                    // Enviamos minimarket
                    vender(mauros);
                    break;
                case 2:
                    cargarMercaderia(mauros);
                    break;
            }

        } while (!sc.next().equals("9"));

    }

    public static Minimarket crearMinimarket(String nombre) {
        Minimarket minimarket = new Minimarket(nombre);
        return minimarket;
    }

    public static void vender(Minimarket minimarket) {
        Venta venta = new Venta();

        System.out.println("Que producto va a vender:");
        for (Producto producto : minimarket.getMercaderia()) {
            System.out.println("producto" + producto.getNombre() + " cantidad: " + producto.getCantidad());
        }

        minimarket.addVenta(venta);
    }

    public static void cargarMercaderia(Minimarket minimarket) {

        // Creamos una lista de productos a enviar a la mercaderia del minimarket
        ArrayList<Producto> mercaderiaAAgregar = new ArrayList<>();

        do {
            // Pedimos los datos sobre el producto
            System.out.println("ID del producto");
            int id = sc.nextInt();
            System.out.println("Nombre del producto: ");
            String nombre = sc.next();
            System.out.println("Precio del producto: ");
            int precio = sc.nextInt();
            System.out.println("Cantidad del producto: ");
            int cantidad = sc.nextInt();

            /* Creamos el producto y lo agregamos al array de productos que posteriormente vamos
            a enviar a la mercaderia del minimarket */
            Producto producto = new Producto(id, nombre, precio, cantidad);
            mercaderiaAAgregar.add(producto);

            System.out.println("a - Ingresar otro producto\n" +
                    "b - Volver al menu principal");
        } while (sc.next().equals("a"));

        // Enviamos el array con la mercaderia nueva
        minimarket.addMercaderia(mercaderiaAAgregar);
    }
}