package dao;

import Entidades.Torneo;
import conexion.ConexionMySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de acceso a datos (DAO) para la entidad Torneo.
 *
 * Permite realizar operaciones de persistencia sobre los torneos,
 * incluyendo registro, búsqueda y consulta de los torneos
 * almacenados en la base de datos.
 */
public class TorneoDAO {


/**
 * Inserta un nuevo torneo en la base de datos.
 *
 * @param torneo Torneo que será registrado.
 * @throws SQLException Si ocurre un error durante la inserción.
 */
public void insertar(Torneo torneo) throws SQLException {

    String sql = """
            INSERT INTO Torneo(nombre_torneo, tipo, fecha_inicio, duracion_minutos)
            VALUES (?, ?, ?, ?)
            """;

    try (Connection con = ConexionMySQL.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        ps.setString(1, torneo.getNombreTorneo());
        ps.setString(2, torneo.getTipo());
        ps.setTimestamp(3, new Timestamp(torneo.getFechaInicio().getTime()));
        ps.setInt(4, torneo.getDuracionMinutos());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();

        if (rs.next()) {

            torneo.setIdTorneo(rs.getInt(1));

        }
    }
}

/**
 * Busca un torneo a partir de su identificador.
 *
 * @param id ID del torneo.
 * @return Objeto Torneo si existe; null en caso contrario.
 * @throws SQLException Si ocurre un error durante la consulta.
 */
public Torneo buscarPorId(int id) throws SQLException {

    String sql = """
            SELECT *
            FROM Torneo
            WHERE id_torneo = ?
            """;

    try (Connection con = ConexionMySQL.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            return new Torneo(
                    rs.getInt("id_torneo"),
                    rs.getString("nombre_torneo"),
                    rs.getString("tipo"),
                    rs.getTimestamp("fecha_inicio"),
                    rs.getInt("duracion_minutos"));

        }
    }

    return null;
}

/**
 * Busca un torneo utilizando su nombre.
 *
 * @param nombre Nombre del torneo.
 * @return Objeto Torneo si existe; null en caso contrario.
 * @throws SQLException Si ocurre un error durante la consulta.
 */
public Torneo buscarPorNombre(String nombre) throws SQLException {

    String sql = """
            SELECT *
            FROM Torneo
            WHERE nombre_torneo = ?
            """;

    try (Connection con = ConexionMySQL.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, nombre);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            return new Torneo(
                    rs.getInt("id_torneo"),
                    rs.getString("nombre_torneo"),
                    rs.getString("tipo"),
                    rs.getTimestamp("fecha_inicio"),
                    rs.getInt("duracion_minutos"));
                    
        }
    }

    return null;
}


/**
 * Recupera todos los torneos registrados en la base de datos.
 *
 * @return Lista con todos los torneos almacenados.
 * @throws SQLException Si ocurre un error durante la consulta.
 */
public List<Torneo> obtenerTodos() throws SQLException {

    List<Torneo> torneos = new ArrayList<>();

    String sql = "SELECT * FROM Torneo";

    try (Connection con = ConexionMySQL.obtenerConexion();
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(sql)) {

        while (rs.next()) {

            torneos.add(
                    new Torneo(
                            rs.getInt("id_torneo"),
                            rs.getString("nombre_torneo"),
                            rs.getString("tipo"),
                            rs.getTimestamp("fecha_inicio"),
                            rs.getInt("duracion_minutos")
                    )
            );
        }
    }

    return torneos;
}


}

