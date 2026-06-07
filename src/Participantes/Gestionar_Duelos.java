package Participantes;

import Entidades.Partida;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Gestiona las partidas pendientes utilizando una cola.
 * Las partidas se atienden siguiendo el principio FIFO
 */
public class Gestionar_Duelos {

    /**
     * Cola que almacena las partidas pendientes.
     */
    private Queue<Partida> colaPartidas;

    /**
     * Inicializa una cola vacía de partidas.
     */
    public Gestionar_Duelos() {
        colaPartidas = new LinkedList<>();
    }

    /**
     * Agrega una nueva partida al final de la cola.
     *
     * @param partida Partida que será agregada.
     */
    public void agregarPartida(Partida partida) {
        colaPartidas.offer(partida);
    }

    /**
     * Obtiene y elimina la siguiente partida pendiente.
     *
     * @return La partida que se encontraba al inicio de la cola
     * o null si la cola está vacía.
     */
    public Partida ejecutarSiguientePartida() {
        return colaPartidas.poll();
    }

    /**
     * Muestra todas las partidas pendientes almacenadas en la cola
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
     * Consulta la siguiente partida pendiente
     * sin eliminarla de la cola.
     *
     * @return La siguiente partida pendiente o null
     * si la cola está vacía.
     */
    public Partida verSiguientePartida() {
        return colaPartidas.peek();
    }

    /**
     * Verifica si existen partidas pendientes.
     *
     * @return true si la cola está vacía;
     * false en caso contrario.
     */
    public boolean estaVacia() {
        return colaPartidas.isEmpty();
    }

    /**
     * Obtiene la cantidad de partidas
     * almacenadas en la cola.
     *
     * @return Número de partidas pendientes.
     */
    public int cantidadPartidas() {
        return colaPartidas.size();
    }
}
