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

    public int obtener_IdPartida() {
        return id_partida;
    }

    public void modificar_IdPartida(int id_partida) {
        this.id_partida = id_partida;
    }

    public Date obtener_Fecha() {
        return fecha;
    }

    public void modificar_Fecha(Date fecha) {
        this.fecha = fecha;
    }

    public String obtener_Resultado() {
        return resultado;
    }

    public void modificar_Resultado(String resultado) {
        this.resultado = resultado;
    }

    public double obtener_PuntajeBlancas() {
        return puntaje_blancas;
    }

    public void modificar_PuntajeBlancas(double puntaje_blancas) {
        this.puntaje_blancas = puntaje_blancas;
    }

    public double obtener_PuntajeNegras() {
        return puntaje_negras;
    }

    public void modificar_PuntajeNegras(double puntaje_negras) {
        this.puntaje_negras = puntaje_negras;
    }

    public Torneo obtener_Torneo() {
        return torneo;
    }

    public void modificar_Torneo(Torneo torneo) {
        this.torneo = torneo;
    }

    public Jugador obtener_JugadorBlancas() {
        return jugador_blancas;
    }

    public void modificar_JugadorBlancas(Jugador jugador_blancas) {
        this.jugador_blancas = jugador_blancas;
    }

    public Jugador obtener_JugadorNegras() {
        return jugador_negras;
    }

    public void modificar_JugadorNegras(Jugador jugador_negras) {
        this.jugador_negras = jugador_negras;
    }

    @Override
    public String toString() {
        return "Partida ID: " + id_partida +
               " | Blancas: " + jugador_blancas.obtener_NombreJugador() +
               " | Negras: " + jugador_negras.obtener_NombreJugador() +
               " | Resultado: " + resultado;
    }
}
