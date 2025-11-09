package org.example;

public class Controller extends Thread { // controlador que representa un hilo que controla la entrada de los hinchas al estadio
    private final Stadium stadium;
    public final int totalHooligans;

    public Controller(Stadium stadium, int totalHooligans) { // constructor
        this.stadium = stadium;
        this.totalHooligans = totalHooligans;
    }

    @Override
    public void run() {
        try {
                stadium.ticketController(totalHooligans); // llama al metodo del estadio que controla la entrada de hinchas. Le pasa la cantidad total de hinchas
            } catch (InterruptedException e) {
                e.printStackTrace(); // si hay error, lo imprime
            }
    }

}