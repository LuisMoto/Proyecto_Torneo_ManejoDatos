package excepciones;

public class FechaInvalidaException
        extends TorneoAjedrezException {

    public FechaInvalidaException() {
        super("La fecha ingresada no es válida.","FECHA_INVALIDA");
    }
}
