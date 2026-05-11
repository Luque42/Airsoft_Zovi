package es.Luque.AirsoftManager.DAO;

import es.Luque.AirsoftManager.dataaccess.ConnectionBD;
import es.Luque.AirsoftManager.model.Equipo;

import java.sql.*;
import java.util.ArrayList;

public class EquipoDAO {
    private final static String SQL_ALL = "SELECT * FROM equipo";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM equipo WHERE ID = ?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM equipo WHERE nombre = ?";
    private final static String SQL_INSERT = "INSERT INTO equipo (nombre, nmiembros) VALUES (?, ?)";
    private final static String SQL_UPDATE = "UPDATE equipo SET nombre = ?, nmiembros = ? WHERE ID = ?";
    private final static String SQL_DELETE = "DELETE FROM equipo WHERE ID = ?";

    public static ArrayList<Equipo> findAll() throws SQLException {
        Equipo equipo = null;
        ArrayList<Equipo> equipos = new ArrayList<>();

        try (Statement st = ConnectionBD.getInstance().getConnection().createStatement()) {
            ResultSet rs = st.executeQuery(SQL_ALL);
            while (rs.next()) {
                int ID = rs.getInt("ID");
                String nombre = rs.getString("nombre");
                int numeroMiembros = rs.getInt("nmiembros");
                equipo = new Equipo(ID, nombre, numeroMiembros, new ArrayList<>()); // miembros empty for now
                equipos.add(equipo);
            }
        }
        return equipos;
    }

    public static Equipo findById(int id) throws SQLException {
        Equipo equipo = null;

        try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int ID = rs.getInt("ID");
                String nombre = rs.getString("nombre");
                int numeroMiembros = rs.getInt("nmiembros");
                equipo = new Equipo(ID, nombre, numeroMiembros, new ArrayList<>());
            }
        }
        return equipo;
    }

    public static Equipo findByName(String nombre) throws SQLException {
        Equipo equipo = null;

        try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_FIND_BY_NAME)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int ID = rs.getInt("ID");
                String nombreEquipo = rs.getString("nombre");
                int numeroMiembros = rs.getInt("nmiembros");
                equipo = new Equipo(ID, nombreEquipo, numeroMiembros, new ArrayList<>());
            }
        }
        return equipo;
    }

    /**
     * Añade un equipo a la base de datos si no existe ya un equipo con el mismo nombre.
     * @param equipo equipo a introducir en la base de datos
     * @return true si el equipo se ha añadido correctamente, false si el equipo es nulo o ya existe un equipo con el mismo nombre.
     * @throws SQLException
     */
    public static boolean addEquipo(Equipo equipo) throws SQLException {
        if ((equipo != null) && findByName(equipo.getNombre()) == null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, equipo.getNombre());
                ps.setInt(2, equipo.getNumeroMiembros());
                ps.executeUpdate();
                return true;
            }
        }
        return false;
    }

    /**
     * Actualiza un equipo existente en la base de datos. El equipo se identifica por su ID.
     * @param equipo equipo a actualizar con los nuevos datos. El ID del equipo no debe ser modificado.
     * @throws SQLException
     */
    public static void updateEquipo(Equipo equipo) throws SQLException {
        if ((equipo != null) && findByName(equipo.getNombre()) != null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setString(1, equipo.getNombre());
                ps.setInt(2, equipo.getNumeroMiembros());
                ps.setInt(3, equipo.getId());
                ps.executeUpdate();
            }
        }
    }

    /**
     * Elimina un equipo de la base de datos por su ID.
     * @param id ID del equipo a eliminar
     * @return true si se eliminó, false si no existía
     * @throws SQLException
     */
    public static boolean deleteEquipo(int id) throws SQLException {
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
