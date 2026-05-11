package es.Luque.AirsoftManager.dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBD {
    private final String FILE = "connection.xml";
    private Connection con;
    private ConnectionProperties properties;
    //1. crear una instancia de la propia clase
    private static ConnectionBD _instance;

    //2. Constructor privado
    private ConnectionBD() {
        //leo del connection.xml los datos para la conexion
        properties = XMLManager.readXML(new ConnectionProperties(), FILE);
    }

    public void connect() throws SQLException {
        try{
            con = DriverManager.getConnection(properties.getURL(), properties.getUser(), properties.getPassword());
        }catch(SQLException e){
            con=null;
            throw e;
        }
    }

    private void disconnect() {
        con = null;
    }

    private boolean isConnected() {
        return con != null;
    }

    //3. metodo publico que me devuelve la instancia ya creada, si la primera vez la crea

    public static ConnectionBD getInstance() {
        if(_instance==null){
            _instance = new ConnectionBD();
            try {
                _instance.connect();
            } catch (SQLException e) {
                System.err.println("Error al conectar a la base de datos: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return _instance;
    }

    public Connection getConnection() {
        return con;
    }
}
