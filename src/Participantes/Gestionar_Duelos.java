package Participantes;

import Entidades.Partida;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Gestiona las partidas pendientes usando una cola
 */
public class Gestionar_Duelos {

    private Queue<Partida> colaPartidas;

    public Gestionar_Duelos() {
        colaPartidas = new LinkedList<>();
    }

    /**
     * Agrega una partida a la cola
     */
    public void agregarPartida(Partida partida) {
        colaPartidas.offer(partida);
    }

    /**
     * Obtiene y elimina la siguiente partida
     */
    public Partida ejecutarSiguientePartida() {
        return colaPartidas.poll();
    }

    /**
     * Muestra todas las partidas pendientes
     */
    public void mostrarCola() {

        if (colaPartidas.isEmpty()) {
            System.out.println("No hay partidas pendientes.");
            return;
        }

        System.out.println("\n============= PARTIDAS PENDIENTES =============");

        for (Partida partida : colaPartidas) {
            System.out.println(partida);
        }
    }

    /**
     * Consulta la siguiente partida sin eliminarla
     */
    public Partida verSiguientePartida() {
        return colaPartidas.peek();
    }

    /**
     * Verifica si la cola está vacía
     */
    public boolean estaVacia() {
        return colaPartidas.isEmpty();
    }

    /**
     * Cantidad de partidas pendientes
     */
    public int cantidadPartidas() {
        return colaPartidas.size();
    }
}
