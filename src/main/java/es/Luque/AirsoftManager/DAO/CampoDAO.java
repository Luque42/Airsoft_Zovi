package es.Luque.AirsoftManager.DAO;

import es.Luque.AirsoftManager.dataaccess.ConnectionBD;
import es.Luque.AirsoftManager.model.Campo;
import es.Luque.AirsoftManager.model.CampoExterior;
import es.Luque.AirsoftManager.model.CampoInterior;
import es.Luque.AirsoftManager.model.Jugador;
import es.Luque.AirsoftManager.model.enums.TamanoCampo;

import java.sql.*;
import java.util.ArrayList;

public class CampoDAO {
    private final static String SQL_ALL_INTERIOR = "SELECT * FROM campointerior";
    private final static String SQL_ALL_EXTERIOR = "SELECT * FROM campoexterior";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM campo where ID =?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM campo where nombre =?";
    private final static String SQL_INSERT = "INSERT INTO campointerior (nombre, tamano) VALUES (?, ?)";
    private final static String SQL_UPDATE = "UPDATE campo SET nombre =?, tamano =? WHERE ID =?";
    private final static String SQL_DELETE = "DELETE FROM campo WHERE ID =?";

    public static ArrayList<Campo> findAllExterior() throws SQLException {
        CampoExterior campoE = null;
        ArrayList<Campo> camposE = new ArrayList<>();

        try (Statement st = ConnectionBD.getInstance().getConnection().createStatement()) {
            ResultSet rs = st.executeQuery(SQL_ALL_EXTERIOR);
            while (rs.next()) {
                int ID = rs.getInt("ID");
                String nombre = rs.getString("nombre");
                campoE = new CampoExterior(ID, nombre);
                camposE.add(campoE);
            }
        }
        return camposE;

    }

    public static ArrayList<Campo> findAllInterior() throws SQLException {
        CampoInterior campoI = null;
        ArrayList<Campo> camposE = new ArrayList<>();

        try (Statement st = ConnectionBD.getInstance().getConnection().createStatement()) {
            ResultSet rs = st.executeQuery(SQL_ALL_INTERIOR);
            while (rs.next()) {
                int ID = rs.getInt("ID");
                String nombre = rs.getString("nombre");
                TamanoCampo tamano = TamanoCampo.valueOf(rs.getString("tamano"));
                campoI = new CampoInterior(ID, nombre, tamano);
                camposE.add(campoI);
            }
        }
        return camposE;
    }

    public static Campo findById(int id) throws SQLException {
        Campo campo = null;

        try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int ID = rs.getInt("ID");
                String nombre = rs.getString("nombre");
                TamanoCampo tamano = TamanoCampo.valueOf(rs.getString("tamano"));
                campo = new CampoInterior(ID, nombre, tamano);
            }
        }
        return campo;
    }

    public static Campo findByName(String nombre) throws SQLException {
        Campo campo = null;

        try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_FIND_BY_NAME)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int ID = rs.getInt("ID");
                String nombreCampo = rs.getString("nombre");
                TamanoCampo tamano = TamanoCampo.valueOf(rs.getString("tamano"));
                campo = new CampoInterior(ID, nombreCampo, tamano);
            }
        }
        return campo;
    }

    /**
     * Añade un campo interior a la base de datos si no existe ya un campo con el mismo nombre.
     * @param campo campo a introducir en la base de datos
     * @return true si el campo se ha añadido correctamente, false si el campo es nulo o ya existe un campo con el mismo nombre.
     * @throws SQLException
     */
    public static boolean addCampoI(Campo campo) throws SQLException {
        if ((campo != null) && findByName(campo.getNombre()) == null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_INSERT)){
                ps.setString(1, campo.getNombre());
                ps.setString(2, campo.getTamano().name());
                ps.executeUpdate();
                return true;
            }

        }
        return false;

        /**
         * Actualiza un campo existente en la base de datos. El campo se identifica por su ID.
         * @param jugador jugador a actualizar con los nuevos datos. El ID del jugador no debe ser modificado.
         * @throws SQLException
         */
    }
    public static void updateCampoI(CampoInterior campo) throws SQLException {
        if ((campo != null) && findByName(campo.getNombre()) != null) {
            try (PreparedStatement ps = ConnectionBD.getInstance().getConnection().prepareStatement(SQL_UPDATE)){
                ps.setString(1, campo.getNombre());
                ps.setString(2, campo.getTamano().name());
                ps.executeUpdate();
            }
        }
    }




}
