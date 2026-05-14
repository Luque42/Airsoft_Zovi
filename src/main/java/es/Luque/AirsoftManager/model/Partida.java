package es.Luque.AirsoftManager.model;

import es.Luque.AirsoftManager.model.enums.ModoJuego;

import java.time.LocalDateTime;

public class Partida {
    private int id;
    private ModoJuego modoDeJuego;
    private LocalDateTime fechaIni;
    private LocalDateTime fechaFin;

    public Partida(int id, ModoJuego modoDeJuego, LocalDateTime fechaIni, LocalDateTime fechaFin) {
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

    public LocalDateTime getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(LocalDateTime fechaIni) {
        this.fechaIni = fechaIni;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }
}
