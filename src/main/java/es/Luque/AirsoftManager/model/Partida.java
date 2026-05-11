package es.Luque.AirsoftManager.model;

import es.Luque.AirsoftManager.model.enums.ModoJuego;

import java.time.LocalDate;

public class Partida {
    private int id;
    private ModoJuego modoDeJuego;
    private LocalDate fechaIni;
    private LocalDate fechaFin;

    public Partida(int id, ModoJuego modoDeJuego, LocalDate fechaIni, LocalDate fechaFin) {
        this.id = id;
        this.modoDeJuego = modoDeJuego;
        this.fechaIni = fechaIni;
        this.fechaFin = fechaFin;
    }

    public int getId() {
        return id;
    }

    public ModoJuego getModoDeJuego() {
        return modoDeJuego;
    }

    public void setModoDeJuego(ModoJuego modoDeJuego) {
        this.modoDeJuego = modoDeJuego;
    }

    public LocalDate getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(LocalDate fechaIni) {
        this.fechaIni = fechaIni;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
}
