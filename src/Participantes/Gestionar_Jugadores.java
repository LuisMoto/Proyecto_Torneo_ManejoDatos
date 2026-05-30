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


    // Constructor para gestionar a los jugadores
    public Gestionar_Jugadores() {
        jugadores = new Jugador[100];
        cantidad_jugadores = 0;
    }

    /**
     * Agrega un jugador al arreglo.
     *
     * @param jugador Jugador a registrar.
     */
    public void agregar_Jugador(Jugador jugador) {

        if (cantidad_jugadores < jugadores.length) {
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

        System.out.println("\n«««««««««««««««««««« JUGADORES RESGISTRADOS »»»»»»»»»»»»»»»»»»»»");

        for (int i = 0; i < cantidad_jugadores; i++) {
            System.out.println(jugadores[i]);
        }

    }

    /**
     * Busca un jugador por ID.
     *
     * @param id_jugador ID del jugador
     * @return Jugador encontrado o null
     */
    public Jugador buscar_IdJugador(int id_jugador) {

        for (int i = 0; i < cantidad_jugadores; i++) {

            if (jugadores[i].obtener_IdJugador() == id_jugador) {
                return jugadores[i];
            }

        }

        return null;

    }

    /**
     * Busca un jugador por nombre.
     *
     * @param nombre_jugador Nombre del jugador.
     * @return Jugador encontrado o null.
     */
    public Jugador buscar_NombreJugador(String nombre_jugador) {

        for (int i = 0; i < cantidad_jugadores; i++) {

            if (jugadores[i]
                    .obtener_NombreJugador()
                    .equals(nombre_jugador)) {

                return jugadores[i];
            }

        }

        return null;

    }

}
