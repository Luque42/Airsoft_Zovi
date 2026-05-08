package es.Luque.AirsoftManager.model;

import es.Luque.AirsoftManager.model.enums.ModoDeJuego;

import java.time.LocalDate;

public class Partida {
    private int id;
    private ModoDeJuego modoDeJuego;
    private LocalDate fechaIni;
    private LocalDate fechaFin;
}
