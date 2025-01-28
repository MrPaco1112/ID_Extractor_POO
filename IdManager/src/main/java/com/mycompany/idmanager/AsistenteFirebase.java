
//Acá van los metodos para CRUD
 
package com.mycompany.idmanager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseError;
import java.util.*;


public class AsistenteFirebase {
    
    //private static FirebaseDatabase firebaseDatabase;
    public static void agregarInfo(FirebaseDatabase firebaseDatabase, String nodo, String clave, Persona valor){ //C
        try {
            
            // Obtener referencia al nodo especificado
            System.out.println("Obteniendo referencia al nodo: " + nodo);
            DatabaseReference referencia = firebaseDatabase.getReference(nodo);

            // Agregar la información al nodo
            System.out.println("Subiendo datos: " + valor.toString());
            referencia.child(clave).setValue(valor, new DatabaseReference.CompletionListener() {
                @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                    if (error != null) {
                        System.out.println("Error al agregar la información: " + error.getMessage());
                    } else {
                        System.out.println("Información agregada correctamente al nodo: " + ref.getPath());
                    }
                }
            });
            
            System.out.println("Operación de escritura completada, esperando confirmación.");
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
        }
    }
    public static void leerInfo(){ //R
        
    }
    public static void actualizarInfo(){ //U
        
    }
    public static void borrarInfo(){ //D
        
    }
}
