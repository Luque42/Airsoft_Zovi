package es.Luque.AirsoftManager.model;

import es.Luque.AirsoftManager.model.enums.TamanoCampo;

public class Campo {
    private int id;
    private String nombre;
    private TamanoCampo tamano;

    public Campo(int id, String nombre, TamanoCampo tamano) {
        this.id = id;
        this.nombre = nombre;
        this.tamano = tamano;
    }
}
