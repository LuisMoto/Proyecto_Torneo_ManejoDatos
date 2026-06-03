package ui;

import excepciones.DatosNulosException;
import excepciones.FechaInvalidaException;
import excepciones.JugadorClonException;
import excepciones.RegistroInvalidoException;


import Entidades.Jugador;
import Participantes.Gestionar_Jugadores;
import dao.JugadorDAO;

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
    //private static Gestionar_Jugadores gestor = new Gestionar_Jugadores();
    private static JugadorDAO jugadorDAO = new JugadorDAO();

    /**
     * Método principal que inicia la aplicación y muestra el menú al usuario. 
     * Permite seleccionar opciones para gestionar jugadores, duelos, 
     * historial de partidas, ranking y actualización de puntajes.
     * 
     */

    public static void main (String[] args){
        //Declaración de la variable para almacenar la opción seleccionada por el usuario
        char opcion; 
        //iniciamos un bucle do-while para mostrar el menú y procesar la opción seleccionada por el usuario
        do{ 
            mostrarMenu();
            opcion = eleccion();
            procesarOpcion(opcion);
        }while (opcion != 'H' && opcion != 'h');

        System.out.println("\n¡Hasta la próxima, vaquero!");
        lectura.close();

    }
    
    /**
     * Método para mostrar el menú principal al usuario. 
     * 
     */
    private static void mostrarMenu(){ 
         System.out.println("\n┌────────────────────────────────────────┐");
         System.out.println("│  TORNEO DE AJEDREZ - SISTEMA DE RANKING │");
         System.out.println("├─────────────────────────────────────────┤");
         System.out.println("│ A ▸ Registrar jugador                   │");
         System.out.println("│ B ▸ Ver lista de jugadores              │");
         System.out.println("│ C ▸ Buscar jugador por ID               │");
         System.out.println("│ D ▸ Gestionar duelos                    │");
         System.out.println("│ E ▸ Historial de partidas               │");
         System.out.println("│ F ▸ Ranking actual                      │");
         System.out.println("│ G ▸ Actualización de puntaje            │");
         System.out.println("│ H ▸ Salir                               │");
         System.out.println("├─────────────────────────────────────────┤");
         System.out.print("➤ Seleccione una opción: ");

    }

    /**
     * Lee y valida la opción seleccionada por el usuario. 
     * Asegura que la entrada sea una letra válida (A-H) 
     * y maneja casos de entradas no válidas.
     * 
     * @return char La opción seleccionada por el usuario, convertida a mayúscula.
     */
    private static char eleccion(){
        // Iniciamos un bucle para validar la entrada del usuario
        while (true){
            System.out.print(" ➤ Opción: ");
            String entrada = lectura.nextLine().trim();
            
            if (entrada.isEmpty()){
            System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            continue;
        }

        char opcion = entrada.toUpperCase().charAt(0);

        if (opcion >= 'A' && opcion <= 'H'){
            return opcion;

        } else {
            System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
        }

        }
    }

    /**
     * Procesa la opción seleccionada y ejecuta la acción correspondiente. 
     * Cada caso del switch corresponde a una funcionalidad del sistema.
     * 
     * @param opcion La opción seleccionada por el usuario, representada como un carácter.
     */

    private static void procesarOpcion(char opcion){
        switch (opcion){
            case 'A':
                registrarJugador();
                break;
            case 'B':
                mostrarJugadores();
                break;
            case 'C':
                buscarJugadorPorID();
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
                System.out.println("Saliendo del programa......");
                break;
            default:
                System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
        }
    }
