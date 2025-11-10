package org.example;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

public class Stadium {
    private int hooligansWaiting = 0; // hinchas esperando

    private final Semaphore hooligans = new Semaphore(0); // semaforo que representa a los hinchas que llegan. Cuando llega un hincha el semaforo se libera, avisando asi al controlador
    private final Semaphore controler = new Semaphore(0); // semaforo que representa al controlador de entradas. El controlador libera el semaforo cuadno termina de verificar a un hincha. oermitiendo que entre al estadio.
    private final Semaphore mutex = new  Semaphore(1); // mutua exclusión.  Solo permite que un hilo a la vez modifique variables compartidas
    private final Semaphore seats;

    private int penarolCount = 0; // contador de hinchas de peñarol para distribución de asientos
    private int nacionalCount = 0; // contador de hinchas de nacional para distribución de asientos
    private int hooligansInside = 0; // cantidad de hinchas que efectivamente entraron al estadio

    private final Queue<HooliganInfo> waitingQueue = new ConcurrentLinkedQueue<>(); // cola que sigue con el principio FIFO - tiene de cola enlazada para hilos (puede agregar o sacar elementos de diferentes hilos sin problemas de concurrencia)
    private volatile boolean closed = false; // bandera para saber si el estadio ya alcanzo cu capacidad y cerró
    private StadiumGUI gui;


    public Stadium(int capacity) { // constructor
        this.seats = new Semaphore(capacity);
    }

    public void setGUI(StadiumGUI gui) {
        this.gui = gui;
    }

    public void hoolingsArrived(int id, String team) throws InterruptedException {

        if(seats.tryAcquire()) { // intenta tomar un asiento disponible
            mutex.acquire(); // el hincha intenta entrar a una sección crítica. Solo un hilo a la vez puede revisar o modificar variab les compartidas.
            hooligansWaiting++; // suma un hincha a la lista de espera
            waitingQueue.add(new HooliganInfo(id, team)); // añadimos a cola

            System.out.println("Hincha "+ id + " de " + team + ", llega al campeon del siglo. (Hinchas esperando: " + hooligansWaiting+ ")");

            if (gui != null) {gui.appendMessage("Hincha "+ id + " de " + team + ", llega al campeon del siglo. (Hinchas esperando: " + hooligansWaiting+ ")");}

            mutex.release(); // sale de la sección crítica, así otro hincha puede revisar

            hooligans.release(); // libera permiso para avisar al controlador que hay un nuevo hincha esperando
            controler.acquire(); // espera a que el controlador le de permiso para entrar
            enterToTheStadium(id, team);  // cuando el controlador lo deja pasar, el hincha entra al estadio

        }
        else {
            while(!closed)
            {Thread.yield();} // cede la CPU. evitamos bloquear otros hilos
            System.out.println("Hincha "+ id + " de " + team + " se va, no hay más lugar en el campeón del siglo");
            if (gui != null) {gui.appendMessage("Hincha "+ id + " de " + team + " se va, no hay más lugar en el campeón del siglo");}

        }
    }

    public void ticketController(int capacity) throws InterruptedException {

        int attended = 0;

                while (attended < capacity)
            {
                hooligans.acquire(); // espera hasta que haya al menos un hincha que quiera entrar. Si no hay, el controlador se bloquea acá

                mutex.acquire(); // entra a la sección crítica para modificar la variable compartida
                HooliganInfo next = waitingQueue.poll(); // obtiene al próximo hincha en la cola

                if (next != null) {
                    hooligansWaiting--;
                    System.out.println("Controlador atiende al hincha " + next.id + " (" + next.team + "). (Esperando: " + hooligansWaiting + ")");
                    if (gui != null) {gui.appendMessage("Controlador atiende al hincha " + next.id + " (" + next.team + "). (Esperando: " + hooligansWaiting + ")");}

                    mutex.release();  // sale de la sección crítica para que otros hilos puedan acceder

                    checkTicket(next); // simula el proceso de revisar el ticket antes de dejar entrar
                    controler.release(); // libera al hincha para que pueda entrar (simula que el verificador le da paso)

                    attended++;
                }
                else {
                    mutex.release();
                }
            }
        System.out.println("ATENCIÓN! - SOLD OUT! Las cantidad de asientos disponibles ya fueron cubiertos. El controlador cierra la entrada del estadio.");
        if (gui != null) {gui.appendMessage("ATENCIÓN! - SOLD OUT! Las cantidad de asientos disponibles ya fueron cubiertos. El controlador cierra la entrada del estadio.");}

        closed = true; // bandera cambio a true. cerro el campeon de siglo

        while (hooligansInside < capacity) {
            Thread.sleep(500); // da tiempo a los hilos que todavía están entrando
        }

        showFinalDistribution();
    }

    public void checkTicket(HooliganInfo hincha) throws InterruptedException {
        System.out.println("Verificando entrada del hincha " + hincha.id + " (" + hincha.team + ")...");
        if (gui != null) {gui.appendMessage("Verificando entrada del hincha " + hincha.id + " (" + hincha.team + ")...");}

        Thread.sleep(1500);

        System.out.println("Entrada válida para hincha " + hincha.id + " (" + hincha.team + ")");
        if (gui != null) {gui.appendMessage("Entrada válida para hincha " + hincha.id + " (" + hincha.team + ")");}
    }

    public void enterToTheStadium(int id, String team) throws InterruptedException { // simula el momento en que el hincha pasa al estadio
        System.out.println("Hincha "+ id + " de " + team + " esta entrando.");
        if (gui != null) {gui.appendMessage("Hincha "+ id + " de " + team + " esta entrando.");}

        Thread.sleep(2000); // simula el tiempo que tarda en pasar por la puerta para estar en la tribuna

        System.out.println("Hincha "+ id + " de " + team + " está en la tribuna.");
        if (gui != null) {gui.appendMessage("Hincha "+ id + " de " + team + " está en la tribuna.");}

        mutex.acquire();

        if (team.equals("Peñarol")) {
            penarolCount++; // aumentamos contador de hinchas de peñarol que ingresaron al estadio
        }
        else {
            nacionalCount++;} // aumentamos contador de hinchas de nacional que ingresaron al estadio

        hooligansInside++; // aumentamos contador de hinchas que ingresaron al estadio

        if (gui != null)
            gui.updateBars(penarolCount, nacionalCount, hooligansInside);

        mutex.release(); // sale de la sección crítica
    }

    public void showFinalDistribution() {
        System.out.println("\nDistribución final de asientos:");
        if (gui != null) {gui.appendMessage("\nDistribución final de asientos:");}

        System.out.println("Peñarol: " + penarolCount + " - Nacional: " + nacionalCount);
        if (gui != null) {gui.appendMessage("Peñarol: " + penarolCount + " - Nacional: " + nacionalCount);}
    }

}
