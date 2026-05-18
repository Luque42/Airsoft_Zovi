package es.Luque.AirsoftManager.DAO;

import es.Luque.AirsoftManager.dataaccess.ConnectionBD;
import es.Luque.AirsoftManager.model.Jugador;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class JugadorDAO {
    private final static String SQL_ALL = "SELECT * FROM jugador";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM Jugador where nombre =?";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM Jugador where ID =?";
    private final static String SQL_INSERT = "INSERT INTO jugador (dni, nombre, apellidos, altura, FNacimiento ) VALUES (?, ?, ?, ?, ?)";
    private final static String SQL_UPDATE = "UPDATE jugador SET dni =?, nombre =?, apellidos =?, altura =?, FNacimiento =? WHERE ID =?";
    private final static String SQL_DELETE = "DELETE FROM jugador WHERE ID =?";

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
            LocalDate fNacimiento = rs.getDate("FNacimiento").toLocalDate();
            Jugador = new Jugador(ID, dni, nombre, apellidos, altura, fNacimiento);
            jugadores.add(Jugador);
        }
        return jugadores;
    }
    public static boolean addJugador(Jugador jugador) throws SQLException {
        if ((jugador != null) && findByName(jugador.getNombre()) == null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_INSERT)){
                ps.setString(1, jugador.getDni());
                ps.setString(2, jugador.getNombre());
                ps.setString(3, jugador.getApellidos());
                ps.setDouble(4, jugador.getAltura());
                ps.setDate(5, Date.valueOf(jugador.getfNacimiento()));
                ps.executeUpdate();
                return true;
            }
        }
        return false;
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
                jugador = new Jugador(ID, dni, nombreJugador, apellidos, altura, fNacimiento.toLocalDate());
            }
        }
        return jugador;
    }

    /**
     * Actualiza un jugador existente en la base de datos. El jugador se identifica por su ID.
     * @param jugador jugador a actualizar con los nuevos datos. El ID del jugador no debe ser modificado.
     * @throws SQLException
     */
    public static void updateJugador(Jugador jugador) throws SQLException {
        if (jugador != null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_UPDATE)){
                ps.setString(1, jugador.getDni());
                ps.setString(2, jugador.getNombre());
                ps.setString(3, jugador.getApellidos());
                ps.setDouble(4, jugador.getAltura());
                ps.setDate(5, Date.valueOf(jugador.getfNacimiento()));
                ps.setInt(6, jugador.getId());
                ps.executeUpdate();
            }
        }
    }

    public static boolean deleteJugadorById(int id) throws SQLException {
        boolean deleted = false;
        if (findById(id) != null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_DELETE)) {
                ps.setInt(1, id);
                ps.executeUpdate();
                deleted = true;
            }
        }
        return deleted;
    }

    public static Jugador findById(int id) throws SQLException {
        Jugador jugador = null;
        try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int ID = rs.getInt("ID");
                String dni = rs.getString("dni");
                String nombreJugador = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                double altura = rs.getDouble("altura");
                Date fNacimiento = rs.getDate("FNacimiento");
                jugador = new Jugador(ID, dni, nombreJugador, apellidos, altura, fNacimiento.toLocalDate());
            }
        }
        return jugador;
    }


}
