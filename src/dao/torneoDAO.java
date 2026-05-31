package dao;

import Entidades.Torneo;
import conexion.ConexionMySQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TorneoDAO {

    public void insertar(Torneo torneo) throws SQLException {
        String sql = "INSERT INTO Torneo (nombre_torneo, tipo, fecha_inicio, duracion_minutos) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionMySQL.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, torneo.getNombre_torneo());
            ps.setString(2, torneo.getTipo());
            ps.setTimestamp(3, new Timestamp(torneo.getFecha_inicio().getTime()));
            ps.setInt(4, torneo.getDuracion_minutos());
            ps.executeUpdate();
        }
    }

    public Torneo buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Torneo WHERE id_torneo = ?";
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
                    rs.getInt("duracion_minutos")
                );
            }
        }
        return null;
    }

    public List<Torneo> obtenerTodos() throws SQLException {
        List<Torneo> lista = new ArrayList<>();
        String sql = "SELECT * FROM Torneo";
        try (Connection con = ConexionMySQL.obtenerConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Torneo(
                    rs.getInt("id_torneo"),
                    rs.getString("nombre_torneo"),
                    rs.getString("tipo"),
                    rs.getTimestamp("fecha_inicio"),
                    rs.getInt("duracion_minutos")
                ));
            }
        }
        return lista;
    }
}