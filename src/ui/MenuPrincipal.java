package ui;

import java.util.Scanner;

import Participantes.Gestionar_Jugadores;
import Entidades.Jugador;

public class MenuPrincipal {
    private static Scanner lectura = new Scanner(System.in);
    private static Gestionar_Jugadores gestor = new Gestionar_Jugadores();

    public static void main (String[] args){
        //Declaración de la variable para almacenar la opción seleccionada por el usuario
        char opcion; 
        //iniciamos un bucle do-while para mostrar el menú y procesar la opción seleccionada por el usuario
        do{ 
            mostrarMenu();
            opcion = eleccion();
            procesarOpcion(opcion);
        }while (opcion != 'H' && opcion != 'h');

        System.out.println("Nos vemos pronto!\n");
        lectura.close();

    }
    
    // Método para mostrar el menú principal al usuario
    private static void mostrarMenu(){ 
        System.out.println("\n╔═══════════════════════════════════╗");
        System.out.println("║      TORNEO DE AJEDREZ - MENU     ║");
        System.out.println("╠═══════════════════════════════════╣");
        System.out.println("║  A - Registrar jugador            ║");
        System.out.println("║  B - Cargar jugadores (Arreglo)   ║");
        System.out.println("║  C - Buscar jugador por ID        ║");
        System.out.println("║  D - Gestionar duelos             ║");
        System.out.println("║  E - Historial de partidas        ║");
        System.out.println("║  F - Ranking actual               ║");
        System.out.println("║  G - Actualizar puntajes          ║");
        System.out.println("║  H - Salir                        ║");
        System.out.println("╚═══════════════════════════════════╝");
        System.out.print("Seleccione una opción: ");
    }

    // Método para leer la opción seleccionada por el usuario y validarla
    private static char eleccion(){
        // Iniciamos un bucle para validar la entrada del usuario
        while (true){
            String entrada = lectura.nextLine().trim();
            
            if (entrada.isEmpty()){
                System.out.println("Opción no válida. Intenta de nuevo.");
                continue;
            }

            char opcion = entrada.toUpperCase().charAt(0);

            if (opcion >= 'A' && opcion <= 'H'){
                return opcion;

            } else {
                System.out.println("Opción no válida. Intenta de nuevo.");
            }

        }
    }

    private static void procesarOpcion(char opcion){
        switch (opcion){

            // Metodo para registrar jugador
            case 'A':

                System.out.println("\n===============================================================");
                System.out.println("                         REGISTRAR JUGADOR");
                System.out.println("===============================================================\n");

                int id;

                while (true) {
                    try {
                        System.out.print("- ID del jugador: ");
                        id = Integer.parseInt(lectura.nextLine());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Error: el ID debe ser numérico, intentalo otra vez.\n");
                    }
                }

                System.out.print("- Nombre del jugador: ");
                String nombre = lectura.nextLine();


                Jugador nuevo = new Jugador(id, nombre, 0);

                gestor.agregar_Jugador(nuevo);

                System.out.println("\n««««««««««  Jugador registrado correctamente  »»»»»»»»»»");
                System.out.println(nuevo);

                System.out.println("\n===============================================================\n");

                break;


            // Metodo para cargar jugadores desde BD a arreglo
            case 'B':

                System.out.println("\n===============================================================");
                System.out.println("                       LISTA DE JUGADORES");
                System.out.println("===============================================================\n");

                gestor.mostrar_Jugadores();

                System.out.println("\n===============================================================\n");

                break;

            // Metodo para buscar jugador
            case 'C':

                System.out.println("\n===============================================================");
                System.out.println("                           BUSCAR JUGADOR");
                System.out.println("===============================================================\n");

                int idBuscado;

                while (true) {
                    try {
                        System.out.print("Ingrese ID del jugador: ");
                        idBuscado = Integer.parseInt(lectura.nextLine());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Error: el ID debe ser numérico, intentalo otra vez\n");
                    }
                }

                Jugador encontrado = gestor.buscar_IdJugador(idBuscado);

                System.out.println("\n«««««««««««««««««««««« BUSCANDO JUGADOR »»»»»»»»»»»»»»»»»»»»»»\n");

                if (encontrado != null) {
                    System.out.println(encontrado);
                } else {
                    System.out.println("Jugador no encontrado.");
                }

                System.out.println("\n===============================================================\n");

                break;

            case 'D':
                System.out.println("Gestionar duelos.");
                break;

            case 'E':
                System.out.println("Historial de partidas.");
                break;

            case 'F':
                System.out.println("Ranking actual.");
                break;

            case 'G':
                System.out.println("Actualización de puntajes tras partidas.");
                break;

            case 'H':
                System.out.println("\n«««««««««««««««««««« SALIENDO DEL PROGRAMA »»»»»»»»»»»»»»»»»»»»\n");
                break;

            default:
                System.out.println("Opción no válida.");
        }
    }

}
