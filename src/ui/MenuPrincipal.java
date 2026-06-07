package ui;

import Entidades.Jugador;
import Entidades.Partida;
import Entidades.Torneo;

import Participantes.Gestionar_Duelos;
import Participantes.Gestionar_Historial;
import Participantes.Gestionar_Jugadores;

import dao.JugadorDAO;
import dao.PartidaDAO;
import dao.TorneoDAO;

import excepciones.DatosNulosException;
import excepciones.JugadorClonException;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MenuPrincipal {

    private static final Scanner lectura = new Scanner(System.in);
    private static final JugadorDAO jugadorDAO = new JugadorDAO();
    private static final TorneoDAO torneoDAO = new TorneoDAO();
    private static final PartidaDAO partidaDAO = new PartidaDAO();
    private static final Gestionar_Duelos gestorDuelos = new Gestionar_Duelos();
    private static final Gestionar_Historial gestorHistorial = new Gestionar_Historial();

    /**
     * Método principal que inicia la aplicación y muestra el menú al usuario.
     * Carga las partidas pendientes en la cola al iniciar el programa.
     */
    public static void main(String[] args) {
        cargarPartidasPendientesEnCola();

        char opcion;

        do {
            mostrarMenu();
            opcion = eleccion();
            procesarOpcion(opcion);

        } while (opcion != 'J');

        System.out.println("\n¡Hasta la próxima, vaquero!\n");
        lectura.close();
    }

    /**
     * Muestra el menú principal al usuario.
     */
    private static void mostrarMenu() {
        System.out.println("\n┌─────────────────────────────────────────┐");
        System.out.println("│            TORNEO DE AJEDREZ            │");
        System.out.println("│         - SISTEMA DE RANKING -          │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.println("│A. Registrar jugador                     │");
        System.out.println("│B. Mostrar jugadores                     │");
        System.out.println("│C. Buscar jugador                        │");
        System.out.println("│D. Registrar torneo                      │");
        System.out.println("│E. Mostrar torneos                       │");
        System.out.println("│F. Programar partida                     │");
        System.out.println("│G. Gestionar duelo                       │");
        System.out.println("│H. Historial por jugador                 │");
        System.out.println("│I. Mostrar ranking                       │");
        System.out.println("│J. Salir                                 │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.print("»» Seleccione una opción: ");
    }

    /**
     * Lee y valida la opción seleccionada por el usuario.
     */
    private static char eleccion() {
        while (true) {
            String entrada = lectura.nextLine().trim();
            if (entrada.isEmpty()) {
                System.out.print("Opción no válida. Por favor, seleccione una opción válida: ");
                continue;
            }
            char opcion = Character.toUpperCase(entrada.charAt(0));
            if (opcion >= 'A' && opcion <= 'J') {
                return opcion;
            }
            System.out.print("Opción no válida. Por favor, seleccione una opción válida: ");
        }
    }

    /**
     * Lee un entero validando errores.
     */
    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(lectura.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido, intentalo otra vez:");
            }
        }
    }

    /**
     * Lee una opción dentro de un rango.
     */
    private static int leerOpcion(String mensaje, int minimo, int maximo) {
        while (true) {
            int opcion = leerEntero(mensaje);
            if (opcion >= minimo && opcion <= maximo) {
                return opcion;
            }
            System.out.println("Opción inválida.");
        }
    }

    /**
     * Lee una fecha válida.
     */
    private static Date leerFecha(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String texto = lectura.nextLine().trim();
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                formato.setLenient(false);
                return formato.parse(texto);
            } catch (Exception e) {
                System.out.println("Fecha inválida. Utilice el formato dd/MM/yyyy.\n");
            }
        }
    }

    /**
     * Lee una duración válida (mayor que 0).
     */
    private static int leerDuracionValida() {
        while (true) {
            int duracion = leerEntero("Duración en minutos: ");
            if (duracion > 0) {
                return duracion;
            }
            System.out.println("La duración debe ser mayor que cero. Intente nuevamente.\n");
        }
    }

    /**
     * Procesa la opción seleccionada y ejecuta la acción correspondiente.
     */
    private static void procesarOpcion(char opcion) {
        switch (opcion) {
            case 'A': registrarJugador(); break;
            case 'B': mostrarJugadores(); break;
            case 'C': buscarJugador(); break;
            case 'D': registrarTorneo(); break;
            case 'E': mostrarTorneos(); break;
            case 'F': programarPartida(); break;
            case 'G': gestionarDuelo(); break;
            case 'H': historialJugador(); break;
            case 'I': mostrarRanking(); break;
            case 'J': System.out.println("\nSaliendo..."); break;
        }
    }

    // ==================== MÉTODOS DE VERIFICACIÓN ====================

    /**
     * Verifica si hay al menos 2 jugadores registrados.
     */
    private static boolean haySuficientesJugadores() {
        try {
            List<Jugador> jugadores = jugadorDAO.obtenerTodos();
            if (jugadores.size() < 2) {
                System.out.println("\nNo hay suficientes jugadores registrados.");
                System.out.println("   Se necesitan al menos 2 jugadores para programar una partida.\n");
                return false;
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Error al verificar jugadores: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica si hay al menos un torneo registrado.
     */
    private static boolean hayTorneosRegistrados() {
        try {
            List<Torneo> torneos = torneoDAO.obtenerTodos();
            if (torneos.isEmpty()) {
                System.out.println("\nNo hay torneos registrados.");
                System.out.println("   Primero registre un torneo con la opción D.\n");
                return false;
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Error al verificar torneos: " + e.getMessage());
            return false;
        }
    }

    // ==================== MÉTODOS DE GESTIÓN DE COLA ====================

    /**
     * Carga las partidas pendientes desde la BD a la cola al iniciar el programa.
     */
    private static void cargarPartidasPendientesEnCola() {
        try {
            List<Partida> pendientes = partidaDAO.obtenerPendientes();
            for (Partida p : pendientes) {
                gestorDuelos.agregarPartida(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar partidas pendientes: " + e.getMessage());
        }
    }

    /**
     * Sincroniza la cola en memoria con las partidas pendientes de la BD.
     */
    private static void sincronizarColaConBD() {
        try {
            List<Partida> pendientesBD = partidaDAO.obtenerPendientes();
            while (!gestorDuelos.estaVacia()) {
                gestorDuelos.ejecutarSiguientePartida();
            }
            for (Partida p : pendientesBD) {
                gestorDuelos.agregarPartida(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al sincronizar cola: " + e.getMessage());
        }
    }

    /**
     * Elimina una partida específica de la cola si existe en ella.
     */
    private static void removerPartidaDeCola(int idPartida) {
        Gestionar_Duelos tempCola = new Gestionar_Duelos();
        while (!gestorDuelos.estaVacia()) {
            Partida p = gestorDuelos.ejecutarSiguientePartida();
            if (p.getIdPartida() != idPartida) {
                tempCola.agregarPartida(p);
            }
        }
        while (!tempCola.estaVacia()) {
            gestorDuelos.agregarPartida(tempCola.ejecutarSiguientePartida());
        }
    }

    /**
     * Muestra el estado de la cola.
     */
    private static void mostrarEstadoColaEnDuelo() {
        

        if (gestorDuelos.estaVacia()) {
            System.out.println("\nCOLA DE PARTIDAS VACIA");
        } else {                    
            System.out.println("\n ««««««««««««  COLA DE PARTIDAS: " + gestorDuelos.cantidadPartidas() + " »»»»»»»»»»»»");
            Partida siguiente = gestorDuelos.verSiguientePartida();
            if (siguiente != null) {
                System.out.println("    Siguiente partida: " + siguiente.getJugadorBlancas().getNombreJugador()  + " vs " + siguiente.getJugadorNegras().getNombreJugador());
            }
        }

    }

    /**
     * Muestra todas las partidas pendientes.
     */
    private static void mostrarPartidasPendientes(List<Partida> partidas) {
        if (partidas.isEmpty()) {
            System.out.println("\nNo hay partidas pendientes.\n");
            return;
        }
        
        System.out.println("\n├────────────────────────────── Partidas Pendientes ──────────────────────────────┤");
        
        int contador = 1;
        for (Partida p : partidas) {
            System.out.println("\n[" + contador + "]  ID: " + p.getIdPartida() + " │ Torneo: " + p.getTorneo().getNombreTorneo());
            System.out.println("     ╬ " + p.getJugadorBlancas().getNombreJugador() + " (BLANCAS) vs " + p.getJugadorNegras().getNombreJugador() + " (NEGRAS) ╬");
            System.out.println("\n──────────────────────────────────────────────────────────────────────────────────");
            contador++;
        }
        
    }

    // ==================== OPCIONES DEL MENÚ ====================

    /**
     * Opción A: Registrar un nuevo jugador.
     */
    private static void registrarJugador() {
        System.out.println("\n----------------------- REGISTRAR JUGADOR -----------------------");
        try {
            System.out.print("Nombre del jugador: ");
            String nombre = lectura.nextLine().trim();
            if (nombre.isEmpty()) {
                throw new DatosNulosException("nombre");
            }
            if (nombre.length() > 100) {
                System.out.println("El nombre no puede exceder 100 caracteres.");
                return;
            }
            Jugador existente = jugadorDAO.buscarPorNombre(nombre);
            if (existente != null) {
                System.out.println("No es posible hacer el registro, ya existe un jugador con ese nombre.\n");
                return;
            }
            Jugador jugador = new Jugador(0, nombre, 0.0);
            jugadorDAO.insertar(jugador);
            System.out.println("\n├───────────── Jugador registrado correctamente ─────────────┤");
            System.out.println(jugador);
        } catch (DatosNulosException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error BD: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Opción B: Mostrar todos los jugadores usando un arreglo.
     */
    private static void mostrarJugadores() {
        System.out.println("\n---------------------- LISTA DE JUGADORES ----------------------");
        try {
            List<Jugador> jugadores = jugadorDAO.obtenerTodos();
            if (jugadores.isEmpty()) {
                System.out.println("No hay jugadores registrados.\n");
                return;
            }
            Gestionar_Jugadores gestor = new Gestionar_Jugadores(jugadores);
            gestor.mostrarJugadores();
        } catch (SQLException e) {
            System.out.println("Error BD: " + e.getMessage());
        }
    }

    /**
     * Opción C: Buscar jugador por ID o nombre.
     */
    private static void buscarJugador() {
        System.out.println("\n------------------------- BUSCAR JUGADOR -------------------------");
        try {
            System.out.println("1. Buscar por ID");
            System.out.println("2. Buscar por nombre");
            int opcion = leerOpcion("»» Seleccione una opción: ", 1, 2);
            Jugador jugador = null;
            if (opcion == 1) {
                int id = leerEntero("\nID del jugador: ");
                jugador = jugadorDAO.buscarPorId(id);
            } else if (opcion == 2) {
                System.out.print("\nNombre del jugador: ");
                String nombre = lectura.nextLine();
                jugador = jugadorDAO.buscarPorNombre(nombre);
            }
            if (jugador == null) {
                System.out.println("\nJugador no encontrado.");
            } else {
                System.out.println("\n├──────────────────── Jugador encontrado ────────────────────┤");
                System.out.println(jugador);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Opción D: Registrar torneo.
     * No permite registrar torneo si no hay al menos 2 jugadores.
     */
    private static void registrarTorneo() {
        System.out.println("\n-------------------------- REGISTRAR TORNEO --------------------------");
        
        // Verificar que hay al menos 2 jugadores
        if (!haySuficientesJugadores()) {
            return;
        }
        
        try {
            System.out.print("Nombre del torneo: ");
            String nombre = lectura.nextLine().trim();
            if (nombre.isEmpty()) {
                throw new DatosNulosException("nombre del torneo");
            }
            if (nombre.length() > 100) {
                System.out.println("El nombre del torneo es demasiado largo.");
                return;
            }
            System.out.print("Tipo: ");
            String tipo = lectura.nextLine().trim();
            if (tipo.isEmpty()) {
                throw new DatosNulosException("tipo");
            }
            Date fecha = leerFecha("Fecha (dd/MM/yyyy): ");
            int duracion = leerDuracionValida();  
            
            Torneo torneo = new Torneo(0, nombre, tipo, fecha, duracion);
            torneoDAO.insertar(torneo);
            System.out.println("\n├──────────────────── Torneo registrado correctamente ────────────────────┤");
            System.out.println(torneo);
        } catch (DatosNulosException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Opción E: Mostrar torneos.
     */
    private static void mostrarTorneos() {
        System.out.println("\n----------------------------------- TORNEOS -----------------------------------");
        try {
            List<Torneo> torneos = torneoDAO.obtenerTodos();
            if (torneos.isEmpty()) {
                System.out.println("No hay torneos registrados.");
                return;
            }
            for (Torneo torneo : torneos) {
                System.out.println(torneo);
            }
        } catch (SQLException e) {
            System.out.println("Error BD: " + e.getMessage());
        }
    }

    /**
     * Opción F: Programar una partida.
     * Verifica que haya torneos y al menos 2 jugadores.
     */
    private static void programarPartida() {
        System.out.println("\n---------------------- PROGRAMAR PARTIDA ----------------------");
        
        // Verificar que hay torneos
        if (!hayTorneosRegistrados()) {
            return;
        }
        
        // Verificar que hay al menos 2 jugadores
        if (!haySuficientesJugadores()) {
            return;
        }
        
        try {
            List<Torneo> torneos = torneoDAO.obtenerTodos();
            System.out.println("\n──────────────────── TORNEOS DISPONIBLES ────────────────────");
            for (Torneo torneo : torneos) {
                System.out.println("¤ ID: " + torneo.getIdTorneo() + "  |  " + torneo.getNombreTorneo());
            }
            System.out.println("─────────────────────────────────────────────────────────────");
            
            int idTorneo = leerEntero("\nID del torneo: ");
            Torneo torneo = torneoDAO.buscarPorId(idTorneo);
            if (torneo == null) {
                System.out.println("Torneo no encontrado.");
                return;
            }
            
            mostrarJugadores();
            
            int idBlancas = leerEntero("\nID del jugador con BLANCAS: ");
            int idNegras = leerEntero("ID del jugador con NEGRAS: ");
            
            if (idBlancas == idNegras) {
                throw new JugadorClonException(idBlancas, idNegras);
            }
            
            Jugador blancas = jugadorDAO.buscarPorId(idBlancas);
            Jugador negras = jugadorDAO.buscarPorId(idNegras);
            
            if (blancas == null) {
                System.out.println("Jugador blancas no encontrado.");
                return;
            }
            if (negras == null) {
                System.out.println("Jugador negras no encontrado.");
                return;
            }
            
            Partida partida = new Partida();
            partida.setFecha(new Date());
            partida.setResultado(null);
            partida.setPuntajeBlancas(0);
            partida.setPuntajeNegras(0);
            partida.setEstado("PENDIENTE");
            partida.setTorneo(torneo);
            partida.setJugadorBlancas(blancas);
            partida.setJugadorNegras(negras);
            
            partidaDAO.insertar(partida);
            gestorDuelos.agregarPartida(partida);
            System.out.println("\n├────────────────────── Partida Programada ──────────────────────┤");
            System.out.println(" Torneo: " + torneo.getNombreTorneo()); 
            System.out.println("╬  " + blancas.getNombreJugador() + " (BLANCAS) vs " + negras.getNombreJugador() + " (NEGRAS) ╬");

            
        } catch (JugadorClonException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Opción G: Gestionar duelos.
     * Ofrece dos modalidades y muestra el estado de la cola.
     */
    private static void gestionarDuelo() {
        System.out.println("\n------------------------- GESTIONAR DUELOS -------------------------");
        
        // Mostrar estado de la cola al entrar
        mostrarEstadoColaEnDuelo();
        
        try {
            List<Partida> pendientesBD = partidaDAO.obtenerPendientes();
            if (pendientesBD.isEmpty()) {
                System.out.println("\nNo hay partidas pendientes en el sistema.");
                System.out.println(" Primero programe partidas con la opción F.\n");
                return;
            }
            
            System.out.println("\n=========== ¿CÓMO GESTIONAR EL DUELO? ===========");
            System.out.println(" 1. Ejecutar siguiente partida (orden de la cola)");
            System.out.println(" 2. Eligir una partida en específico (por ID)");

            
            int modo = leerOpcion("»» Seleccione una opción: ", 1, 2);
            Partida partida = null;
            
            if (modo == 1) {
                // Modo FIFO: siguiente partida de la cola
                sincronizarColaConBD();
                if (gestorDuelos.estaVacia()) {
                    System.out.println("\nLa cola está vacía. Intentando cargar partidas desde BD...");
                    cargarPartidasPendientesEnCola();
                    if (gestorDuelos.estaVacia()) {
                        System.out.println("No hay partidas pendientes en la cola.\n");
                        return;
                    }
                }
                partida = gestorDuelos.ejecutarSiguientePartida();
                System.out.println("\n Ejecuntando siguiente partida...");

                
            } else {
                // Modo manual: mostrar todas las partidas pendientes              
                mostrarPartidasPendientes(pendientesBD);
                
                int idPartida = leerEntero("\nIngrese el ID de la partida a ejecutar: ");
                partida = partidaDAO.buscarPorId(idPartida);
                
                if (partida == null) {
                    System.out.println("Partida no encontrada.");
                    return;
                }
                if (!"PENDIENTE".equals(partida.getEstado())) {
                    System.out.println("Esta partida ya fue finalizada.");
                    return;
                }
                removerPartidaDeCola(idPartida);
                

                System.out.println("\n Ejecutando partida seleccionada...");

            }
            
            // Mostrar información de la partida
            System.out.println("\n─────────────────────────────────────────────────────────────────────────────────");
            System.out.println("ID Partida: " + partida.getIdPartida());
            System.out.println("Torneo: " + partida.getTorneo().getNombreTorneo());
            System.out.println("╬  " + partida.getJugadorBlancas().getNombreJugador() + " (BLANCAS)  vs  " + partida.getJugadorNegras().getNombreJugador() + " (NEGRAS)  ╬");
            System.out.println("\n─────────────────────────────────────────────────────────────────────────────────");

            
            // Solicitar resultado
            System.out.println("\n===== REGISTRAR RESULTADO =====");
            System.out.println("1. Ganan BLANCAS (1-0)");
            System.out.println("2. EMPATE (1/2-1/2)");
            System.out.println("3. Ganan NEGRAS (0-1)");

            
            int opcion = leerOpcion("»» Seleccione el resultado: ", 1, 3);
            
            String resultado;
            double puntosBlancas;
            double puntosNegras;
            
            switch (opcion) {
                case 1:
                    resultado = "1-0";
                    puntosBlancas = 1.0;
                    puntosNegras = 0.0;
                    break;
                case 2:
                    resultado = "1/2-1/2";
                    puntosBlancas = 0.5;
                    puntosNegras = 0.5;
                    break;
                case 3:
                    resultado = "0-1";
                    puntosBlancas = 0.0;
                    puntosNegras = 1.0;
                    break;
                default:
                    System.out.println("Opción inválida.");
                    return;
            }
            
            // Actualizar BD
            partidaDAO.actualizarResultado(partida.getIdPartida(), resultado, puntosBlancas, puntosNegras);
            jugadorDAO.sumarPuntaje(partida.getJugadorBlancas().getIdJugador(), puntosBlancas);
            jugadorDAO.sumarPuntaje(partida.getJugadorNegras().getIdJugador(), puntosNegras);
            
            // Actualizar objeto
            partida.setResultado(resultado);
            partida.setPuntajeBlancas(puntosBlancas);
            partida.setPuntajeNegras(puntosNegras);
            partida.setEstado("FINALIZADA");
            
            // Registrar en historial
            gestorHistorial.agregarPartida(partida);
            
            // Confirmación
            System.out.println("\n├─────────── Partida Finalizada ───────────┤");
            System.out.println("Resultado: " +  resultado);
            System.out.println("BLANCAS " + puntosBlancas + "  -  " + puntosNegras + " NEGRAS");
            System.out.println("Puntos: BLANCAS 0.0  -  1.0 NEGRAS");
            System.out.println("────────────────────────────────────────────");

            
            // Mostrar estado actualizado de la cola
            mostrarEstadoColaEnDuelo();
            
        } catch (Exception e) {
            System.out.println("Error al gestionar el duelo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Opción H: Historial de un jugador usando PILA (LIFO).
     */
    private static void historialJugador() {
        System.out.println("\n------------------------- HISTORIAL DEL JUGADOR -------------------------");
        
        try {
            int idJugador = leerEntero("ID del jugador: ");
            Jugador jugador = jugadorDAO.buscarPorId(idJugador);
            if (jugador == null) {
                System.out.println("No existe un jugador con ese ID.\n");
                return;
            }
            
            System.out.println("\n»»»»» Historial de: " + jugador.getNombreJugador());
            
            List<Partida> partidasBD = partidaDAO.obtenerPartidasJugador(idJugador);
            if (partidasBD.isEmpty()) {
                System.out.println("\nEste jugador no tiene partidas registradas.\n");
                return;
            }
            
            // Cargar en PILA
            Gestionar_Historial pilaHistorial = new Gestionar_Historial();
            for (Partida p : partidasBD) {
                pilaHistorial.agregarPartida(p);
            }
            
            pilaHistorial.mostrarUltimasCinco();
            
            System.out.println("\n¤ Total de partidas registradas: " + pilaHistorial.cantidadPartidas());

            
        } catch (Exception e) {
            System.out.println("Error al mostrar el historial: " + e.getMessage());
        }
    }

    /**
     * Opción I: Mostrar ranking general.
     */
    private static void mostrarRanking() {
        System.out.println("\n------------------------- RANKING GENERAL -------------------------");
        
        try {
            List<Jugador> ranking = jugadorDAO.obtenerRanking();
            if (ranking.isEmpty()) {
                System.out.println("\nNo hay jugadores registrados.\n");
                return;
            }

            int posicion = 1;
            for (Jugador jugador : ranking) {
                System.out.println(posicion +". "+ jugador.getNombreJugador() + " - " +jugador.getPuntajeAcumulado());
                posicion++;
            }

            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
