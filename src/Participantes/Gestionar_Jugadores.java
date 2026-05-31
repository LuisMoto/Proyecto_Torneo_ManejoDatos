package Participantes;

import Entidades.Jugador;

/**
 * Clase encargada de administrar los jugadores
 * mediante un arreglo.
 */
public class Gestionar_Jugadores {

    // Arreglo donde se almacenan los jugadores.
    private Jugador[] jugadores;

    // Cantidad de jugadores registrados.
    private int cantidad_jugadores;

    // Control de ID automático
    private int siguiente_id = 1;

    public Gestionar_Jugadores() {
        jugadores = new Jugador[100];
        cantidad_jugadores = 0;
    }

    /**
     * Agrega un jugador al arreglo.
     */
    public void agregar_Jugador(Jugador jugador) {

        if (cantidad_jugadores < jugadores.length) {

            // Asignar ID autom automaticamente
            jugador.setIdJugador(siguiente_id);
            siguiente_id++;

            jugadores[cantidad_jugadores] = jugador;
            cantidad_jugadores++;

        } else {
            System.out.println("No hay espacio para más jugadores.");
        }
    }

    // Mostrar jugadores registrados
    public void mostrar_Jugadores() {

        if (cantidad_jugadores == 0) {
            System.out.println("No hay jugadores registrados.");
            return;
        }

        System.out.println("\n«««««««««« JUGADORES REGISTRADOS »»»»»»»»»»");

        for (int i = 0; i < cantidad_jugadores; i++) {
            System.out.println(jugadores[i]);
        }
    }

    /**
     * Busca un jugador por ID.
     */
    public Jugador buscar_IdJugador(int id_jugador) {

        for (int i = 0; i < cantidad_jugadores; i++) {

            if (jugadores[i].getIdJugador() == id_jugador) {
                return jugadores[i];
            }
        }

        return null;
    }

    /**
     * Busca un jugador por nombre.
     */
    public Jugador buscar_NombreJugador(String nombre_jugador) {

        for (int i = 0; i < cantidad_jugadores; i++) {

            if (jugadores[i].getNombreJugador().equals(nombre_jugador)) {
                return jugadores[i];
            }
        }

        return null;
    }
}
