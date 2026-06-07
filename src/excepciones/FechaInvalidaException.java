package excepciones;

/**
 * Excepción que se lanza cuando una fecha ingresada
 * no cumple con el formato esperado o no es válida.
 */
public class FechaInvalidaException extends TorneoAjedrezException {

     /**
     * Construye una excepción para indicar
     * que la fecha proporcionada es inválida.
     */        
    public FechaInvalidaException() {
        super("La fecha ingresada no es válida.","FECHA_INVALIDA");
    }
}
