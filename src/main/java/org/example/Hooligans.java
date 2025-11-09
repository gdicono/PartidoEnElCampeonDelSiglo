package org.example;

public class Hooligans extends Thread { // hincha como hilo
    private final Stadium stadium; // estadio al que va a llegar
    public int id; // su id
    public final String team; // equipo del cual es hincha

    public Hooligans(Stadium stadium,  int id, String team) { // constrcutor
        this.stadium = stadium;
        this.id = id;
        this.team = team;
    }

    @Override
    public void run() {
        try
        {
            stadium.hoolingsArrived(id, team); // invoca al metodo del estadio indicando que este hincha llegó
        } catch (InterruptedException e) {
            e.printStackTrace(); // si hay error lo imprime
        }
    }
}
