package es.Luque.AirsoftManager.model;

public class Replica {
    private int id;
    private String nombre;
    private String nSerie;
    private int potencia;

    public Replica(int id, String nombre, String nSerie, int potencia) {
        this.id = id;
        this.nombre = nombre;
        this.nSerie = nSerie;
        this.potencia = potencia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getnSerie() {
        return nSerie;
    }

    public void setnSerie(String nSerie) {
        this.nSerie = nSerie;
    }

    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }
}
