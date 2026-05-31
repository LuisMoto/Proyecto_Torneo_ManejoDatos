package dao;

import Entidades.Partida;
import conexion.ConexionMySQL;
import java.sql.*;

public class PartidaDAO {

    public void insertar(Partida partida) throws SQLException {
        String sql = "INSERT INTO Partida (fecha, resultado, puntaje_blancas, puntaje_negras, id_torneo, jugador_blancas, jugador_negras) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionMySQL.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setTimestamp(1, new Timestamp(partida.getFecha().getTime()));
            ps.setString(2, partida.getResultado());
            ps.setDouble(3, partida.getPuntaje_blancas());
            ps.setDouble(4, partida.getPuntaje_negras());
            ps.setInt(5, partida.getTorneo().getId_torneo());
            ps.setInt(6, partida.getJugador_blancas().getId_jugador());
            ps.setInt(7, partida.getJugador_negras().getId_jugador());
            ps.executeUpdate();
        }
    }

    public Partida buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Partida WHERE id_partida = ?";
        try (Connection con = ConexionMySQL.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Partida(
                    rs.getInt("id_partida"),
                    rs.getTimestamp("fecha"),
                    rs.getString("resultado"),
                    rs.getDouble("puntaje_blancas"),
                    rs.getDouble("puntaje_negras"),
                    null, null, null // Torneo y Jugadores se cargan por separado
                );
            }
        }
        return null;
    }
}