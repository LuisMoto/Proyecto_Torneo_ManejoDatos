package Participantes;

import Entidades.Jugador;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Clase encargada de gestionar los duelos del torneo
 * utilizando una estructura de cola.
 */
public class Gestionar_Duelos {

    private Queue<Jugador> colaDuelos;

    public Gestionar_Duelos() {
        this.colaDuelos = new LinkedList<>();
    }

    /**
     * Agrega un jugador a la cola de duelos.
     */
    public void agregarJugador(Jugador jugador) {
        colaDuelos.offer(jugador);
    }

    /**
     * Muestra todos los jugadores en espera de duelo.
     */
    public void mostrarCola() {

        if (colaDuelos.isEmpty()) {
            System.out.println("No hay jugadores en cola de duelos.");
            return;
        }

        System.out.println("\n««««««««««««« COLA DE DUELOS »»»»»»»»»»»»»");

        for (Jugador j : colaDuelos) {
            System.out.println(j);
        }
    }

    /**
     * Realiza un duelo entre los dos primeros jugadores.
     */
    public void realizarDuelo() {

        if (colaDuelos.size() < 2) {
            System.out.println("No hay suficientes jugadores para realizar un duelo.");
            return;
        }

        Jugador jugador1 = colaDuelos.poll();
        Jugador jugador2 = colaDuelos.poll();

        System.out.println("\n============= DUEL0 INICIADO =============");
        System.out.println("Blancas: " + jugador1.getNombreJugador());
        System.out.println("Negras: " + jugador2.getNombreJugador());
    }

    /**
     * Ver siguiente jugador en turno.
     */
    public Jugador siguiente() {
        return colaDuelos.peek();
    }

    /**
     * Verifica si la cola está vacía.
     */
    public boolean estaVacia() {
        return colaDuelos.isEmpty();
    }
}
