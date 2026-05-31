package validaciones;

import excepciones.*;
import Entidades.Torneo;
import Entidades.Partida;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ValidadorArena {

    private List<String> bitacoraErrores;
    private boolean registroValido;

    public ValidadorArena() {
        this.bitacoraErrores = new ArrayList<>();
        this.registroValido = true;
    }
    public boolean limpiarYValidarRegistro(Torneo torneo, Partida partida) {
        // Asumimos que el registro es válido hasta que se demuestre lo contrario
        this.registroValido = true; 

        // Evaluamos cada filtro. Si uno falla, atrapamos la excepción y la guardamos en la bitácora,
        // pero NO detenemos el programa, permitiendo evaluar los demás errores del mismo registro.
        
        try {
            // Pasamos un arreglo simulando datos críticos que no pueden ser nulos
            validarDatosNulosYFaltantes(new Object[]{torneo.getNombre_torneo(), partida.getResultado()});
        } catch (DatosNulosException e) {
            registrarError(e);
        }

        try {
            validarFechas(torneo.getFecha_inicio(), torneo.getDuracion_minutos(), partida.getFecha());
        } catch (FechaInvalidaException e) {
            registrarError(e);
        }

        try {
            validarJugadorClonado(partida.getJugador_blancas().getId_jugador(), partida.getJugador_negras().getId_jugador());
        } catch (JugadorClonException e) {
            registrarError(e);
        }

        return this.registroValido;
    }

    // Métodos privados

    private void validarDatosNulosYFaltantes(Object[] datosObtenidos) throws DatosNulosException {
        for (Object dato : datosObtenidos) {
            if (dato == null || (dato instanceof String && ((String) dato).trim().isEmpty())) {
                throw new DatosNulosException("Un campo obligatorio");
            }
        }
    }

    private void validarFechas(Date fechaTorneo, int duracion, Date fechaPartida) throws FechaInvalidaException {
        if (fechaTorneo == null || fechaPartida == null) return; // Evita NullPointerException

        // Calculamos la fecha de fin del torneo sumando la duración (en minutos)
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaTorneo);
        cal.add(Calendar.MINUTE, duracion);
        Date fechaFinTorneo = cal.getTime();

        // Si la partida es estrictamente ANTES del torneo, o estrictamente DESPUÉS del fin
        if (fechaPartida.before(fechaTorneo) || fechaPartida.after(fechaFinTorneo)) {
            throw new FechaInvalidaException(fechaTorneo, fechaPartida);
        }
    }

    private void validarJugadorClonado(int idBlancas, int idNegras) throws JugadorClonException {
        if (idBlancas == idNegras) {
            throw new JugadorClonException(idBlancas, idNegras);
        }
    }

    // Bitacora

    private void registrarError(TorneoAjedrezException e) {
        this.bitacoraErrores.add(e.toString());
        this.registroValido = false; // El registro ya está corrupto
    }

    public List<String> getBitacoraErrores() {
        return bitacoraErrores;
    }

    public void limpiarBitacora() {
        this.bitacoraErrores.clear();
        this.registroValido = true;
    }
}
