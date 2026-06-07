package Participantes;

import Entidades.Partida;
import java.util.Stack;

/**
 * Gestiona el historial de partidas utilizando una pila.
 * Las partidas se almacenan siguiendo el principio LIFO
 */
public class Gestionar_Historial {

    /**
     * Pila que almacena el historial de partidas.
     */
    private Stack<Partida> historial;

    public Gestionar_Historial() {
        historial = new Stack<>();
    }

    /**
     * Agrega una partida al historial.
     *
     * @param partida Partida que se registrará en la pila.
     */
    public void agregarPartida(Partida partida) {
        historial.push(partida);
    }

    /**
     * Muestra las últimas cinco partidas registradas.
     * Si existen menos de cinco partidas, se muestran
     * únicamente las disponibles.
     */
    public void mostrarUltimasCinco() {

        if (historial.isEmpty()) {
            System.out.println("No hay partidas registradas.");
            return;
        }

        System.out.println("\n========= ÚLTIMAS 5 PARTIDAS =========");

        int contador = 0;

        for (int i = historial.size() - 1; i >= 0 && contador < 5; i--) {

            System.out.println(historial.get(i));
            contador++;
            
        }
    }

    /**
     * Obtiene la pila completa de partidas registradas.
     *
     * @return Historial completo de partidas.
     */
    public Stack<Partida> getHistorial() {
        return historial;
    }

    /**
     * Obtiene la cantidad total de partidas almacenadas
     * en el historial.
     *
     * @return Número de partidas registradas.
     */
    public int cantidadPartidas() {
        return historial.size();
    }
}
