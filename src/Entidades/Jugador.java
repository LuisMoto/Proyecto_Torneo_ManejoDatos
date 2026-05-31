package Entidades;

public class Jugador {

    private int id_jugador;
    private String nombre_jugador;
    private double puntaje_acumulado;

    public Jugador() {
        
    }

    public Jugador(int id_jugador, String nombre_jugador, double puntaje_acumulado) {
        this.id_jugador = id_jugador;
        this.nombre_jugador = nombre_jugador;
        this.puntaje_acumulado = puntaje_acumulado;
    }

    public int getIdJugador() {
        return id_jugador;
    }

    public void setIdJugador(int id_jugador) {
        this.id_jugador = id_jugador;
    }

    public String getNombreJugador() {
        return nombre_jugador;
    }

    public void setNombreJugador(String nombre_jugador) {
        this.nombre_jugador = nombre_jugador;
    }

    public double getPuntajeAcumulado() {
        return puntaje_acumulado;
    }

    public void setPuntajeAcumulado(double puntaje_acumulado) {
        this.puntaje_acumulado = puntaje_acumulado;
    }

    @Override
    public String toString() {
        return "¤ ID: " + id_jugador +
               " ║ Nombre: " + nombre_jugador +
               " ║ Puntaje: " + puntaje_acumulado;
    }
}

