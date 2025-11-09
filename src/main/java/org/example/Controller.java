package org.example;

public class Controller extends Thread { // controlador que representa un hilo que controla la entrada de los hinchas al estadio
    private final Stadium stadium;
    public final int totalFans;

    public Controller(Stadium stadium, int  totalFans) { // constructor
        this.stadium = stadium;
        this.totalFans = totalFans;
    }

    @Override
    public void run() {
        try {
                stadium.ticketController(totalFans); // llama al metodo del estadio que controla la entrada de hinchas. Le pasa la cantidad total de hinchas
            } catch (InterruptedException e) {
                e.printStackTrace(); // si hay error, lo imprime
            }
    }

}