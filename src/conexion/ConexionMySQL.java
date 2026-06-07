package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Clase encargada de establecer la conexión con la base de datos MySQL.
 * Centraliza los parámetros de conexión para que puedan ser utilizados
 * por todas las clases DAO del sistema.
 *
 * Base de datos: torneo_ajedrez
 */
public class ConexionMySQL {
    private static final String URL    = "jdbc:mysql://localhost:3306/torneo_ajedrez";
    private static final String USUARIO = "root";
    private static final String CLAVE   = "tu_contraseña";


    /**
     * Obtiene una conexión activa con la base de datos.
     *
     * @return objeto Connection listo para realizar consultas SQL.
     * @throws SQLException si ocurre un error al conectarse.
     */
    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
    
}
