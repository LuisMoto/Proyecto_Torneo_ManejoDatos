package dao;

import Entidades.Jugador;
import conexion.ConexionMySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JugadorDAO {


/**
 * Inserta un nuevo jugador.
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
 * Busca un jugador por ID.
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
 * Busca jugador por nombre.
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
 * Obtiene todos los jugadores.
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
 * Actualiza el puntaje acumulado.
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
 * Obtiene ranking desde la vista.
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
 * Suma puntos al puntaje acumulado del jugador.
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
