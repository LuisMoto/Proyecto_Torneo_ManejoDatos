package excepciones;

/**
 * Excepción base para el sistema de Torneo de Ajedrez.
 * Todas las excepciones personalizadas del sistema heredan de esta clase.
 */
public class TorneoAjedrezException extends Exception {

    private final String codigoError;

    public TorneoAjedrezException(String mensaje) {
        super(mensaje);
        this.codigoError = "ERROR_GENERAL";
    }

    public TorneoAjedrezException(String mensaje, String codigoError) {
        super(mensaje);
        this.codigoError = codigoError;
    }

    public TorneoAjedrezException(String mensaje, Throwable causa) {
        super(mensaje, causa);
        this.codigoError = "ERROR_GENERAL";
    }

    public String getCodigoError() {
        return codigoError;
    }

    @Override
    public String toString() {
        return "[" + codigoError + "] " + getMessage();
    }
}
