package es.Luque.AirsoftManager.DAO;

import es.Luque.AirsoftManager.dataaccess.ConnectionBD;
import es.Luque.AirsoftManager.model.Campo;
import es.Luque.AirsoftManager.model.CampoExterior;
import es.Luque.AirsoftManager.model.CampoInterior;
import es.Luque.AirsoftManager.model.enums.TamanoCampo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CampoDAO {
    private final static String SQL_ALL_INTERIOR = "SELECT * FROM campointerior";
    private final static String SQL_ALL_EXTERIOR = "SELECT * FROM campoexterior";
    private final static String SQL_FIND_BY_ID = "SELECT * FROM campo where ID =?";
    private final static String SQL_FIND_BY_NAME = "SELECT * FROM campo where nombre =?";
    private final static String SQL_INSERT = "INSERT INTO campo (nombre, tamano) VALUES (?, ?)";
    private final static String SQL_UPDATE = "UPDATE campo SET nombre =?, tamano =? WHERE ID =?";
    private final static String SQL_DELETE = "DELETE FROM campo WHERE ID =?";

    public static ArrayList<Campo> findAllExterior() throws SQLException {
        CampoExterior campoE = null;
        ArrayList<Campo> camposE = new ArrayList<>();

        Statement st = ConnectionBD.getInstance().getConnection().createStatement();
        ResultSet rs = st.executeQuery(SQL_ALL_EXTERIOR);
        while (rs.next()) {
            int ID = rs.getInt("ID");
            String nombre = rs.getString("nombre");
            campoE = new CampoExterior(ID, nombre);
            camposE.add(campoE);
        }
        return camposE;

    }
    public static ArrayList<Campo> findAllInterior() throws SQLException {
        CampoInterior campoI = null;
        ArrayList<Campo> camposE = new ArrayList<>();

        Statement st = ConnectionBD.getInstance().getConnection().createStatement();
        ResultSet rs = st.executeQuery(SQL_ALL_INTERIOR);
        while (rs.next()) {
            int ID = rs.getInt("ID");
            String nombre = rs.getString("nombre");
            TamanoCampo tamano = TamanoCampo.valueOf(rs.getString("tamano")) ;
            campoI = new CampoInterior(ID, nombre, tamano);
            camposE.add(campoI);
        }
        return camposE;
    }
}
