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

        Minimarket mauros = crearMinimarket("Mauros");

        try {
            // Cargar el controlador H2
            Class.forName("org.h2.Driver");

            // Establecer la conexión
            Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");

            // Crear una tabla
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS producto (id INT PRIMARY KEY, nombre VARCHAR(255), precio INT, cantidad INT)");
            ResultSet resultSet = null;

            // Cargamos los datos de la tabla en el minimarket
            resultSet = statement.executeQuery("SELECT * FROM producto");
            ArrayList<Producto> mercaderiaBase = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                int precio = resultSet.getInt("precio");
                int cantidad = resultSet.getInt("precio");

                // Crear un objeto Producto y añadirlo a la lista
                Producto product = new Producto(id, nombre, precio, cantidad);
                mercaderiaBase.add(product);
            }

            mauros.setMercaderia(mercaderiaBase);

            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        int accion;
        do {

            System.out.println("Ingrese la accion a realizar: \n" +
                    "1-Vender/Cobrar un producto\n" + // 2da (tomi - proceso)
                    "2-Ingreso de mercadería\n" + // 1ra (tomi - lista)
                    "3-Pago a proveedor\n" + // 5ta (faus)
                    "4-Consulta de ventas\n" + // 4ta (tomi - pendiente)
                    "      a- Diaria\n" +
                    "      b- Mensual\n" +
                    "5-Balance (mostrar ganancias y pérdidas)\n" + // 6ta (faus)
                    "6-Solicitar una comanda a la cocina.\n" + // 7ma (faus)
                    "7-Pagar cuenta\n" + // 3ra (tomi - pendiente)
                    "8-Informacion estadística de platos más pedidos.\n" + // 8va (faus)
                    "9-Salir"
            );

            //  Leemos la accion que el usuario quiere realizar
            accion = Integer.parseInt(sc.next());

            switch (accion) {
                case 1:
                    // Enviamos minimarket
                    vender(mauros);
                    break;
                case 2:
                    cargarMercaderia(mauros);
                    break;
                case 9:
                    System.out.println("Saliendo...");
                    break;
            }

        } while (accion != 9);

    }

    public static Minimarket crearMinimarket(String nombre) {
        Minimarket minimarket = new Minimarket(nombre);
        return minimarket;
    }

    public static void vender(Minimarket minimarket) {

        System.out.println("Que producto va a vender:");
        if (minimarket.getMercaderia().size() > 0) {
            Venta venta = new Venta();

            for (Producto producto : minimarket.getMercaderia()) {
                System.out.println("id: " + producto.getId() + " | producto: " + producto.getNombre() + " | cantidad: " + producto.getCantidad());
            }

            ArrayList<Producto> productosAVender = new ArrayList<>();

            String salir = "";

            do {

                System.out.println("Ingrese el id del producto a vender:");
                int idAVender = sc.nextInt();

                // TODO: Quitar tambien de la DB
                Producto prodAVender = minimarket.getProducto(idAVender);
                minimarket.venderMercaderia(prodAVender);

                System.out.println("Desea cargar otro producto a la venta? y/n");
                salir = sc.next();

            } while (salir.equals("y"));


            // minimarket.addVenta(venta);
        } else {
            System.out.println("!!! No hay productos para vender");
        }
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
            try {
                Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");
                Statement statement = connection.createStatement();

                statement.execute("INSERT INTO producto(id, nombre, precio, cantidad) VALUES('" + id + "','" + nombre + "','" + precio + "','" + cantidad + "')");
                
            }
            catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            }
            System.out.println("a - Ingresar otro producto\n" +
                    "b - Volver al menu principal");
        } while (sc.next().equals("a"));

        // Enviamos el array con la mercaderia nueva
        minimarket.addMercaderia(mercaderiaAAgregar);
    }

    public void menuCliente(){

        ArrayList<Clientes> clientes = new ArrayList<>();
        ClientesHabituales clientesHabituales = new ClientesHabituales(clientes);

        System.out.println("¿Que desea hacer?");
        System.out.println("1. Agregar un nuevo cliente.");
        System.out.println("2. Mostrar la lista de clientes.");
        System.out.println("3. Volver");
        int opcion = sc.nextInt();
        sc.nextLine(); //Para limpiar el buffer

        if (opcion == 1) {

            System.out.println("Ingrese el nombre completo del cliente: ");
            String nombreCli = sc.nextLine();
            int idCli = clientesHabituales.obtenerIdUltimoCliente() + 1;

            Clientes cliente = new Clientes(nombreCli, idCli);
            clientesHabituales.agregarCliente(cliente);

            System.out.println("Cliente agregado: " + cliente);

        } else if (opcion == 2){

            System.out.println("Listado de clientes: " + clientesHabituales.getClientes());

        } else {return;}

    }

}
