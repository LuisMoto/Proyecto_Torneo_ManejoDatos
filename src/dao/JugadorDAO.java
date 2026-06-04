package dao;

import Entidades.Jugador;
import conexion.ConexionMySQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JugadorDAO {

    // public void insertar(Jugador jugador) throws SQLException {
    //     String sql = "INSERT INTO Jugador (nombre_jugador, puntaje_acumulado) VALUES (?, ?)";
    //     try (Connection con = ConexionMySQL.obtenerConexion();
    //          PreparedStatement ps = con.prepareStatement(sql)) {
    //         ps.setString(1, jugador.getNombreJugador());
    //         ps.setDouble(2, jugador.getPuntajeAcumulado());
    //         ps.executeUpdate();
    //     }
    // }

    public void insertar(Jugador jugador) throws SQLException {

        String sql = "INSERT INTO Jugador(nombre_jugador,puntaje_acumulado) VALUES(?,?)";
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





    public Jugador buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Jugador WHERE id_jugador = ?";
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

    public List<Jugador> obtenerTodos() throws SQLException {
        List<Jugador> lista = new ArrayList<>();
        String sql = "SELECT * FROM Jugador";
        try (Connection con = ConexionMySQL.obtenerConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Jugador(
                    rs.getInt("id_jugador"),
                    rs.getString("nombre_jugador"),
                    rs.getDouble("puntaje_acumulado")
                ));
            }
        }
        return lista;
    }
}
