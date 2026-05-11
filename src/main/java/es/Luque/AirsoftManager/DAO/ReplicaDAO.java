package es.Luque.AirsoftManager.DAO;

import es.Luque.AirsoftManager.dataaccess.ConnectionBD;
import es.Luque.AirsoftManager.model.Replica;

import java.sql.*;
import java.util.ArrayList;

public class ReplicaDAO {
    private final static String SQL_ALL = "SELECT * FROM replica";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM replica WHERE ID = ?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM replica WHERE nombre = ?";
    private final static String SQL_INSERT = "INSERT INTO replica (nombre, nSerie, potencia) VALUES (?, ?, ?)";
    private final static String SQL_UPDATE = "UPDATE replica SET nombre = ?, nSerie = ?, potencia = ? WHERE ID = ?";
    private final static String SQL_DELETE = "DELETE FROM replica WHERE ID = ?";

    public static ArrayList<Replica> findAll() throws SQLException {
        Replica replica = null;
        ArrayList<Replica> replicas = new ArrayList<>();

        try (Statement st = ConnectionBD.getInstance().getConnection().createStatement()) {
            ResultSet rs = st.executeQuery(SQL_ALL);
            while (rs.next()) {
                int ID = rs.getInt("ID");
                String nombre = rs.getString("nombre");
                String nSerie = rs.getString("nSerie");
                int potencia = rs.getInt("potencia");
                replica = new Replica(ID, nombre, nSerie, potencia);
                replicas.add(replica);
            }
        }
        return replicas;
    }

    public static Replica findById(int id) throws SQLException {
        Replica replica = null;

        try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int ID = rs.getInt("ID");
                String nombre = rs.getString("nombre");
                String nSerie = rs.getString("nSerie");
                int potencia = rs.getInt("potencia");
                replica = new Replica(ID, nombre, nSerie, potencia);
            }
        }
        return replica;
    }

    public static Replica findByName(String nombre) throws SQLException {
        Replica replica = null;

        try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_FIND_BY_NAME)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int ID = rs.getInt("ID");
                String nombreReplica = rs.getString("nombre");
                String nSerie = rs.getString("nSerie");
                int potencia = rs.getInt("potencia");
                replica = new Replica(ID, nombreReplica, nSerie, potencia);
            }
        }
        return replica;
    }

    /**
     * Añade una réplica a la base de datos si no existe ya una réplica con el mismo nombre.
     * @param replica réplica a introducir en la base de datos
     * @return true si la réplica se ha añadido correctamente, false si la réplica es nula o ya existe una réplica con el mismo nombre.
     * @throws SQLException
     */
    public static boolean addReplica(Replica replica) throws SQLException {
        if ((replica != null) && findByName(replica.getNombre()) == null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_INSERT)) {
                ps.setString(1, replica.getNombre());
                ps.setString(2, replica.getnSerie());
                ps.setInt(3, replica.getPotencia());
                ps.executeUpdate();
                return true;
            }
        }
        return false;
    }

    /**
     * Actualiza una réplica existente en la base de datos. La réplica se identifica por su ID.
     * @param replica réplica a actualizar con los nuevos datos. El ID de la réplica no debe ser modificado.
     * @throws SQLException
     */
    public static void updateReplica(Replica replica) throws SQLException {
        if ((replica != null) && findByName(replica.getNombre()) != null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_UPDATE)) {
                ps.setString(1, replica.getNombre());
                ps.setString(2, replica.getnSerie());
                ps.setInt(3, replica.getPotencia());
                ps.setInt(4, replica.getId());
                ps.executeUpdate();
            }
        }
    }

    /**
     * Elimina una réplica de la base de datos por su ID.
     * @param id ID de la réplica a eliminar
     * @throws SQLException
     */
    public static void deleteReplica(int id) throws SQLException {
        try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
