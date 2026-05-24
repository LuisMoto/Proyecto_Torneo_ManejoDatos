package excepciones;

/**
 * Excepción lanzada cuando un registro completo (Torneo, Partida o Nivel)
 * falla la validación integral antes de persistirse.
 */
public class RegistroInvalidoException extends TorneoAjedrezException {

    private final String tipoRegistro;

    public RegistroInvalidoException(String tipoRegistro, String detalle) {
        super("Registro de tipo '" + tipoRegistro + "' inválido: " + detalle,
              "REGISTRO_INVALIDO");
        this.tipoRegistro = tipoRegistro;
    }

    public String getTipoRegistro() { return tipoRegistro; }
}
