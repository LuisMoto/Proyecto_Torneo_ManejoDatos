package Participantes;

import Entidades.Partida;
import java.util.Stack;

/**
 * Gestiona el historial de partidas usando una pila
 */
public class Gestionar_Historial {

    private Stack<Partida> historial;

    public Gestionar_Historial() {
        historial = new Stack<>();
    }

    /**
     * Agrega una partida al historial
     */
    public void agregarPartida(Partida partida) {
        historial.push(partida);
    }

    /**
     * Muestra las últimas 5 partidas
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
     * Obtiene la pila completa
     */
    public Stack<Partida> getHistorial() {
        return historial;
    }

    /**
     * Devuelve la cantidad de partidas registradas
     */
    public int cantidadPartidas() {
        return historial.size();
    }
}
