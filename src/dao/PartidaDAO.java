package dao;

import Entidades.Jugador;
import Entidades.Partida;
import Entidades.Torneo;
import conexion.ConexionMySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartidaDAO {

/**
 * Insertar partida.
 */
public void insertar(Partida partida) throws SQLException {

    String sql = """
        INSERT INTO Partida(fecha, resultado, puntaje_blancas, puntaje_negras,id_torneo, jugador_blancas, jugador_negras, estado)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

    try (Connection con = ConexionMySQL.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        ps.setTimestamp(1, new Timestamp(partida.getFecha().getTime()));
        ps.setString(2, partida.getResultado());
        ps.setDouble(3, partida.getPuntajeBlancas());
        ps.setDouble(4, partida.getPuntajeNegras());
        ps.setInt(5, partida.getTorneo().getIdTorneo());
        ps.setInt(6, partida.getJugadorBlancas().getIdJugador());
        ps.setInt(7, partida.getJugadorNegras().getIdJugador());
        ps.setString(8, partida.getEstado());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();

        if (rs.next()) {
            partida.setIdPartida(rs.getInt(1));
        }
    }
}

/**
 * Buscar partida por ID.
 */
public Partida buscarPorId(int id) throws SQLException {

    String sql = """
            SELECT p.*,
            t.nombre_torneo,
            jb.nombre_jugador AS nombre_blancas,
            jn.nombre_jugador AS nombre_negras
            FROM Partida p
            INNER JOIN Torneo t
            ON p.id_torneo = t.id_torneo
            INNER JOIN Jugador jb
            ON p.jugador_blancas = jb.id_jugador
            INNER JOIN Jugador jn
            ON p.jugador_negras = jn.id_jugador
            WHERE p.id_partida = ?
            """;

    try (Connection con = ConexionMySQL.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            Jugador blancas = new Jugador();
            blancas.setIdJugador(rs.getInt("jugador_blancas"));
            blancas.setNombreJugador(rs.getString("nombre_blancas"));

            Jugador negras = new Jugador();
            negras.setIdJugador(rs.getInt("jugador_negras"));
            negras.setNombreJugador(rs.getString("nombre_negras"));

            Torneo torneo = new Torneo();
            torneo.setIdTorneo(rs.getInt("id_torneo"));
            torneo.setNombreTorneo(rs.getString("nombre_torneo"));

            Partida partida = new Partida();
            partida.setIdPartida(rs.getInt("id_partida"));
            partida.setFecha(rs.getTimestamp("fecha"));
            partida.setResultado(rs.getString("resultado"));
            partida.setPuntajeBlancas(rs.getDouble("puntaje_blancas"));
            partida.setPuntajeNegras(rs.getDouble("puntaje_negras"));
            partida.setEstado(rs.getString("estado"));
            partida.setJugadorBlancas(blancas);
            partida.setJugadorNegras(negras);
            partida.setTorneo(torneo);

            return partida;
        }
    }

    return null;
}

/**
 * Historial de partidas de un jugador.
 */
public List<Partida> obtenerPartidasJugador(
        int idJugador)
        throws SQLException {

    List<Partida> lista = new ArrayList<>();

    String sql = """
            SELECT p.*,
            t.nombre_torneo,
            jb.nombre_jugador AS nombre_blancas,
            jn.nombre_jugador AS nombre_negras
            FROM Partida p
            INNER JOIN Torneo t
            ON p.id_torneo = t.id_torneo
            INNER JOIN Jugador jb
            ON p.jugador_blancas = jb.id_jugador
            INNER JOIN Jugador jn
            ON p.jugador_negras = jn.id_jugador
            WHERE p.jugador_blancas = ?
            OR p.jugador_negras = ?
            ORDER BY p.fecha DESC
            Limit 5
            """;

    try (Connection con = ConexionMySQL.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idJugador);
        ps.setInt(2, idJugador);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            Jugador blancas = new Jugador();
            blancas.setIdJugador(rs.getInt("jugador_blancas"));
            blancas.setNombreJugador(rs.getString("nombre_blancas"));

            Jugador negras = new Jugador();
            negras.setIdJugador(rs.getInt("jugador_negras"));
            negras.setNombreJugador(rs.getString("nombre_negras"));

            Torneo torneo = new Torneo();
            torneo.setIdTorneo(rs.getInt("id_torneo"));
            torneo.setNombreTorneo(rs.getString("nombre_torneo"));

            Partida partida = new Partida();
            partida.setIdPartida(rs.getInt("id_partida"));
            partida.setFecha(rs.getTimestamp("fecha"));
            partida.setResultado(rs.getString("resultado"));
            partida.setPuntajeBlancas(rs.getDouble("puntaje_blancas"));
            partida.setPuntajeNegras(rs.getDouble("puntaje_negras"));
            partida.setEstado(rs.getString("estado"));
            partida.setJugadorBlancas(blancas);
            partida.setJugadorNegras(negras);
            partida.setTorneo(torneo);
            lista.add(partida);
        }
    }

    return lista;
}

/**
 * Actualizar estado.
 */
public void actualizarEstado(
        int idPartida,
        String estado)
        throws SQLException {

    String sql ="UPDATE Partida SET estado = ? WHERE id_partida = ?";

    try (Connection con = ConexionMySQL.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, estado);
        ps.setInt(2, idPartida);
        ps.executeUpdate();
    }
}

/**
 * Partidas pendientes.
 */
public List<Partida> obtenerPendientes()
        throws SQLException {

    List<Partida> lista = new ArrayList<>();

    String sql = """
            SELECT p.*,
            t.nombre_torneo,
            jb.nombre_jugador AS nombre_blancas,
            jn.nombre_jugador AS nombre_negras
            FROM Partida p
            INNER JOIN Torneo t
            ON p.id_torneo = t.id_torneo
            INNER JOIN Jugador jb
            ON p.jugador_blancas = jb.id_jugador
            INNER JOIN Jugador jn
            ON p.jugador_negras = jn.id_jugador
            WHERE p.estado = 'PENDIENTE'
            """;

    try (Connection con = ConexionMySQL.obtenerConexion();
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(sql)) {

        while (rs.next()) {

            Jugador blancas = new Jugador();
            blancas.setIdJugador(rs.getInt("jugador_blancas"));
            blancas.setNombreJugador(rs.getString("nombre_blancas"));

            Jugador negras = new Jugador();
            negras.setIdJugador(rs.getInt("jugador_negras"));
            negras.setNombreJugador(rs.getString("nombre_negras"));

            Torneo torneo = new Torneo();torneo.setIdTorneo(rs.getInt("id_torneo"));
            torneo.setNombreTorneo(rs.getString("nombre_torneo"));

            Partida partida = new Partida();
            partida.setIdPartida(rs.getInt("id_partida"));
            partida.setFecha(rs.getTimestamp("fecha"));
            partida.setResultado(rs.getString("resultado"));
            partida.setPuntajeBlancas(rs.getDouble("puntaje_blancas"));
            partida.setPuntajeNegras(rs.getDouble("puntaje_negras"));
            partida.setEstado(rs.getString("estado"));
            partida.setJugadorBlancas(blancas);
            partida.setJugadorNegras(negras);
            partida.setTorneo(torneo);

            lista.add(partida);
        }
    }

    return lista;
}

/**
 * Actualiza resultado y puntajes.
 */
public void actualizarResultado(
        int idPartida,
        String resultado,
        double puntajeBlancas,
        double puntajeNegras)
        throws SQLException {

    String sql = """
            UPDATE Partida
            SET resultado = ?,
            puntaje_blancas = ?,
            puntaje_negras = ?,
            estado = 'FINALIZADA'
            WHERE id_partida = ?
            """;

    try (Connection con = ConexionMySQL.obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, resultado);
        ps.setDouble(2, puntajeBlancas);
        ps.setDouble(3, puntajeNegras);
        ps.setInt(4, idPartida);

        ps.executeUpdate();
    }
}

}
