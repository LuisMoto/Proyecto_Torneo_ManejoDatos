package excepciones;

public class JugadorClonException extends TorneoAjedrezException {

    public JugadorClonException(int idBlancas,int idNegras) {

        super("El jugador de blancas (" + idBlancas + ") y el jugador de negras (" + idNegras + ") no pueden ser el mismo.", "JUGADOR_CLON");
    }
}
