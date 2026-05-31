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

    public int obtener_IdJugador() {
        return id_jugador;
    }

    public void modificar_IdJugador(int id_jugador) {
        this.id_jugador = id_jugador;
    }

    public String obtener_NombreJugador() {
        return nombre_jugador;
    }

    public void modificar_NombreJugador(String nombre_jugador) {
        this.nombre_jugador = nombre_jugador;
    }

    public double obtener_PuntajeAcumulado() {
        return puntaje_acumulado;
    }

    public void modificar_PuntajeAcumulado(double puntaje_acumulado) {
        this.puntaje_acumulado = puntaje_acumulado;
    }

    @Override
    public String toString() {
        return "¤ ID: " + id_jugador +
               " ║ Nombre: " + nombre_jugador +
               " ║ Puntaje: " + puntaje_acumulado;
    }
}

