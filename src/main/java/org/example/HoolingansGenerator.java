package org.example;

public class HoolingansGenerator { // generador de hinchas
    private final Stadium stadium;

    public HoolingansGenerator(Stadium stadium) { // constrcutor
        this.stadium = stadium;
    }

    public void generateHoolingans(int cantidad) {

        String [] teams ={ "Peñarol", "Nacional"}; // array de dos elementos


        for(int i = 1; i <= cantidad; i++) // bucle para generar la cantidad de hinchas que fue asignada
        {
            String team = teams[(int)(Math.random() * 2)]; // elige pseudoaletoriamente un equipo (indice 0 o 1)
            Hooligans hooligan = new Hooligans(stadium, i, team); // crea un objeto hooligans (hilo) con su equipo, su id y el estadio
            hooligan.start(); // lo inicializa

        try {
            Thread.sleep((int)(Math.random() * 1500));; // tiempo aleatorio para generar otro hincha (entre 0 y 1500 ms)
        } catch (InterruptedException e) {
            e.printStackTrace(); // si hay error imprime
        }

        }
    }
}
