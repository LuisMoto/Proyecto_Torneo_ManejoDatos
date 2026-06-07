package excepciones;

/**
 * Excepción base para el sistema de Torneo de Ajedrez.
 * Todas las excepciones personalizadas del sistema heredan de esta clase.
 */
public class TorneoAjedrezException extends Exception {
    private final String codigoError;

    /**
     * Crea una excepción con un mensaje y un código
     * de error general.
     *
     * @param mensaje Descripción del error ocurrido.
     */
    public TorneoAjedrezException(String mensaje) {
        super(mensaje);
        this.codigoError = "ERROR_GENERAL";
    }

    /**
     * Crea una excepción con un mensaje y un código
     * de error personalizado.
     *
     * @param mensaje Descripción del error.
     * @param codigoError Código identificador del error.
     */
    public TorneoAjedrezException(String mensaje, String codigoError) {
        super(mensaje);
        this.codigoError = codigoError;
    }

    /**
     * Crea una excepción con un mensaje y una causa
     * original que provocó el error.
     *
     * @param mensaje Descripción del error.
     * @param causa Excepción que originó el problema.
     */
    public TorneoAjedrezException(String mensaje, Throwable causa) {
        super(mensaje, causa);
        this.codigoError = "ERROR_GENERAL";
    }

    /**
     * Obtiene el código asociado al error.
     *
     * @return Código identificador del error.
     */
    public String getCodigoError() {
        return codigoError;
    }

    /**
     * Devuelve una representación textual de la excepción.
     *
     * @return Código de error y mensaje descriptivo.
     */
    @Override
    public String toString() {
        return "[" + codigoError + "] " + getMessage();
    }
}
