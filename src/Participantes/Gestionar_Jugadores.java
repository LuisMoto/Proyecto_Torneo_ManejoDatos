package Participantes;

import Entidades.Jugador;
import java.util.List;

/**
 * Clase encargada de cargar jugadores en un arreglo
 * para cumplir con el requisito de la práctica.
 */
public class Gestionar_Jugadores {

    private Jugador[] jugadores;

    public Gestionar_Jugadores(List<Jugador> listaJugadores) {

        jugadores = new Jugador[listaJugadores.size()];

        for (int i = 0; i < listaJugadores.size(); i++) {
            jugadores[i] = listaJugadores.get(i);
        }
    }

    public void mostrarJugadores() {

        if (jugadores.length == 0) {
            System.out.println("No hay jugadores registrados.");
            return;
        }

        System.out.println("\n«««««««««« LISTA DE JUGADORES »»»»»»»»»»");

        for (Jugador jugador : jugadores) {
            System.out.println(jugador);
        }
    }
}
