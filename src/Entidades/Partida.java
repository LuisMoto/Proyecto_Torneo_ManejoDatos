package Entidades;

import java.util.Date;

/**
 * Representa una partida de ajedrez dentro de un torneo.
 *
 * Una partida registra la información de los jugadores
 * participantes, el torneo al que pertenece, el resultado
 * obtenido y los puntos asignados a cada jugador.
 */
public class Partida {

    private int id_partida;
    private Date fecha;                                     // Fecha en la que se programó o jugó la partida.
    private String resultado;                               // Resultado de la partida (1-0, 0-1 o 1/2-1/2)
    private double puntaje_blancas;
    private double puntaje_negras;
    private String estado;                                  // Estado actual de la partida (PENDIENTE o FINALIZADA).
    private Torneo torneo;                                  // Torneo al que pertenece la partida.
    private Jugador jugador_blancas;
    private Jugador jugador_negras;

    public Partida() {
    }

    /**
     * Constructor completo de la clase Partida.
     *
     * @param id_partida Identificador de la partida.
     * @param fecha Fecha de la partida.
     * @param resultado Resultado registrado.
     * @param puntaje_blancas Puntaje obtenido por blancas.
     * @param puntaje_negras Puntaje obtenido por negras.
     * @param estado Estado de la partida.
     * @param torneo Torneo asociado.
     * @param jugador_blancas Jugador con piezas blancas.
     * @param jugador_negras Jugador con piezas negras.
     */
    public Partida(int id_partida, Date fecha, String resultado, double puntaje_blancas, double puntaje_negras, String estado, Torneo torneo, Jugador jugador_blancas,Jugador jugador_negras) {
        this.id_partida = id_partida;
        this.fecha = fecha;
        this.resultado = resultado;
        this.puntaje_blancas = puntaje_blancas;
        this.puntaje_negras = puntaje_negras;
        this.estado = estado;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    /**
     * Devuelve una representación legible de la partida
     * para mostrarla en consola.
     *
     * @return Cadena con los datos principales de la partida.
     */
    @Override
    public String toString() {

        String nombreTorneo =
                (torneo != null)
                ? torneo.getNombreTorneo()
                : "Sin torneo";

        String blancas =
                (jugador_blancas != null)
                ? jugador_blancas.getNombreJugador()
                : "Sin asignar";

        String negras =
                (jugador_negras != null)
                ? jugador_negras.getNombreJugador()
                : "Sin asignar";

        return "\n══════════════════════════════════════"
                + "\nTorneo: " + nombreTorneo
                + "\nPartida #" + id_partida
                + "\nBlancas: " + blancas
                + "\nNegras: " + negras
                + "\nResultado: " + resultado
                + "\nPuntos Blancas: " + puntaje_blancas
                + "\nPuntos Negras: " + puntaje_negras
                + "\nEstado: " + estado
                + "\nFecha: " + fecha;
    }
}
