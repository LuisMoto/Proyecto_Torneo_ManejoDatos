package excepciones;

/**
 * Excepción lanzada cuando el jugador de blancas y el de negras son el mismo
 * (mismos IDs), lo cual es inválido en una partida de ajedrez.
 */
public class JugadorClonException extends TorneoAjedrezException {

    private final int idBlancas;
    private final int idNegras;

    public JugadorClonException(int idBlancas, int idNegras) {
        super("El jugador de blancas (ID: " + idBlancas + ") y el jugador de negras (ID: " +
              idNegras + ") no pueden ser el mismo jugador.",
              "JUGADOR_CLON");
        this.idBlancas = idBlancas;
        this.idNegras = idNegras;
    }

    public int getIdBlancas() { return idBlancas; }
    public int getIdNegras() { return idNegras; }
}
