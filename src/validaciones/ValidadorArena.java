package validaciones;

import excepciones.*;
import entidades.*; // Aquí tienen que estar las clases Torneo, Partida, Nivel
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
//Lo que estaba pensando es que para que el programa no se detenga en cuanto encuentre un error, haga una bitacora 
    public boolean limpiarYValidarRegistro(Torneo torneo, Partida partida, Nivel nivel) {
        this.registroValido = true; 
        
        try {
            validarDatosNulosYFaltantes(new Object[]{torneo.getNombre_torneo(), partida.getResultado(), nivel.getNombre_nivel()});
        } catch (DatosNulosException e) {
            registrarError(e);
        }

        try {
            validarFechas(torneo.getFecha_inicio(), torneo.getDuracion(), partida.getFecha());
        } catch (FechaInvalidaException e) {
            registrarError(e);
        }

        try {
            validarNivelRoto(nivel.getPuntaje_minimo(), nivel.getPuntaje_maximo());
        } catch (RegistroInvalidoException e) {
            registrarError(e);
        }

        try {
            validarJugadorClonado(partida.getJugador_blancas().getId_jugador(), partida.getJugador_negras().getId_jugador());
        } catch (JugadorClonException e) {
            registrarError(e);
        }

        return this.registroValido;
    }


    private void validarDatosNulosYFaltantes(Object[] datosObtenidos) throws DatosNulosException {
        for (Object dato : datosObtenidos) {
            if (dato == null || (dato instanceof String && ((String) dato).trim().isEmpty())) {
                throw new DatosNulosException("Un campo obligatorio");
            }
        }
    }

    private void validarFechas(Date fechaTorneo, int duracion, Date fechaPartida) throws FechaInvalidaException {
        if (fechaTorneo == null || fechaPartida == null) return; // Evita NullPointerException

        //Tenemos un problema aquí, los torneos duran horas no días. Necesitamos saber que vamos a hacer (Yo digo que lo cambiemos a horas o minutos)
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaTorneo);
        cal.add(Calendar.DAY_OF_MONTH, duracion);
        Date fechaFinTorneo = cal.getTime();

        if (fechaPartida.before(fechaTorneo) || fechaPartida.after(fechaFinTorneo)) {
            throw new FechaInvalidaException(fechaTorneo, fechaPartida);
        }
    }
//Este hace referencia al elo, entonces también tenemos que decidir si lo vamos a tomar en cuenta o no
    private void validarNivelRoto(int min, int max) throws RegistroInvalidoException {
        if (min >= max) {
            throw new RegistroInvalidoException("Nivel", 
                "El puntaje mínimo (" + min + ") no puede ser mayor o igual al máximo (" + max + ").");
        }
    }

    private void validarJugadorClonado(int idBlancas, int idNegras) throws JugadorClonException {
        if (idBlancas == idNegras) {
            throw new JugadorClonException(idBlancas, idNegras);
        }
    }

//BItacora

    private void registrarError(TorneoAjedrezException e) {
        this.bitacoraErrores.add(e.toString());
        this.registroValido = false; //El registro ya está corrupto
    }

    public List<String> getBitacoraErrores() {
        return bitacoraErrores;
    }

    public void limpiarBitacora() {
        this.bitacoraErrores.clear();
        this.registroValido = true;
    }
}