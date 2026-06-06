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
     * Permite seleccionar opciones para gestionar jugadores, duelos,
     * historial de partidas, ranking y actualización de puntajes.
     *
     */
    public static void main(String[] args) {
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
     * Método para mostrar el menú principal al usuario.
     *
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
        System.out.print( "»» Seleccione una opción: ");
    }


    /**
     * Lee y valida la opción seleccionada por el usuario.
     * Asegura que la entrada sea una letra válida (A-J)
     * y maneja casos de entradas no válidas.
     *
     * @return char La opción seleccionada por el usuario, convertida a mayúscula.
     */
    private static char eleccion() {

        while (true) {

            String entrada = lectura.nextLine().trim();

            if (entrada.isEmpty()) {

                System.out.print("Opción no válida. Por favor, seleccione una opción válida. ");
                continue;
            }

            char opcion = Character.toUpperCase(entrada.charAt(0));

            if (opcion >= 'A' && opcion <= 'J') {
                return opcion;
            }

            System.out.print("Opción no válida. Por favor, seleccione una opción válida. ");
        }
    }

    /**
     * Lee un entero validando errores
     */
    private static int leerEntero(String mensaje) {

        while (true) {

            try {

                System.out.print(mensaje);
                return Integer.parseInt(lectura.nextLine().trim());

            } catch (NumberFormatException e) {

                System.out.println("Debe ingresar un número válido, intentalo otra vez.");
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
     * Procesa la opción seleccionada y ejecuta la acción correspondiente.
     * Cada caso del switch corresponde a una funcionalidad del sistema.
     *
     * @param opcion La opción seleccionada por el usuario, representada como un carácter.
     */
    private static void procesarOpcion(char opcion) {

        switch (opcion) {

            case 'A':
                registrarJugador();
                break;

            case 'B':
                mostrarJugadores();
                break;

            case 'C':
                buscarJugador();
                break;

            case 'D':
                registrarTorneo();
                break;

            case 'E':
                mostrarTorneos();
                break;

            case 'F':
                programarPartida();
                break;

            case 'G':
                gestionarDuelo();
                break;

            case 'H':
                historialJugador();
                break;

            case 'I':
                mostrarRanking();
                break;

            case 'J':
                System.out.println("\nSaliendo...");
                break;
        }
    }


    /**
     * Opción A : Registrar un nuevo jugador en el sistema
     * Solicita nombre al usuario, crea un nuevo jugador y lo guarda en la base de datos.
     * El ID se genera automáticamente y el puntaje es 0.
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
     * Opción B: Muestra la lista de todos los jugadores registrados.
     * Si no hay jugadores registrados, muestra un mensaje indicándolo.
     */
    private static void mostrarJugadores() {

        System.out.println("\n---------------------- LISTA DE JUGADORES ----------------------");

        try {

            List<Jugador> jugadores = jugadorDAO.obtenerTodos();
            Gestionar_Jugadores gestor = new Gestionar_Jugadores(jugadores);
            gestor.mostrarJugadores();

        } catch (SQLException e) {

            System.out.println("Error BD: " + e.getMessage());
        }
    }

    /**
     * Opción C: Busca un jugador por su ID o por su nombre y muestra sus datos.
     * Si el jugador no existe, muestra un mensaje indicándolo.
     * Si el usuario ingresa un valor inválido, se maneja la excepción.
     */
    private static void buscarJugador() {

        System.out.println(
                "\n------------------------- BUSCAR JUGADOR -------------------------");

        try {

            System.out.println("1. Buscar por ID");
            System.out.println("2. Buscar por nombre");
            int opcion = leerOpcion("»» Seleccione una opción: ",1, 2);
            Jugador jugador = null;

            if (opcion == 1) {

                int id = leerEntero("\nID del jugador: ");
                jugador = jugadorDAO.buscarPorId(id);

            } else if (opcion == 2) {

                System.out.print("\nNombre del juagdor: ");
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
     * Opcion D: Registrar torneo.
     */
    private static void registrarTorneo() {

        System.out.println("\n-------------------------- REGISTRAR TORNEO --------------------------");

        try {

            System.out.print("Nombre del torneo: ");
            String nombre =lectura.nextLine().trim();

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
            int duracion = leerEntero("Duración en minutos: ");

            if (duracion <= 0) {

                System.out.println("La duración debe ser mayor que cero.");
                return;
            }

            Torneo torneo = new Torneo(0, nombre, tipo, fecha, duracion);
            torneoDAO.insertar(torneo);
            System.out.println("\n├──────────────────── Torneo registrado correctamente ────────────────────┤");
            System.out.println(torneo);


        } catch (DatosNulosException e) {

            System.out.println("Error: " + e.getMessage());

        } catch (Exception e) {

            System.out.println("Error: "+ e.getMessage());
        }
    }

    /**
     * Opción E: Mostrar torneos.
     */
    private static void mostrarTorneos() {

        System.out.println("\n---------------------------------------- TORNEOS ----------------------------------------");

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
     * Opción F: Programar una partida entre dos jugadores.
     */
    private static void programarPartida() {

        System.out.println(
                "\n---------------------- PROGRAMAR PARTIDA ----------------------");

        try {

            List<Torneo> torneos = torneoDAO.obtenerTodos();

            if (torneos.isEmpty()) {

                System.out.println("No existen torneos registrados.");
                return;
            }

            System.out.println("\n──────────────────── Torneos Registrados ────────────────────");

            for (Torneo torneo : torneos) {

                System.out.println(torneo.getIdTorneo() + " - " + torneo.getNombreTorneo());
                System.out.println("\n─────────────────────────────────────────────────────────────");

            }

            int idTorneo = leerEntero("\nID del torneo: ");
            Torneo torneo = torneoDAO.buscarPorId(idTorneo);

            if (torneo == null) {

                System.out.println("Torneo no encontrado.");
                return;
            }

            mostrarJugadores();

            int idBlancas = leerEntero("\nID jugador blancas: ");

            int idNegras = leerEntero("ID jugador negras: ");

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

            System.out.println("\n«««««««««« Partida programada en el torneo " + torneo.getNombreTorneo() + " »»»»»»»»»»");

        } catch (JugadorClonException e) {

            System.out.println("Error: " + e.getMessage());

        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Opción G: Gestionar resultados de las partidas programadas.
     */
    private static void gestionarDuelo() {

        System.out.println(
                "\n-------------------------- GESTIONAR RESULTADOS DE PARTIDAS --------------------------");

        try {

            List<Partida> pendientes = partidaDAO.obtenerPendientes();

            if (pendientes.isEmpty()) {

                System.out.println("No existen partidas pendientes.");
                return;
            }

            System.out.println("\n├───────────────────────────── Partidas pendientes ─────────────────────────────┤");

            for (Partida partida : pendientes) {

                System.out.println("\nID de la partida: " + partida.getIdPartida());
                System.out.println("Torneo: " + partida.getTorneo().getNombreTorneo());
                System.out.println("╬  " + partida.getJugadorBlancas().getNombreJugador() + " vs " + partida.getJugadorNegras().getNombreJugador() + "  ╬");

                
            }

            int idPartida = leerEntero("\nID de la partida: ");
            Partida partida = partidaDAO.buscarPorId(idPartida);

            if (partida == null) {

                System.out.println("Partida no encontrada.");
                return;
            }

            System.out.println("\n├────────── Resultado ──────────┤");
            System.out.println("1. Ganan blancas");
            System.out.println("2. Empate");
            System.out.println("3. Ganan negras");

            int opcion = leerOpcion("»» Seleccione una opción: ", 1,3);
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

            partidaDAO.actualizarResultado(idPartida, resultado, puntosBlancas, puntosNegras);
            jugadorDAO.sumarPuntaje(partida.getJugadorBlancas().getIdJugador(), puntosBlancas);
            jugadorDAO.sumarPuntaje(partida.getJugadorNegras().getIdJugador(), puntosNegras);
            
            partida.setResultado(resultado);
            partida.setPuntajeBlancas(puntosBlancas);
            partida.setPuntajeNegras(puntosNegras);
            partida.setEstado("FINALIZADA");

            gestorHistorial.agregarPartida(partida);

            System.out.println("\n«««««««« Partida finalizada »»»»»»»»");

        }

        catch (Exception e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Opción H: Historial de un jugador.
     */
     private static void historialJugador() {

         System.out.println("\n---------------------------------- HISTORIAL ----------------------------------");

         try {

             int idJugador = leerEntero("ID del jugador: ");
             List<Partida> partidas = partidaDAO.obtenerPartidasJugador(idJugador);

             if (partidas.isEmpty()) {

                 System.out.println("No existen partidas registradas.");
                 return;
             }

             int limite = Math.min(5, partidas.size());

             for (int i = 0; i < limite; i++) {
                Partida partida = partidas.get(i);
                System.out.println("\n───────────────────────────────────────────────────");
                System.out.println("Torneo: " + partida.getTorneo().getNombreTorneo());
                System.out.println("╬  "+ partida.getJugadorBlancas().getNombreJugador() + " vs " + partida.getJugadorNegras().getNombreJugador() + "  ╬");
                System.out.println("Resultado: " + partida.getResultado());
                System.out.println("Estado: " + partida.getEstado());
                System.out.println("Fecha: " + partida.getFecha());
             }

         } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
         }
     }

    /**
     * Opción I. Mostrar el Ranking general.
     */
    private static void mostrarRanking() {

        System.out.println(
                "\n------------------ RANKING ------------------");

        try {

            List<Jugador> ranking = jugadorDAO.obtenerRanking();

            if (ranking.isEmpty()) {

                System.out.println("No hay jugadores registrados.");
                return;
            }

            int posicion = 1;

            for (Jugador jugador : ranking) {

                System.out.println(posicion + ". " + jugador.getNombreJugador() + " - " + jugador.getPuntajeAcumulado());
                posicion++;
            }

        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());
        }
    }
}
