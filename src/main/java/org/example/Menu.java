package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu { // menu interactivo

    public void showMenu() {
        Scanner scanner = new Scanner(System.in); // obejto scanner para leer los inputs
        int capacity = 0;
        int totalHooligans = 0;

        System.out.println("Bienvenido al simulador del Campeón del Siglo");
        System.out.println("-");

        try {

            System.out.println("Ingrese la capacidad del estadio: ");
            capacity = scanner.nextInt(); // input, lee enteros

            System.out.println("Ingrese la cantidad de hinchas que van a llegar al campeon del siglo: ");
            totalHooligans = scanner.nextInt(); // input, lee enteros

            scanner.nextLine(); // limpamos buffer (quita el salto de línea pendiente)

        } catch (Exception e) {

            System.out.println("Error. Debe ingresar solamente números");
            scanner.nextLine(); // limpamos buffer (quita el salto de línea pendiente)
            return;
        }

        if (Confirmation.askConfirmation(scanner))
        {
            System.out.println("Iniciando! ");
            Simulation.startSimulation(capacity, totalHooligans);
        } else {
            System.out.println("Simulación cancelada por el usuario. Hasta pronto!");
        }
        scanner.close(); // cerramos el scanner
    }
}
