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

    /**
     * Valida un registro completo de torneo y partida.
     * Si encuentra errores, los almacena en la bitácora.
     *
     * @param torneo Torneo a validar.
     * @param partida Partida a validar.
     * @return true si todo es válido, false en caso contrario.
     */
    public boolean limpiarYValidarRegistro(Torneo torneo, Partida partida) {

        limpiarBitacora();

        if (torneo == null || partida == null) {
            return false;
        }

        // Evaluamos cada filtro. Si uno falla, atrapamos la excepción y la guardamos en la bitácora,
        // pero NO detenemos el programa, permitiendo evaluar los demás errores del mismo registro.
        
        try {
            // Pasamos un arreglo simulando datos críticos que no pueden ser nulos
            validarDatosNulosYFaltantes(new Object[]{ torneo.getNombreTorneo(), partida.getResultado()});
        } catch (DatosNulosException e) {
            registrarError(e);
        }

        try {
            validarFechas(torneo.getFechaInicio(), torneo.getDuracionMinutos(), partida.getFecha());
        } catch (FechaInvalidaException e) {
            registrarError(e);
        }

        try {

            if (partida.getJugadorBlancas() == null || partida.getJugadorNegras() == null) {
                throw new DatosNulosException("Jugadores de la partida");
            }

            validarJugadorClonado(partida.getJugadorBlancas().getIdJugador(), partida.getJugadorNegras().getIdJugador());
        } catch (DatosNulosException | JugadorClonException e) {
            registrarError(e);
        }

        return registroValido;
    }

    /**
     * Verifica que los datos obligatorios no sean nulos o vacíos.
     */
    private void validarDatosNulosYFaltantes(Object[] datosObtenidos) throws DatosNulosException {
        for (Object dato : datosObtenidos) {
            if (dato == null) {
                throw new DatosNulosException("Campo obligatorio");
            }

            if (dato instanceof String &&
                ((String) dato).trim().isEmpty()) {
                throw new DatosNulosException("Campo obligatorio");
            }
        }
    }

    /**
     * Verifica que la fecha de la partida ocurra dentro
     * del intervalo válido del torneo.
     */
    private void validarFechas(Date fechaTorneo, int duracion, Date fechaPartida)
            throws FechaInvalidaException {

        if (fechaTorneo == null || fechaPartida == null) {
            throw new FechaInvalidaException("Las fechas del torneo y la partida son obligatorias.");
        }

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

    /**
     * Verifica que los jugadores de blancas y negras
     * no sean la misma persona.
     */
    private void validarJugadorClonado(int idBlancas, int idNegras) throws JugadorClonException {
        if (idBlancas == idNegras) {
            throw new JugadorClonException(idBlancas, idNegras);
        }
    }

    /**
     * Guarda el error en la bitácora y marca
     * el registro como inválido.
     */
    private void registrarError(TorneoAjedrezException e) {
        bitacoraErrores.add(e.toString());
        registroValido = false;  // El registro ya está corrupto
    }

    /**
     * Obtiene la lista de errores encontrados.
     */
    public List<String> getBitacoraErrores() {
        return bitacoraErrores;
    }

    /**
     * Limpia la bitácora para una nueva validación.
     */
    public void limpiarBitacora() {
        bitacoraErrores.clear();
        registroValido = true;
    }
}
