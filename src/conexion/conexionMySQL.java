package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {
    private static final String URL    = "jdbc:mysql://localhost:3306/torneo_ajedrez";
    private static final String USUARIO = "root";
    private static final String CLAVE   = "tu_contraseña";

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}
    
