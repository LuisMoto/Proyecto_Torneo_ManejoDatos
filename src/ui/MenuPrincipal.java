package ui;

import java.util.Scanner;

public class MenuPrincipal {
    private static Scanner lectura = new Scanner(System.in);

    public static void main (String[] args){
        //Declaración de la variable para almacenar la opción seleccionada por el usuario
        char opcion; 
        //iniciamos un bucle do-while para mostrar el menú y procesar la opción seleccionada por el usuario
        do{ 
            mostrarMenu();
            opcion = eleccion();
            procesarOpcion(opcion);
        }while (opcion != 'H' && opcion != 'h');

        System.out.println("Nos vemos pronto!");
        lectura.close();

    }
    
    // Método para mostrar el menú principal al usuario
    private static void mostrarMenu(){ 
         System.out.println("\n==== MENU PRINCIPAL - TORNEo AJEDREZ====");
         System.out.println("A - Registrar jugador");
         System.out.println("B - Cargar jugadores desde BD a arreglo");
         System.out.println("C - Buscar jugador por ID");
         System.out.println("D - Gestionar duelos");
         System.out.println("E - Historial de partidas");
         System.out.println("F - Ranking actual");
         System.out.println("G - Actualización de puntajes tras partidas");
         System.out.println("H - Salir");
         System.out.print("Seleccione una opción: ");

    }

    // Método para leer la opción seleccionada por el usuario y validarla
    private static char eleccion(){
        // Iniciamos un bucle para validar la entrada del usuario
        while (true){
            System.out.print("Opción: ");
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

    private static void procesarOpcion(char opcion){
        switch (opcion){
            case 'A':
                System.out.println("Registrar jugador ");
                // Metodo para registrar jugador
                break;
            case 'B':
                System.out.println("Cargar jugadores desde BD a arreglo.");
                // Metodo para cargar jugadores desde BD a arreglo
                break;
            case 'C':
                System.out.println("Buscar jugador por ID .");
                // Metodo para buscar jugador
                break;
            case 'D':
                System.out.println("Gestionar duelos.");
                // Metodo para gestionar duelos
                break;
            case 'E':
                System.out.println("Historial de partidas .");
                // Metodo para mostrar historial de partidas
                break;
            case 'F':
                System.out.println("Ranking actual .");
                // Metodo para mostrar ranking actual
                break;
            case 'G':
                System.out.println("Actualización de puntajes tras partidas .");
                // Metodo para actualizar puntajes
                break;
            case 'H':
                System.out.println("Salir del programa.");
                break;
            default:
                System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
        }
    }


}
