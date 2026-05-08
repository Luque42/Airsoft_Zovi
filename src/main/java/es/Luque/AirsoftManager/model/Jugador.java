package es.Luque.AirsoftManager.model;


import java.sql.Date;
import java.time.LocalDate;

public class Jugador {
    private Integer id;
    private String dni;
    private String nombre;
    private String apellidos;
    private double altura;
    private LocalDate fechaNacimiento;

    public Jugador(int id, String dni, String nombre, String apellidos, double altura, Date fechaNacimiento) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.altura = altura;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "id=" + id +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", altura=" + altura +
                ", fechaNacimiento=" + fechaNacimiento +
                '}';
    }
}
