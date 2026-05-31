package excepciones;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Inconsistencias en las fechas del torneo o partida.
 */
public class FechaInvalidaException extends TorneoAjedrezException {

    private final Date fechaTorneo;
    private final Date fechaPartida;

    public FechaInvalidaException(String mensaje) {
        super(mensaje, "FECHA_INVALIDA");
        this.fechaTorneo = null;
        this.fechaPartida = null;
    }

    //antes del inicio o después del fin del torneo
    public FechaInvalidaException(Date fechaTorneo, Date fechaPartida) {
        super("Incongruencia temporal: La fecha de la partida (" + formatearFecha(fechaPartida) + 
              ") se encuentra fuera de la ventana de duración del torneo (" + formatearFecha(fechaTorneo) + ").", 
              "FECHA_FUERA_DE_RANGO");
        this.fechaTorneo = fechaTorneo;
        this.fechaPartida = fechaPartida;
    }

    private static String formatearFecha(Date fecha) {
        if (fecha == null) return "Fecha Desconocida";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(fecha);
    }

    public Date getFechaTorneo() { return fechaTorneo; }
    public Date getFechaPartida() { return fechaPartida; }
}

