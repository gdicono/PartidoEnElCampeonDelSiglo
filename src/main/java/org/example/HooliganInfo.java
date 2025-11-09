package org.example;

public class HooliganInfo { // información del hincha
    public final int id; // id del hincha
    public final String team; // equipo del hincha

    public HooliganInfo(int id, String team) { // constructor
        this.id = id;
        this.team = team;
    }

    public int getId() {return id;} // devuelve el id del hincha

    public String getTeam() {
        return team;
    } // devuelve el equipo del hincha
}
