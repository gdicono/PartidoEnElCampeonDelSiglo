package org.example;

import javax.swing.*;

public class Simulation { //maneja la simulacion de los hinchas y el estadio

    public static void startSimulation(int capacity, int totalHooligans)
    {   StadiumGUI gui = new StadiumGUI(capacity);
        SwingUtilities.invokeLater(() -> gui.setVisible(true));

        new Thread(() -> {
        Stadium stadium = new Stadium(capacity); // crea objeto estadio. Al campeon del siglo
        stadium.setGUI(gui);
        int realHooligans = Math.min(capacity, totalHooligans); // limita la cantidad de hinchas "reales" para que no supere la capacidad del estadio.

        Controller controller = new Controller(stadium, realHooligans); // crea al verificador que va a controlar las entradas
        controller.start(); // lo inicializa

        HoolingansGenerator hoolingansGenerator = new HoolingansGenerator(stadium); // crea el objeto para crear los hinchas
        hoolingansGenerator.generateHoolingans(totalHooligans); //genera la cantidad asignada por el usuario de hinchas

    }).start(); // inicia simulacion
    }
}
