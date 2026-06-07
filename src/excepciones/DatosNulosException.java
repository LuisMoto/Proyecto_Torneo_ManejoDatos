package excepciones;

/**
 * Excepción que se lanza cuando un campo obligatorio
 * no recibe un valor válido o se encuentra vacío.
 */
public class DatosNulosException extends TorneoAjedrezException {
    private final String campoProblematico;

    /**
     * Construye la excepción indicando el campo
     * que presentó el problema.
     *
     * @param campo Nombre del campo vacío o nulo.
     */
    public DatosNulosException(String campo) {
        super("El campo obligatorio '" + campo + "' no puede ser nulo o vacío.", "DATO_NULO");
        this.campoProblematico = campo;
    }

    /**
     * Obtiene el nombre del campo que ocasionó la excepción.
     *
     * @return Campo problemático.
     */
    public String getCampoProblematico() {
        return campoProblematico;
    }
}
