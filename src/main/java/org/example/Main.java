package org.example;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Main {
    public static void main(String[] args) {

        try {
            PrintStream dualOut = new DualPrint(new FileOutputStream("resultadoDeLaSimulación.xml"), System.out);
            System.setOut(dualOut);  // redirige la salida por default (System.out) hacia dualOut.
            System.setErr(dualOut); // tambien con los errores
        } catch (FileNotFoundException e) {
            e.printStackTrace(); // lanza excepcion si hay algun error
        }

        Menu menu = new Menu(); // creamos objeto menu

        menu.showMenu(); // inicializamos el metodo de menu

    }
}