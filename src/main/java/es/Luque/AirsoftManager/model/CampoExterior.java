package es.Luque.AirsoftManager.model;

import es.Luque.AirsoftManager.model.enums.TamanoCampo;

public class CampoExterior extends Campo{
    public CampoExterior(int id, String nombre) {
        super(id, nombre, TamanoCampo.XL);
    }

    public CampoExterior(String nombre, TamanoCampo tamano) {
        super(nombre, tamano);
    }

    @Override
    public String toString() {
        return "CampoExterior{" + super.toString();
    }
}
