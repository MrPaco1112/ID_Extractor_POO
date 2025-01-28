
//Acá van los metodos para CRUD
 
package com.mycompany.idmanager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;




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
                        System.out.println("Error al agregar la informacion: " + error.getMessage());
                    } else {
                        System.out.println("Informacion agregada correctamente al nodo: " + ref.getPath());
                    }
                }
            });
            
            System.out.println("Operacion de escritura completada, esperando confirmacion...");
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
        }
    }
    public static void leerInfo(FirebaseDatabase firebaseDatabase, String nodo, String clave){ //R
        DatabaseReference referencia = firebaseDatabase.getReference(nodo).child(clave);
        System.out.println("Informacion en el nodo: " + referencia.getPath());
        referencia.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("onDataChange llamado.");
                // Obtener la persona desde Firebase
                Persona persona = dataSnapshot.getValue(Persona.class);
                
                if (persona != null) {
                    System.out.println("Datos actuales: " + persona);
                    
                } else {
                    System.out.println("Empleado no encontrado.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error al obtener la información: " + databaseError.getMessage());
            }
        });
    }
    public static void actualizarInfo(FirebaseDatabase firebaseDatabase, String nodo, String clave, Persona nuevoValor){ //U
        try {
        DatabaseReference referencia = firebaseDatabase.getReference(nodo).child(clave);
        referencia.setValue(nuevoValor, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                if (error != null) {
                    System.out.println("Error al actualizar la informacion: " + error.getMessage());
                } else {
                    System.out.println("Informacion actualizada correctamente en el nodo: " + ref.getPath());
                }
            }
        });
        } catch (Exception e) {
        System.out.println("Error al actualizar la informacion: " + e.getMessage());
        }
    }
    public static void borrarInfo(){ //D
        
    }
}
