package org.example;

import java.util.ArrayList;
import java.util.Scanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            // Cargar el controlador H2
            Class.forName("org.h2.Driver");

            // Establecer la conexión
            Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");

            // Crear una tabla
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE example(id INT PRIMARY KEY, name VARCHAR(255))");

            // Insertar datos
            statement.execute("INSERT INTO example(id, name) VALUES(1, 'CocaCola')");
            statement.execute("INSERT INTO example(id, name) VALUES(2, 'Pepsi')");

            // Consultar datos
            ResultSet resultSet = statement.executeQuery("SELECT * FROM example");
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id") + ", Name: " + resultSet.getString("name"));
            }

            // Cerrar conexiones
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        // Creamos el primer minimarket del sistema
        Minimarket mauros = crearMinimarket("Mauros");
        ArrayList<Producto> mercaderiaBase = new ArrayList<>();
        mauros.setMercaderia(mercaderiaBase);

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
        if (minimarket.getMercaderia().size() > 0) {
            for (Producto producto : minimarket.getMercaderia()) {
                System.out.println("producto" + producto.getNombre() + " cantidad: " + producto.getCantidad());
            }
        } else {
            System.out.println("No hay productos para vender");
        }

        minimarket.addVenta(venta);
    }

    public static void cargarMercaderia(Minimarket minimarket) {

        // Creamos una lista de productos a enviar a la mercaderia del minimarket
        ArrayList<Producto> mercaderiaAAgregar = new ArrayList<>();
        //if (minimarket.getMercaderia().size() > 0) {

        //}
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