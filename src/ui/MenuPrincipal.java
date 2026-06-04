package ui;

import excepciones.DatosNulosException;

import Entidades.Jugador;
import Participantes.Gestionar_Jugadores;
import dao.JugadorDAO;
import Participantes.Gestionar_Duelos;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Clase principal que contiene la interfaz de consola para el sistema de raking
 * del torneo de ajedrez. Permite a los usuarios interactuar con el sistema a través de un menú
 * con opciones para registrar jugadores, mostrarlos, buscarlos,
 * gestionar dulelos, ver historial, raking y actualizar puntajes
 *
 * @author Diane
 * @version 2.0
 */

public class MenuPrincipal {

    private static Scanner lectura = new Scanner(System.in);
    private static JugadorDAO jugadorDAO = new JugadorDAO();
    private static Gestionar_Duelos gestorDuelos = new Gestionar_Duelos();

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
        } while (opcion != 'H');

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
        System.out.println("│ A - Registrar jugador                   │");
        System.out.println("│ B - Ver lista de jugadores              │");
        System.out.println("│ C - Buscar jugador por ID o nombre      │");
        System.out.println("│ D - Gestionar duelos                    │");
        System.out.println("│ E - Historial de partidas               │");
        System.out.println("│ F - Ranking actual                      │");
        System.out.println("│ G - Actualización de puntaje            │");
        System.out.println("│ H - Salir                               │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.print("»» Seleccione una opción: ");
    }

    /**
     * Lee y valida la opción seleccionada por el usuario.
     * Asegura que la entrada sea una letra válida (A-H)
     * y maneja casos de entradas no válidas.
     *
     * @return char La opción seleccionada por el usuario, convertida a mayúscula.
     */
    private static char eleccion() {

        while (true) {

            String entrada = lectura.nextLine().trim();

            if (entrada.isEmpty()) {
                System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
                continue;
            }

            char opcion = Character.toUpperCase(entrada.charAt(0));

            if (opcion >= 'A' && opcion <= 'H') {
                return opcion;
            }

            System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
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
                gestionarDuelos();
                break;

            case 'E':
                verHistorialPartidas();
                break;

            case 'F':
                mostrarRankingActual();
                break;

            case 'G':
                actualizarPuntajes();
                break;

            case 'H':
                System.out.println("\nSaliendo del programa...");
                break;

            default:
                System.out.println("Opción no válida.");
        }
    }

    // --------- Métodos para cada opción del menú con try-catch ---------

    /**
     * Opción A : Registrar un nuevo jugador en el sistema
     * Solicita nombre y rating al usuario, crea un nuevo jugador y lo guarda en la base de datos.
     * El ID se genera automáticamente.
     */
    private static void registrarJugador() {

        System.out.println("\n------ REGISTRAR NUEVO JUGADOR ------");

        try {

            System.out.print("Nombre del jugador: ");
            String nombre = lectura.nextLine().trim();

            if (nombre.isEmpty()) {
                throw new DatosNulosException("nombre");
            }

            System.out.print("Puntaje inicial: ");
            double puntaje = Double.parseDouble(lectura.nextLine().trim());

            Jugador nuevo = new Jugador(0, nombre, puntaje);

            jugadorDAO.insertar(nuevo);

            System.out.println("\nJugador registrado exitosamente:");
            System.out.println(nuevo);

        } catch (DatosNulosException e) {

            System.out.println("Error de datos: " + e.getMessage());

        } catch (NumberFormatException e) {

            System.out.println("Error: el puntaje debe ser numérico.");

        } catch (SQLException e) {

            System.out.println("Error en la base de datos: " + e.getMessage());

        } catch (Exception e) {

            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    /**
     * Opción B: Muestra la lista de todos los jugadores registrados.
     * Si no hay jugadores registrados, muestra un mensaje indicándolo.
     */
    private static void mostrarJugadores() {

        System.out.println("\n------ LISTA DE JUGADORES REGISTRADOS ------");

        try {

            List<Jugador> listaJugadores = jugadorDAO.obtenerTodos();

            Gestionar_Jugadores gestor = new Gestionar_Jugadores(listaJugadores);

            gestor.mostrarJugadores();

        } catch (SQLException e) {

            System.out.println("Error en la base de datos: " + e.getMessage());

        } catch (Exception e) {

            System.out.println("Error al cargar jugadores: " + e.getMessage());
        }
    }


    /**
     * Opción C: Busca un jugador por su ID o por su nombre y muestra sus datos.
     * Si el jugador no existe, muestra un mensaje indicándolo.
     * Si el usuario ingresa un valor inválido, se maneja la excepción.
     */
    private static void buscarJugador() {

        System.out.println("\n------ BUSCAR JUGADOR ------");

        try {

            List<Jugador> listaJugadores = jugadorDAO.obtenerTodos();

            Gestionar_Jugadores gestor = new Gestionar_Jugadores(listaJugadores);

            System.out.println("1. Buscar por ID.");
            System.out.println("2. Buscar por nombre.");
            System.out.print("Seleccione una opción: ");

            int opcionBusqueda = Integer.parseInt(lectura.nextLine().trim());

            Jugador encontrado = null;

            switch (opcionBusqueda) {

                case 1:

                    System.out.print("» ID del jugador: ");

                    int id = Integer.parseInt(lectura.nextLine().trim());

                    encontrado = gestor.buscar_IdJugador(id);

                    break;

                case 2:

                    System.out.print("» Nombre del jugador: ");

                    String nombre = lectura.nextLine().trim();

                    encontrado = gestor.buscar_NombreJugador(nombre);

                    break;

                default:

                    System.out.println("Opción de búsqueda no válida.");
                    return;
            }

            if (encontrado != null) {

                System.out.println("\nJugador encontrado:");
                System.out.println(encontrado);

            } else {

                System.out.println("\n¡Jugador no encontrado!");
            }

        } catch (NumberFormatException e) {

            System.out.println("Error: Debe ingresar un valor válido.");

        } catch (SQLException e) {

            System.out.println("Error en la base de datos: " + e.getMessage());

        } catch (Exception e) {

            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    /**
     * Opción D: Gestionar duelos entre jugadores usando
     * una estructura de cola.
     * Estado : Pendiente.
     */
    private static void gestionarDuelos() {

        System.out.println("\n------------- GESTIONAR DUELOS -------------");

        try {
            List<Jugador> lista = jugadorDAO.obtenerTodos();

            Gestionar_Jugadores gestor = new Gestionar_Jugadores(lista);

            Jugador[] jugadores = gestor.getJugadores();

            // Limpiar cola antes de volver a cargar
            gestorDuelos.limpiarCola();

            // Meter jugadores en la cola
            for (Jugador j : jugadores) {
                gestorDuelos.agregarJugador(j);
            }

            // Mostrar cola
            gestorDuelos.mostrarCola();

            // Realizar ronda completa (NO solo un duelo)
            gestorDuelos.realizarRonda();
        } catch (SQLException e) {
            System.out.println("Error en base de datos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error en duelos: " + e.getMessage());
        }
    }

    /**
     * Opción E: Ver el historial de partidas jugadas usando
     * una estructura de pila.
     * Estado : Pendiente.
     */
    private static void verHistorialPartidas() {

        System.out.println("\n------ HISTORIAL DE PARTIDAS ------");
        System.out.println("Funcionalidad pendiente de implementar.");
    }

    /**
     * Opción F: Mostrar el ranking actual de jugadores ordenados por puntaje acumulado.
     * Estado : Pendiente.
     */
    private static void mostrarRankingActual() {

        System.out.println("\n------ RANKING ACTUAL DE JUGADORES ------");
        System.out.println("Funcionalidad pendiente de implementar.");
    }

    /**
     * Opción G: Actualizar los puntajes de los jugadores después de cada duelo.
     * Estado : Pendiente.
     */
    private static void actualizarPuntajes() {

        System.out.println("\n------ ACTUALIZACIÓN DE PUNTAJES ------");
        System.out.println("Funcionalidad pendiente de implementar.");
    }
}
