
package id.extractor;

import java.util.*;


public class IDExtractor {


    public static void main(String[] args) {
     System.out.println("==== Bienvenido a la fabrica de pociones digital de Paco =====");
        Scanner s = new Scanner(System.in);
        int opcion1 = -1;
        int opcion2 = -1;
        AsistenteFirebase.getFirebaseFirestore();
        
        do{
            try{
                System.out.println("=== Menu Principal ===");
                System.out.println("1. Gestionar Empleados");
                System.out.println("2. Extractor de C.C");
                System.out.println("3. Salir");
                System.out.print("Elige una opcion: ");
                opcion1 = s.nextInt();                
            }catch(InputMismatchException e){
                System.out.println("Error el valor ingresado no es valido, reintentar");
                System.out.println();
                break;
            }
            switch(opcion1){
                case 1:
                    try{
                        System.out.println("=== Menu Principal ===");
                        System.out.println("1. Añadir empleado");
                        System.out.println("2. Actualizar info empleado");
                        System.out.println("3. Ver empleados");
                        System.out.println("4. Eliminar empleados");
                        System.out.println("5. Salir");
                        System.out.print("Elige una opcion: ");
                        opcion2 = s.nextInt();                
                    }catch(InputMismatchException e){
                        System.out.println("Error el valor ingresado no es valido, reintentar");
                        System.out.println();
                        break;
                    }
                    switch(opcion2){
                        case 1:
                            
                            break;
                        case 2:
                            
                            break;
                        case 3:
                            
                            break;
                        case 4:
                            
                            break;
                        case 5:
                            System.out.println("Saliendo...");
                            break;
                        default:
                            
                            break;
                    }
                    break;
                case 2:
                    System.out.println("Esta funcion aun no está completa");
                    break;
                case 3:
                    System.out.println("Saliendo...");
                     break;

                default:
                    
            }        
        }while(opcion1 != 5);   
    }
    
}