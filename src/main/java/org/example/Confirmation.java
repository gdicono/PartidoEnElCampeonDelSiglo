package org.example;
import java.util.Scanner;

public class Confirmation { // maneja lo que confirme el usuario

    public static boolean askConfirmation(Scanner scanner) {

        while (true) {

            System.out.print("Desea iniciar? (s/n): ");
            String option = scanner.nextLine().trim().toLowerCase(); // input para el usuario. lo pasa a minusculas

            if (option.equals("s")) {
                return true;
            }
            else if (option.equals("n")) {
                return false;

            } else
            {System.out.println("Opción no válida. Ingrese 's' para sí o 'n' para no.");}

        }

    }
}
