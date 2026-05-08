package es.Luque.AirsoftManager.DAO;

import es.Luque.AirsoftManager.dataaccess.ConnectionBD;
import es.Luque.AirsoftManager.model.Jugador;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class JugadorDAO {
    private final static String SQL_ALL = "SELECT * FROM jugador";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM Jugador where nombre =?";
    private final static String SQL_INSERT = "INSERT INTO campo (dni, nombre, apellidos, altura, FNacimiento ) VALUES (?, ?)";
    private final static String SQL_UPDATE = "UPDATE campo SET nombre =?, tamano =? WHERE ID =?";
    private final static String SQL_DELETE = "DELETE FROM campo WHERE ID =?";

    public static ArrayList<Jugador> findAllJugador() throws SQLException {
        Jugador Jugador = null;
        ArrayList<Jugador> jugadores = new ArrayList<>();

        Statement st = ConnectionBD.getInstance().getConnection().createStatement();
        ResultSet rs = st.executeQuery(SQL_ALL);
        while (rs.next()) {
            int ID = rs.getInt("ID");
            String dni = rs.getString("dni");
            String nombre = rs.getString("nombre");
            String apellidos = rs.getString("apellidos");
            double altura = rs.getDouble("altura");
            LocalDate fNacimiento = rs.getDate("FNacimiento");
            Jugador = new Jugador(ID, dni, nombre, apellidos, altura, fNacimiento);
            jugadores.add(Jugador);
        }
        return jugadores;
    }
    public static Jugador addJugador(Jugador jugador) throws SQLException {

        if ((jugador != null) && findByName(jugador.getNombre()) == null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, jugador.getNombre());
                ps.executeUpdate();
                //con la siguiente linea, una vez insertado, busco el autor por su nombre para devolverlo
                //con el id correcto que tiene en la bbdd
                jugador=findByName(jugador.getNombre());
            }
        } else {
            jugador = null;
        }
        return jugador;
    }
    public static Jugador findByName(String nombre) throws SQLException {
        Jugador jugador = null;

        try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_FIND_BY_NAME)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int ID = rs.getInt("ID");
                String dni = rs.getString("dni");
                String nombreJugador = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                double altura = rs.getDouble("altura");
                Date fNacimiento = rs.getDate("FNacimiento");
                jugador = new Jugador(ID, dni, nombreJugador, apellidos, altura, fNacimiento);
            }
        }
        return jugador;
    }


}
