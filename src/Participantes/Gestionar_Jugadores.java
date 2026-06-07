package Participantes;

import Entidades.Jugador;
import java.util.List;

/**
 * Gestiona los jugadores utilizando un arreglo.
 * La información obtenida desde la base de datos
 * se carga temporalmente en un arreglo para realizar
 * consultas y recorridos durante la ejecución del sistema.
 */
public class Gestionar_Jugadores {

    /**
     * Arreglo que almacena los jugadores cargados.
     */
    private Jugador[] jugadores;

    /**
     * Construye el arreglo de jugadores a partir
     * de una lista obtenida desde la base de datos.
     *
     * @param listaJugadores Lista de jugadores recuperados.
     */
    public Gestionar_Jugadores(List<Jugador> listaJugadores) {

        jugadores = new Jugador[listaJugadores.size()];

        for (int i = 0; i < listaJugadores.size(); i++) {
            jugadores[i] = listaJugadores.get(i);
        }
    }

    /**
     * Muestra todos los jugadores almacenados
     * en el arreglo.
     */
    public void mostrarJugadores() {

        if (jugadores.length == 0) {
            System.out.println("No hay jugadores registrados.");
            return;
        }

        System.out.println("\n======================= JUGADORES =======================");

        for (Jugador jugador : jugadores) {
            System.out.println(jugador);
        }
    }

    /**
     * Busca un jugador por su ID.
     *
     * @param idJugador ID del jugador a buscar.
     * @return Jugador encontrado o null si no existe.
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
     * Busca un jugador por su nombre.
     *
     * @param nombreJugador Nombre del jugador.
     * @return Jugador encontrado o null si no existe.
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
     * Devuelve el arreglo completo de jugadores.
     *
     * @return Arreglo de jugadores.
     */
    public Jugador[] getJugadores() {
        return jugadores;
    }

    /**
     * Obtiene la cantidad de jugadores
     * almacenados en el arreglo.
     *
     * @return Número de jugadores.
     */
    public int cantidadJugadores() {
        return jugadores.length;
    }

    /**
     * Obtiene un jugador según su posición
     * dentro del arreglo.
     *
     * @param indice Posición solicitada.
     * @return Jugador encontrado o null si
     * el índice es inválido.
     */
    public Jugador getJugador(int indice) {

        if (indice < 0 || indice >= jugadores.length) {
            return null;
        }

        return jugadores[indice];
    }

}
