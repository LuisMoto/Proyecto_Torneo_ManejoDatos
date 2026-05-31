package Entidades;

import java.util.Date;

public class Partida {

    private int id_partida;
    private Date fecha;
    private String resultado;
    private double puntaje_blancas;
    private double puntaje_negras;

    private Torneo torneo;

    private Jugador jugador_blancas;
    private Jugador jugador_negras;

    public Partida() {

    }

    public Partida(int id_partida, Date fecha, String resultado, double puntaje_blancas, double puntaje_negras, Torneo torneo, Jugador jugador_blancas, Jugador jugador_negras) {
        this.id_partida = id_partida;
        this.fecha = fecha;
        this.resultado = resultado;
        this.puntaje_blancas = puntaje_blancas;
        this.puntaje_negras = puntaje_negras;
        this.torneo = torneo;
        this.jugador_blancas = jugador_blancas;
        this.jugador_negras = jugador_negras;
    }

    public int getIdPartida() {
        return id_partida;
    }

    public void setIdPartida(int id_partida) {
        this.id_partida = id_partida;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public double getPuntajeBlancas() {
        return puntaje_blancas;
    }

    public void setPuntajeBlancas(double puntaje_blancas) {
        this.puntaje_blancas = puntaje_blancas;
    }

    public double getPuntajeNegras() {
        return puntaje_negras;
    }

    public void setPuntajeNegras(double puntaje_negras) {
        this.puntaje_negras = puntaje_negras;
    }

    public Torneo getTorneo() {
        return torneo;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    public Jugador getJugadorBlancas() {
        return jugador_blancas;
    }

    public void setJugadorBlancas(Jugador jugador_blancas) {
        this.jugador_blancas = jugador_blancas;
    }

    public Jugador getJugadorNegras() {
        return jugador_negras;
    }

    public void setJugadorNegras(Jugador jugador_negras) {
        this.jugador_negras = jugador_negras;
    }

    @Override
    public String toString() {
        return "¤ Partida ID: " + id_partida +
               " ║ Blancas: " + jugador_blancas.getNombreJugador() +
               " ║ Negras: " + jugador_negras.getNombreJugador() +
               " ║ Resultado: " + resultado;
    }
}
