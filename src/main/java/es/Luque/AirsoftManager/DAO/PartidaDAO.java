package es.Luque.AirsoftManager.DAO;

import es.Luque.AirsoftManager.dataaccess.ConnectionBD;
import es.Luque.AirsoftManager.model.Partida;
import es.Luque.AirsoftManager.model.enums.ModoJuego;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class PartidaDAO {
    private final static String SQL_ALL = "SELECT * FROM partida";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM partida WHERE ID = ?";
    private final static String SQL_FIND_BY_MODO = "SELECT * FROM partida WHERE ModoJuego = ?";
    private final static String SQL_INSERT = "INSERT INTO partida (ModoJuego, fecha_ini, fecha_fin) VALUES (?, ?, ?)";
    private final static String SQL_UPDATE = "UPDATE partida SET ModoJuego = ?, fecha_ini = ?, fecha_fin = ? WHERE ID = ?";
    private final static String SQL_DELETE = "DELETE FROM partida WHERE ID = ?";

    public static ArrayList<Partida> findAll() throws SQLException {
        Partida partida = null;
        ArrayList<Partida> partidas = new ArrayList<>();

        try (Statement st = ConnectionBD.getInstance().getConnection().createStatement()) {
            ResultSet rs = st.executeQuery(SQL_ALL);
            while (rs.next()) {
                int ID = rs.getInt("ID");
                ModoJuego modoDeJuego = ModoJuego.valueOf(rs.getString("ModoJuego"));
                LocalDate fechaIni = rs.getDate("fecha_ini").toLocalDate();
                LocalDate fechaFin = rs.getDate("fecha_fin").toLocalDate();
                partida = new Partida(ID, modoDeJuego, fechaIni, fechaFin);
                partidas.add(partida);
            }
        }
        return partidas;
    }

    public static Partida findById(int id) throws SQLException {
        Partida partida = null;

        try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int ID = rs.getInt("ID");
                ModoJuego modoDeJuego = ModoJuego.valueOf(rs.getString("ModoJuego"));
                LocalDate fechaIni = rs.getDate("fecha_ini").toLocalDate();
                LocalDate fechaFin = rs.getDate("fecha_fin").toLocalDate();
                partida = new Partida(ID, modoDeJuego, fechaIni, fechaFin);
            }
        }
        return partida;
    }

    public static ArrayList<Partida> findByModoDeJuego(ModoJuego modoDeJuego) throws SQLException {
        Partida partida = null;
        ArrayList<Partida> partidas = new ArrayList<>();

        try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_FIND_BY_MODO)) {
            ps.setString(1, modoDeJuego.name());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ID = rs.getInt("ID");
                ModoJuego modo = ModoJuego.valueOf(rs.getString("ModoJuego"));
                LocalDate fechaIni = rs.getDate("fecha_ini").toLocalDate();
                LocalDate fechaFin = rs.getDate("fecha_fin").toLocalDate();
                partida = new Partida(ID, modo, fechaIni, fechaFin);
                partidas.add(partida);
            }
        }
        return partidas;
    }

    /**
     * Añade una partida a la base de datos.
     * @param partida partida a introducir en la base de datos
     * @return true si la partida se ha añadido correctamente, false si la partida es nula.
     * @throws SQLException
     */
    public static boolean addPartida(Partida partida) throws SQLException {
        if (partida != null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, partida.getModoDeJuego().name());
                ps.setDate(2, Date.valueOf(partida.getFechaIni()));
                ps.setDate(3, Date.valueOf(partida.getFechaFin()));
                ps.executeUpdate();
                return true;
            }
        }
        return false;
    }

    /**
     * Actualiza una partida existente en la base de datos. La partida se identifica por su ID.
     * @param partida partida a actualizar con los nuevos datos. El ID de la partida no debe ser modificado.
     * @throws SQLException
     */
    public static void updatePartida(Partida partida) throws SQLException {
        if (partida != null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setString(1, partida.getModoDeJuego().name());
                ps.setDate(2, Date.valueOf(partida.getFechaIni()));
                ps.setDate(3, Date.valueOf(partida.getFechaFin()));
                ps.setInt(4, partida.getId());
                ps.executeUpdate();
            }
        }
    }

    /**
     * Elimina una partida de la base de datos por su ID.
     * @param id ID de la partida a eliminar
     * @return true si se eliminó, false si no existía
     * @throws SQLException
     */
    public static boolean deletePartida(int id) throws SQLException {
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
}
