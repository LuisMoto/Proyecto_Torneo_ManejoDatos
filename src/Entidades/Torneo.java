package Entidades;

import java.util.Date;

/**
 * Representa un torneo de ajedrez registrado en el sistema.
 * Almacena la información básica del torneo como nombre,
 * tipo de juego, fecha de inicio y duración.
 */
public class Torneo {

    private int id_torneo;
    private String nombre_torneo;
    private String tipo;
    private Date fecha_inicio;
    private int duracion_minutos;

    /**
     * Constructor vacío.
     */
    public Torneo() {
    }

    /**
     * Constructor con todos los atributos.
     *
     * @param id_torneo ID del torneo
     * @param nombre_torneo nombre del torneo
     * @param tipo tipo de torneo
     * @param fecha_inicio fecha de inicio
     * @param duracion_minutos duración en minutos
     */
    public Torneo(int id_torneo, String nombre_torneo, String tipo, Date fecha_inicio, int duracion_minutos) {
        this.id_torneo = id_torneo;
        this.nombre_torneo = nombre_torneo;
        this.tipo = tipo;
        this.fecha_inicio = fecha_inicio;
        this.duracion_minutos = duracion_minutos;
    }

    public int getIdTorneo() {
        return id_torneo;
    }

    public void setIdTorneo(int id_torneo) {
        this.id_torneo = id_torneo;
    }

    public String getNombreTorneo() {
        return nombre_torneo;
    }

    public void setNombreTorneo(String nombre_torneo) {
        this.nombre_torneo = nombre_torneo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFechaInicio() {
        return fecha_inicio;
    }

    public void setFechaInicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public int getDuracionMinutos() {
        return duracion_minutos;
    }

    public void setDuracionMinutos(int duracion_minutos) {
        this.duracion_minutos = duracion_minutos;
    }

    /**
     * Devuelve una representación legible del torneo.
     *
     * @return información resumida del torneo
     */
    @Override
    public String toString() {
        return "¤ ID: " + id_torneo
                + " ║ Torneo: " + nombre_torneo
                + " ║ Tipo: " + tipo
                + " ║ Duración: " + duracion_minutos + " min";
    }
}
