package Entidades;

import java.util.Date;

public class Partida {

    private int idPartida;
    private Date fecha;
    private String resultado;

    private double puntajeBlancas;
    private double puntajeNegras;

    private Torneo torneo;

    private Jugador jugadorBlancas;
    private Jugador jugadorNegras;

    public Partida() {
    }

    public Partida(int idPartida,
                   Date fecha,
                   String resultado,
                   double puntajeBlancas,
                   double puntajeNegras,
                   Torneo torneo,
                   Jugador jugadorBlancas,
                   Jugador jugadorNegras) {

        this.idPartida = idPartida;
        this.fecha = fecha;
        this.resultado = resultado;
        this.puntajeBlancas = puntajeBlancas;
        this.puntajeNegras = puntajeNegras;
        this.torneo = torneo;
        this.jugadorBlancas = jugadorBlancas;
        this.jugadorNegras = jugadorNegras;
    }

    public int getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
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
        return puntajeBlancas;
    }

    public void setPuntajeBlancas(double puntajeBlancas) {
        this.puntajeBlancas = puntajeBlancas;
    }

    public double getPuntajeNegras() {
        return puntajeNegras;
    }

    public void setPuntajeNegras(double puntajeNegras) {
        this.puntajeNegras = puntajeNegras;
    }

    public Torneo getTorneo() {
        return torneo;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    public Jugador getJugadorBlancas() {
        return jugadorBlancas;
    }

    public void setJugadorBlancas(Jugador jugadorBlancas) {
        this.jugadorBlancas = jugadorBlancas;
    }

    public Jugador getJugadorNegras() {
        return jugadorNegras;
    }

    public void setJugadorNegras(Jugador jugadorNegras) {
        this.jugadorNegras = jugadorNegras;
    }
}
