package org.example;

import java.util.Scanner;

public class Main {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

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
                "8-Informacion estadística de platos más pedidos."
        );

        int accion = Integer.parseInt(sc.next());

    }
}