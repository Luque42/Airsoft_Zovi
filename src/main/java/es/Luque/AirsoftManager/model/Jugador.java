package es.Luque.AirsoftManager.model;


import java.sql.Date;
import java.time.LocalDate;

public class Jugador {
    private Integer id;
    private String dni;
    private String nombre;
    private String apellidos;
    private double altura;
    private LocalDate fNacimiento;


    public Jugador(int id, String dni, String nombre, String apellidos, double altura, LocalDate fNacimiento) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.altura = altura;
        this.fNacimiento = fNacimiento;

    }

    public String getNombre() {
        return nombre;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getfNacimiento() {
        return fNacimiento;
    }

    public String getDni() {
        return dni;
    }

    public String getApellidos() {
        return apellidos;
    }

    public double getAltura() {
        return altura;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "id=" + id +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", altura=" + altura;
    }
}