// --------- Métodos para cada opción del menú con try-catch ---------
/**
 * Opción A : Registrar un nuevo jugador en el sistema
 * Solicita nombre y rating al usuario, crea un nuevo jugador y lo guarda en la base de datos.
 * El ID se genera automáticamente. 
 */
    private static void registrarJugador(){
        System.out.println("\n ------ REGISTRAR NUEVO JUGADOR ------");
        try {
            System.out.print(" Nombre del jugador: ");

            String nombre = lectura.nextLine().trim();

            if (nombre.isEmpty()) {
                throw new DatosNulosException("nombre");
            }
            System.out.print(" Rating inicial (0-3000): ");
            
            // Validamos que el rating sea un número entero dentro del rango permitido
            int rating = Integer.parseInt(lectura.nextLine().trim());
            if (rating < 0 || rating > 3000) {
               System.out.println("El rating debe estar entre 0 & 3000. Se usará 1500 por defecto");
               rating = 1500; // Valor por defecto si el rating es inválido
            }
            Jugador nuevo = new Jugador(0, nombre, rating);
            // Aquí se llama al método del DAO para insertar el nuevo jugador en la base de datos
            jugadorDAO.insertar(nuevo); 
            
            System.out.println("\n Jugador registrado exitosamente ");
            System.out.println("  ┌─────────────────────────────────────┐");
            System.out.println("  │ ID:     " + nuevo.getIdJugador());
            System.out.println("  │ Nombre: " + nuevo.getNombreJugador());
            System.out.println("  │ Rating: " + nuevo.getPuntajeAcumulado());
            System.out.println("  └─────────────────────────────────────┘");
           
        
        }catch (DatosNulosException e){
            System.out.println("Error de datos: " + e.getMessage());
            System.out.println("Campo probelmatcico: "+ e.getCampoProblematico());
        }catch(NumberFormatException e){
            System.out.println("Error: El rating debe ser un número.");
        }catch (SQLException e){
            System.out.println("Error en la base de datos: " + e.getMessage());
        }catch (Exception e){
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }
    
    
    /**
     * Opción B: Muestra la lista de todos los jugadores registrados.
     * Si no hay jugadores registrados, muestra un mensaje indicandolo.
     */
    private static void mostrarJugadores(){
        System.out.println("\n ------ LISTA DE JUGADORES REGISTRADOS ------");
        try {
            List<Jugador> jugadores = jugadorDAO.obtenerTodos();
            
            if (jugadores.isEmpty()) {
                System.out.println(" No hay jugadores registrados.");
            } else {
                for (Jugador j : jugadores) {
                    System.out.println("  ┌─────────────────────────────────────┐");
                    System.out.println("  │ ID:     " + j.getIdJugador());
                    System.out.println("  │ Nombre: " + j.getNombreJugador());
                    System.out.println("  │ Rating: " + j.getPuntajeAcumulado());
                    System.out.println("  └─────────────────────────────────────┘");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al cargar jugadores: " + e.getMessage());
        }    
    } 


    /**
     * Opción C: Busca un jugador por su ID y muestra sus datos.
     * Si el ID no existe, muestra un mensaje indicándolo.
     * Si el usuario ingresa un valor no numérico, se maneja la excepción.
     */

    private static void buscarJugadorPorID(){
        System.out.println("\n ------ BUSCAR JUGADOR POR ID ------");

        try{
            System.out.print("ID del jugador:");
            
            int id = Integer.parseInt(lectura.nextLine().trim());

            Jugador encontrado = jugadorDAO.buscarPorId(id);
            if (encontrado != null){
                System.out.println("\n Jugador encontrado: ");
                System.out.println("  ┌─────────────────────────────────────┐");
                System.out.println("  │ ID:     " + encontrado.getIdJugador());
                System.out.println("  │ Nombre: " + encontrado.getNombreJugador());
                System.out.println("  │ Rating: " + encontrado.getPuntajeAcumulado());
                System.out.println("  └─────────────────────────────────────┘");
            } else {
                System.out.println("No se encontró ningún jugador con ID: " + id);
            }
        }catch(NumberFormatException e){
            System.out.println("Error: El ID debe ser un número entero.");
        }catch (SQLException e){
            System.out.println("Error en la base de datos: " + e.getMessage());
        }    
    }

    /**
     * Opción D: Gestionar duelos entre jugadores usando 
     * una estructura de cola.
     * Estado : Pendiente.
     */

    private static void gestionarDuelos(){
        System.out.println("\n ------ GESTIONAR DUELOS ------");
        //Pendiente
    }
    
    /**
     * Opción E: Ver el historial de partidas jugadas usando
     * una estructura de pila.
     * Estado : Pendiente.
     */

    private static void verHistorialPartidas(){
        System.out.println("\n ------ HISTORIAL DE PARTIDAS ------");
        //Pendiente
    }


    /**
     * Opción F: Mostrar el ranking actual de jugadores ordenados por puntaje acumulado.
     * Estado : Pendiente.
     */

    private static void mostrarRankingActual(){
        System.out.println("\n ------ RANKING ACTUAL DE JUGADORES ------");
        //Pendiente
    }
    
    /**
     * Opción G: Actualizar los puntajes de los jugadores después de cada duelo.
     * Estado : Pendiente.
     */
    
    private static void actualizarPuntajes(){
        System.out.println("\n ------ ACTUALIZACIÓN DE PUNTAJES ------");
        //Pendiente
    }    
 
}
