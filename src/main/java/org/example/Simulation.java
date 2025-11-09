package org.example;
import org.example.HoolingansGenerator;

public class Simulation { //maneja la simulacion de los hinchas y el estadio

    public static void startSimulation(int capacity, int totalHooligans)
    {
        Stadium stadium = new Stadium(capacity); // crea objeto estadio. Al campeon del siglo

        Controller controller = new Controller(stadium, totalHooligans); // crea al verificador que va a controlar las entradas
        controller.start(); // lo inicializa

        HoolingansGenerator hoolingansGenerator = new HoolingansGenerator(stadium); // crea el objeto para crear los hinchas
        hoolingansGenerator.generateHoolingans(totalHooligans); //genera la cantidad asignada por el usuario de hinchas

    }
}
