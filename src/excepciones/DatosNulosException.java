package excepciones;

/**
 * Cuando los datos son nulos.
 */
public class DatosNulosException extends TorneoAjedrezException {

    private final String campoProblematico;

    public DatosNulosException(String campo) {
        super("El campo obligatorio '" + campo + "' no puede ser nulo o vacío.", "DATO_NULO");
        this.campoProblematico = campo;
    }

    public String getCampoProblematico() {
        return campoProblematico;
    }
}
