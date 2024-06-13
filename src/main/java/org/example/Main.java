package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:tcp://localhost/~/test";
    private static final String USER = "sa";
    private static final String PASS = "";

    public static Scanner sc = new Scanner(System.in);
    private static Balance balance = new Balance();

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
                int cantidad = resultSet.getInt("cantidad");

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
                    "1-Vender/Cobrar un producto\n" + // 2da (tomi - lista)
                    "2-Ingreso de mercadería\n" + // 1ra (tomi - lista)
                    "3-Pago a proveedor\n" + // 5ta (faus - pendiente)
                    "4-Consulta de ventas\n" + // 4ta (tomi - lista)
                    "      a- Diaria\n" +
                    "      b- Mensual\n" +
                    "5-Balance (mostrar ganancias y pérdidas)\n" + // 6ta (lau - pendiente)
                    "6-Menu cocina.\n" + // 7ma (faus - pendiente)
                    "7-Pagar cuenta\n" + // 3ra (tomi - lista)
                    "8-Salir"
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
                case 3:
                    pagarProveedor();
                    break;
                case 4:
                    verVentas(mauros);
                    break;
                case 5:
                    mostrarBalance();
                    break;
                case 6:
                    solicitarComandaCocina();
                    break;
                case 7:
                    pagarCuenta(mauros);
                    break;
                case 8:
                    System.out.println("Saliendo...");
                    break;
            }

        } while (accion != 9);

    }

    public static Minimarket crearMinimarket(String nombre) {
        // Instanciamos minimarket
        Minimarket minimarket = new Minimarket(nombre);

        // Cargamos lista de ventas vacia
        ArrayList<Venta> ventasVacio = new ArrayList<>();
        minimarket.setVentas(ventasVacio);

        return minimarket;
    }

    public static void vender(Minimarket minimarket) {

        System.out.println("Que producto va a vender:");
        if (minimarket.getMercaderia().size() > 0) {
            Venta venta = new Venta();

            for (Producto producto : minimarket.getMercaderia()) {
                System.out.println("id: " + producto.getId() + " | producto: " + producto.getNombre() + " | cantidad: " + producto.getCantidad());
            }

            // Lista de productos a vender
            ArrayList<Producto> productosAVender = new ArrayList<>();
            // Iterador para sumar total de la venta
            int total = 0;

            String salir = "";

            do {

                System.out.println("Ingrese el id del producto a vender:");
                int idAVender = sc.nextInt();

                // Instanciamos el producto a vender
                Producto prodAVender = minimarket.getProducto(idAVender);
                if (prodAVender != null) {
                    System.out.println("Ingrese la cantidad a vender:");
                    int cantidadAVender = sc.nextInt();

                    if (cantidadAVender > 0 && cantidadAVender <= prodAVender.getCantidad()) {
                        // actualizamos en el objeto
                        minimarket.venderMercaderia(prodAVender, cantidadAVender);
                        // actualizamos cantidad en la db
                        actualizarCantidadEnDB(prodAVender.getId(), prodAVender.getCantidad());
                        // agregamos a la lista de productos a vender para cargar en la venta
                        productosAVender.add(prodAVender);
                        // sumamos al total el precio del producto multiplicado por la cantidad de este
                        System.out.println("precio del producto: " + prodAVender.getPrecio());
                        System.out.println("cantidad del producto: " + cantidadAVender);
                        total += prodAVender.getPrecio() * cantidadAVender;
                    } else {
                        System.out.println("Cantidad inválida.");
                    }

                } else {
                    System.out.println("Producto no encontrado.");
                }

                System.out.println("Desea cargar otro producto a la venta? y/n");
                salir = sc.next();

            } while (salir.equals("y"));

            venta.setProductos(productosAVender);
            venta.setTotal(total);

            minimarket.addVenta(venta);
            balance.agregarGanancia(total);


        } else {
            System.out.println("!!! No hay productos para vender");
        }
    }

    public static void actualizarCantidadEnDB(int idProducto, int nuevaCantidad) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "UPDATE producto SET cantidad = ? WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, nuevaCantidad);
            pstmt.setInt(2, idProducto);
            pstmt.executeUpdate();

            System.out.println("Cantidad actualizada en la base de datos.");


        } catch (SQLException | ClassNotFoundException se) {
            se.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
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
        mercaderiaAAgregar.addAll(minimarket.getMercaderia());

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

    // 3ra:
    public static void pagarCuenta(Minimarket minimarket) {

        System.out.println("Selecciona los productos a cobrar:");
        if (minimarket.getMercaderia().size() > 0) {
            Venta venta = new Venta();

            for (Producto producto : minimarket.getMercaderia()) {
                System.out.println("id: " + producto.getId() + " | producto: " + producto.getNombre() + " | cantidad: " + producto.getCantidad());
            }

            // Lista de productos a vender
            ArrayList<Producto> productosAVender = new ArrayList<>();
            // Iterador para sumar total de la venta
            int total = 0;

            String salir = "";

            do {

                System.out.println("Ingrese el id del producto a agregar al ticket:");
                int idAVender = sc.nextInt();

                // Instanciamos el producto a vender
                Producto prodAVender = minimarket.getProducto(idAVender);
                if (prodAVender != null) {
                    System.out.println("Ingrese la cantidad consumida:");
                    int cantidadAVender = sc.nextInt();

                    if (cantidadAVender > 0 && cantidadAVender <= prodAVender.getCantidad()) {
                        // actualizamos en el objeto
                        minimarket.venderMercaderia(prodAVender, cantidadAVender);
                        // actualizamos cantidad en la db
                        actualizarCantidadEnDB(prodAVender.getId(), prodAVender.getCantidad());
                        // agregamos a la lista de productos a vender para cargar en la venta
                        productosAVender.add(prodAVender);
                        // sumamos al total el precio del producto multiplicado por la cantidad de este
                        System.out.println("precio del producto: " + prodAVender.getPrecio());
                        System.out.println("cantidad del producto: " + cantidadAVender);
                        total += prodAVender.getPrecio() * cantidadAVender;
                    } else {
                        System.out.println("Cantidad inválida.");
                    }

                } else {
                    System.out.println("Producto no encontrado.");
                }

                System.out.println("Desea cargar otro producto al ticket? y/n");
                salir = sc.next();

            } while (salir.equals("y"));

            venta.setProductos(productosAVender);
            venta.setTotal(total);

            minimarket.addVenta(venta);
            balance.agregarGanancia(total);


        } else {
            System.out.println("!!! No hay productos para vender");
        }
    }
    // 4ta:
    public static void verVentas(Minimarket minimarket) {
        if (minimarket.getVentas().isEmpty()) {
            System.out.println("No hay ventas");
        } else {
            for (Venta venta : minimarket.getVentas()) {
                System.out.println("<Fecha y hora>");
                for (Producto producto : venta.getProductos()) {
                    System.out.println(producto.getNombre() + " " + producto.getPrecio() + " <cantidad>");
                }
                System.out.println("TOTAL: " + venta.getTotal());
                System.out.println("---");
            }
        }
    }

    public static void pagarProveedor() {
        System.out.println("Ingrese el id del proveedor:");
        int idProveedor = sc.nextInt();
        System.out.println("Ingrese el nombre del proveedor:");
        String nombreProveedor = sc.next();
        System.out.println("Ingrese el monto a pagar:");
        double monto = sc.nextDouble();

        Proveedor proveedor = new Proveedor(idProveedor, nombreProveedor);
        PagoProveedor pago = new PagoProveedor(proveedor, monto);

        balance.agregarPerdida(monto);
        System.out.println("Pago realizado a " + nombreProveedor + " por un monto de $" + monto);
    }
    public static void mostrarBalance() {
        System.out.println("Ganancias: $" + balance.getGanancias());
        System.out.println("Pérdidas: $" + balance.getPerdidas());
        System.out.println("Balance total: $" + balance.calcularBalance());
    }


    public static void solicitarComandaCocina(){

        boolean continuar = true;
        Cocina cocina = new Cocina();

        while (continuar) {
            System.out.println("¿Qué deseas hacer?");
            System.out.println("1. Mostrar menú");
            System.out.println("2. Pedir plato");
            System.out.println("3. Mostrar pedidos");
            System.out.println("4. Salir");
            int opcion = sc.nextInt();
            sc.nextLine();  // Limpiar el buffer

            switch (opcion) {
                case 1:
                    cocina.mostrarMenu();
                    break;
                case 2:
                    System.out.print("Ingrese el nombre del plato: ");
                    String nombrePlato = sc.nextLine();
                    System.out.print("Ingrese la cantidad: ");
                    int cantidad = sc.nextInt();
                    cocina.pedirPlato(nombrePlato, cantidad);
                    break;
                case 3:
                    cocina.mostrarPedidos();
                    break;
                case 4:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

}


