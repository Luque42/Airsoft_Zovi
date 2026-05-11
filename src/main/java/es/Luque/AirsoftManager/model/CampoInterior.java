package es.Luque.AirsoftManager.model;

import es.Luque.AirsoftManager.model.enums.TamanoCampo;

public class CampoInterior extends Campo{
    public CampoInterior(int id, String nombre, TamanoCampo tamano) {
        super(id, nombre, tamano);
    }
    public CampoInterior(String nombre, TamanoCampo tamano) {
        super(nombre, tamano);
    }
    @Override
    public String toString() {
        return "CampoInterior{" + super.toString();
    }

}
