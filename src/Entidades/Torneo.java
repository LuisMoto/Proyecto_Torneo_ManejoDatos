package Entidades;

import java.util.Date;

public class Torneo {

    private int id_torneo;
    private String nombre_torneo;
    private String tipo;
    private Date fecha_inicio;
    private int duracion_minutos;

    public Torneo() {

    }

    public Torneo(int id_torneo, String nombre_torneo, String tipo, Date fecha_inicio, int duracion_minutos) {
        this.id_torneo = id_torneo;
        this.nombre_torneo = nombre_torneo;
        this.tipo = tipo;
        this.fecha_inicio = fecha_inicio;
        this.duracion_minutos = duracion_minutos;
    }

    public int obtener_IdTorneo() {
        return id_torneo;
    }

    public void modificar_IdTorneo(int id_torneo) {
        this.id_torneo = id_torneo;
    }

    public String obtener_NombreTorneo() {
        return nombre_torneo;
    }

    public void modificar_NombreTorneo(String nombre_torneo) {
        this.nombre_torneo = nombre_torneo;
    }

    public String obtener_Tipo() {
        return tipo;
    }

    public void modificar_Tipo(String tipo) {
        this.tipo = tipo;
    }

    public Date obtener_FechaInicio() {
        return fecha_inicio;
    }

    public void modificar_FechaInicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public int obtener_DuracionMinutos() {
        return duracion_minutos;
    }

    public void modificar_DuracionMinutos(int duracion_minutos) {
        this.duracion_minutos = duracion_minutos;
    }

    @Override
    public String toString() {
        return "ID: " + id_torneo +
               " | Torneo: " + nombre_torneo +
               " | Tipo: " + tipo +
               " | Duración: " + duracion_minutos + " min";
    }
}