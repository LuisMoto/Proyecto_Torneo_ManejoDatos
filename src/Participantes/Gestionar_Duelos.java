package Participantes;

import Entidades.Jugador;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Clase encargada de gestionar los duelos del torneo
 * utilizando una estructura de cola.
 *
 * La cola funciona de forma:
 * Primero en entrar → Primero en salir.
 */
public class Gestionar_Duelos {

    // Cola donde se almacenan los jugadores en espera de duelo
    private Queue<Jugador> colaDuelos;

    /**
     * Constructor
     * Inicializa la estructura de cola usando LinkedList
     */
    public Gestionar_Duelos() {
        this.colaDuelos = new LinkedList<>();
    }

    /**
     * Agrega un jugador a la cola de duelos (entra al final de la cola)
     */
    public void agregarJugador(Jugador jugador) {
        colaDuelos.offer(jugador);
    }

    /**
     * Muestra todos los jugadores en la cola de espera
     */
    public void mostrarCola() {

        if (colaDuelos.isEmpty()) {
            System.out.println("No hay jugadores en cola.");
            return;
        }

        System.out.println("\n««««««««««««« COLA DE DUELOS »»»»»»»»»»»»»");

        for (Jugador j : colaDuelos) {
            System.out.println(j);
        }
    }

    /**
     * Realiza una ronda de duelos.
     *
     * Toma jugadores de 2 en 2:
     * - El primero juega contra el segundo
     * - El tercero contra el cuarto
     *
     * Si queda uno solo, se queda en espera.
     */
    public void realizarRonda() {

        System.out.println("\n============= INICIANDO RONDA =============");

        // Validación: al menos 2 jugadores
        if (colaDuelos.size() < 2) {
            System.out.println("No hay suficientes jugadores.");
            return;
        }

        // Mientras haya al menos 2 jugadores en cola
        while (colaDuelos.size() >= 2) {

            // Sacar jugadores del frente de la cola
            Jugador j1 = colaDuelos.poll();
            Jugador j2 = colaDuelos.poll();

            // Mostrar el enfrentamiento
            System.out.println("■ " + j1.getNombreJugador() + " vs " + j2.getNombreJugador());
        }

        // Si queda un jugador sin rival
        if (!colaDuelos.isEmpty()) {
            System.out.println("\nJugador en espera: " + colaDuelos.peek().getNombreJugador());
        }

        System.out.println("\n ============= RONDA TERMINADA =============");
    }

    /**
     * Limpia completamente la cola de duelos
     */
    public void limpiarCola() {
        colaDuelos.clear();
    }
}
