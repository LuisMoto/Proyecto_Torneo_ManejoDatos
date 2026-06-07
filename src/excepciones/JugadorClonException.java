package excepciones;

/**
 * Excepción que se lanza cuando se intenta programar
 * una partida utilizando el mismo jugador para las
 * piezas blancas y negras.
 */
public class JugadorClonException extends TorneoAjedrezException {

    /**
     * Construye una excepción indicando los ID
     * de los jugadores seleccionados.
     *
     * @param idBlancas ID del jugador asignado a las blancas.
     * @param idNegras ID del jugador asignado a las negras.
     */
    public JugadorClonException(int idBlancas,int idNegras) {
        super("El jugador de blancas (" + idBlancas + ") y el jugador de negras (" + idNegras + ") no pueden ser el mismo.", "JUGADOR_CLON");
    }
}
