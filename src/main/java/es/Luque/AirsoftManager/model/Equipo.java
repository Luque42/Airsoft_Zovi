package es.Luque.AirsoftManager.model;

import java.util.ArrayList;

public class Equipo {
    private int id;
    private String nombre;
    private int nmiembros;
    private ArrayList<Jugador> miembros;

    public Equipo(int id, String nombre, int nmiembros, ArrayList<Jugador> miembros) {
        this.id = id;
        this.nombre = nombre;
        this.nmiembros = nmiembros;
        this.miembros = miembros;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroMiembros() {
        return nmiembros;
    }

    public void setNumeroMiembros(int numeroMiembros) {
        this.nmiembros = numeroMiembros;
    }

    public ArrayList<Jugador> getMiembros() {
        return miembros;
    }

    public void setMiembros(ArrayList<Jugador> miembros) {
        this.miembros = miembros;
    }
}
