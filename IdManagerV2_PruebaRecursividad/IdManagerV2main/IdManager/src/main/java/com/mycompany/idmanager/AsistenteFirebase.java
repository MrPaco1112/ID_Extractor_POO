
//Acá van los metodos para CRUD
 
package com.mycompany.idmanager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import java.util.concurrent.CountDownLatch;
import java.util.*;




public class AsistenteFirebase {
    
    //private static FirebaseDatabase firebaseDatabase;
    public static void agregarInfo(FirebaseDatabase firebaseDatabase, String nodo, String clave, Persona valor){ //C
        CountDownLatch latch = new CountDownLatch(1);
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
                    latch.countDown();
                }
                
            });
            
            System.out.println("Operacion de escritura completada, esperando confirmacion...");
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
        }
        try {
        latch.await();
        } catch (InterruptedException ex) {
        System.out.println("Error al agregar informacion: " + ex.getMessage());
        }
    }
    
    public static void leerInfo(FirebaseDatabase firebaseDatabase, String nodo){
        Scanner s1 = new Scanner(System.in);//R
        
        DatabaseReference referencia = firebaseDatabase.getReference(nodo);
        System.out.println("Informacion en el nodo: " + referencia.getPath());
        referencia.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                
                System.out.println(firebaseDatabase.getReference(nodo));
                System.out.println("onDataChange llamado.");
                // Obtener la persona desde Firebase
                System.out.println(dataSnapshot.getValue());
                
                System.out.print("Elige una opcion: ");
                String opc = s1.nextLine();
                leerInfo(firebaseDatabase, opc);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error al obtener la información: " + databaseError.getMessage());
            }
        });
        
    }
    
    public static void actualizarInfo(FirebaseDatabase firebaseDatabase, String nodo, String clave, Persona nuevoValor){ //U
        CountDownLatch latch = new CountDownLatch(1);
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
                latch.countDown();
            }
        });
        try {
        latch.await();
        } catch (InterruptedException ex) {
        System.out.println("Error al actualizar informacion: " + ex.getMessage());
        }
        } catch (Exception e) {
        System.out.println("Error al actualizar la informacion: " + e.getMessage());
        }
    }
    public static void borrarInfo(FirebaseDatabase firebaseDatabase, String nodo, String clave){ //D
        CountDownLatch latch = new CountDownLatch(1);
        try {
            
            // Obtener referencia al nodo especificado
            System.out.println("Obteniendo referencia al nodo: " + nodo);
            DatabaseReference referencia = firebaseDatabase.getReference(nodo);

            // Borrar la información del nodo
            
            referencia.child(clave).removeValue(new DatabaseReference.CompletionListener() {
                @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                    if (error != null) {
                        System.out.println("Error al agregar la informacion: " + error.getMessage());
                    } else {
                        System.out.println("Informacion eliminada correctamente del nodo: " + ref.getPath());
                    }
                }
            });
            
            System.out.println("Operacion de escritura completada, esperando confirmacion...");
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
        }
        try {
        latch.await();
        } catch (InterruptedException ex) {
        System.out.println("Error al borrar informacion: " + ex.getMessage());
        }
    }
        public static Persona obtenerInfo(FirebaseDatabase firebaseDatabase, String nodo, String clave) throws InterruptedException{ //R
        DatabaseReference referencia = firebaseDatabase.getReference(nodo).child(clave);
        final Persona[] persona = new Persona[1]; // Crear un arreglo de una posición para almacenar la persona
        final CountDownLatch latch = new CountDownLatch(1); // Para esperar la respuesta asincrónica
        referencia.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("onDataChange llamado.");
                // Obtener la persona desde Firebase y guardarla en el arreglo
                persona[0] = dataSnapshot.getValue(Persona.class);
                latch.countDown(); // Liberamos el latch
                
                if (persona != null) {
                    
                    
                } else {
                    System.out.println("Empleado no encontrado.");
                    latch.countDown(); 
                }
                latch.countDown();
            }
            
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error al obtener la información: " + databaseError.getMessage());
            }
        });
        latch.await(); // Esperamos a que se complete el callback
        return persona[0]; // Retornamos la persona obtenida
    }
}
