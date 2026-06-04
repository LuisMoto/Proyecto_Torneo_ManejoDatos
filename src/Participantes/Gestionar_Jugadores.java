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

    /**
     * Muestra todos los jugadores.
     */
    public void mostrarJugadores() {

        if (jugadores.length == 0) {
            System.out.println("No hay jugadores registrados.");
            return;
        }

        System.out.println("\n««««««««««««««« JUGADORES »»»»»»»»»»»»»»»");

        for (Jugador jugador : jugadores) {
            System.out.println(jugador);
        }
    }

    /**
     * Busca un jugador por ID.
     */
    public Jugador buscar_IdJugador(int idJugador) {

        for (Jugador jugador : jugadores) {

            if (jugador.getIdJugador() == idJugador) {
                return jugador;
            }
        }

        return null;
    }

    /**
     * Busca un jugador por nombre.
     */
    public Jugador buscar_NombreJugador(String nombreJugador) {

        for (Jugador jugador : jugadores) {

            if (jugador.getNombreJugador().equalsIgnoreCase(nombreJugador)) {
                return jugador;
            }
        }

        return null;
    }

    /**
     * Devuelve el arreglo de jugadores.
     */
    public Jugador[] getJugadores() {
        return jugadores;
    }
}
