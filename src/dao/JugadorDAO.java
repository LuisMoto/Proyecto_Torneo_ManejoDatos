package dao;

import Entidades.Jugador;
import conexion.ConexionMySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de acceso a datos (DAO) para la entidad Jugador.
 *
 * Permite realizar operaciones y consultas relacionadas
 * con los jugadores almacenados en la base de datos,
 * incluyendo búsqueda, actualización de puntajes y ranking.
 */
public class JugadorDAO {



/**
 * Registra un nuevo jugador en la base de datos.
 *
 * @param jugador Jugador que será almacenado.
 * @throws SQLException Si ocurre un error durante la inserción.
 */
public void insertar(Jugador jugador) throws SQLException {

    String sql = """
            INSERT INTO Jugador(nombre_jugador, puntaje_acumulado)
            VALUES (?, ?)
            """;

    try (Connection con = ConexionMySQL.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        ps.setString(1, jugador.getNombreJugador());
        ps.setDouble(2, jugador.getPuntajeAcumulado());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();

        if (rs.next()) {
            jugador.setIdJugador(rs.getInt(1));
        }
    }
}


/**
 * Busca un jugador utilizando su identificador único.
 *
 * @param id ID del jugador.
 * @return Objeto Jugador si existe; null en caso contrario.
 * @throws SQLException Si ocurre un error en la consulta.
 */
public Jugador buscarPorId(int id) throws SQLException {

    String sql = """
            SELECT *
            FROM Jugador
            WHERE id_jugador = ?
            """;

    try (Connection con = ConexionMySQL.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            return new Jugador(
                    rs.getInt("id_jugador"),
                    rs.getString("nombre_jugador"),
                    rs.getDouble("puntaje_acumulado")
            );

        }
    }

    return null;
}


/**
 * Busca un jugador por su nombre.
 *
 * @param nombre Nombre del jugador.
 * @return Objeto Jugador si existe; null en caso contrario.
 * @throws SQLException Si ocurre un error en la consulta.
 */
public Jugador buscarPorNombre(String nombre) throws SQLException {

    String sql = """
            SELECT *
            FROM Jugador
            WHERE nombre_jugador = ?
            """;

    try (Connection con = ConexionMySQL.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, nombre);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            return new Jugador(
                    rs.getInt("id_jugador"),
                    rs.getString("nombre_jugador"),
                    rs.getDouble("puntaje_acumulado")
            );

        }
    }

    return null;
}


/**
 * Recupera todos los jugadores registrados.
 *
 * @return Lista con todos los jugadores almacenados.
 * @throws SQLException Si ocurre un error en la consulta.
 */
public List<Jugador> obtenerTodos() throws SQLException {

    List<Jugador> jugadores = new ArrayList<>();

    String sql = """
            SELECT *
            FROM Jugador
            """;

    try (Connection con = ConexionMySQL.obtenerConexion();
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(sql)) {

        while (rs.next()) {

            jugadores.add(
                    new Jugador(
                            rs.getInt("id_jugador"),
                            rs.getString("nombre_jugador"),
                            rs.getDouble("puntaje_acumulado")
                    )
            );

        }
    }

    return jugadores;
}

/**
 * Actualiza el puntaje acumulado de un jugador.
 *
 * @param idJugador ID del jugador.
 * @param nuevoPuntaje Nuevo puntaje a asignar.
 * @throws SQLException Si ocurre un error durante la actualización.
 */
public void actualizarPuntaje(int idJugador,
                              double nuevoPuntaje) throws SQLException {

    String sql = """
            UPDATE Jugador
            SET puntaje_acumulado = ?
            WHERE id_jugador = ?
            """;

    try (Connection con = ConexionMySQL.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setDouble(1, nuevoPuntaje);
        ps.setInt(2, idJugador);

        ps.executeUpdate();
    }
}


/**
 * Obtiene el ranking general utilizando la vista Vista_Ranking.
 *
 * @return Lista de jugadores ordenada por puntaje acumulado.
 * @throws SQLException Si ocurre un error en la consulta.
 */
public List<Jugador> obtenerRanking() throws SQLException {

    List<Jugador> ranking = new ArrayList<>();

    String sql = """
            SELECT *
            FROM Vista_Ranking
            ORDER BY puntaje_acumulado DESC
            """;

    try (Connection con = ConexionMySQL.obtenerConexion();
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(sql)) {

        while (rs.next()) {

            ranking.add(
                    new Jugador(
                            0,
                            rs.getString("nombre_jugador"),
                            rs.getDouble("puntaje_acumulado")
                    )
            );
            
        }
    }

    return ranking;
}


/**
 * Incrementa el puntaje acumulado de un jugador.
 *
 * @param idJugador ID del jugador.
 * @param puntos Cantidad de puntos a sumar.
 * @throws SQLException Si ocurre un error durante la actualización.
 */
public void sumarPuntaje(
        int idJugador,
        double puntos)
        throws SQLException {

    String sql = """
        UPDATE Jugador
        SET puntaje_acumulado =
        puntaje_acumulado + ?
        WHERE id_jugador = ?
        """;

    try (Connection con = ConexionMySQL.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setDouble(1, puntos);
        ps.setInt(2, idJugador);

        ps.executeUpdate();
    }
}


}
