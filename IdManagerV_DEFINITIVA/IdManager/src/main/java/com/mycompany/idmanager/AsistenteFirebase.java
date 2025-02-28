//Aca se inicializa firebase y
//Acá van los metodos para CRUD
 
package com.mycompany.idmanager;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AsistenteFirebase {
    public static FirebaseDatabase firebaseDatabase;
// Agregado por Angel
        
    //Inicializa la conexión a Firebase RTD (Real Time Database).
    public static void initRTD() {
        try {
            // Lee el archivo de credenciales 
            FileInputStream serviceAccount = new FileInputStream("src/main/resources/firebase2_clave.json");
            
	    //URL de la base de datos
            FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://idmanager-4ded8-default-rtdb.firebaseio.com/")
                .build();
                
            FirebaseApp.initializeApp(options);
            firebaseDatabase = FirebaseDatabase.getInstance();
            System.out.println("Firebase se ha INICIALIZADO CORRECTAMENTE.");
            
        } catch (RuntimeException ex) {
            System.out.println("Problema en la ejecución: " + ex.getMessage());
        } catch (FileNotFoundException ex) {
            System.out.println("Problema con el archivo de credenciales: " + ex.getMessage());
        } catch (IOException e) {
            System.out.println("Error al cargar las credenciales: " + e.getMessage());
        }
    }
//   
    
    static HashMap personas = new HashMap<>();
    
    public static HashMap leerDB(FirebaseDatabase firebaseDatabase, String nodo){
        CountDownLatch latch = new CountDownLatch(1);
        DatabaseReference referencia = firebaseDatabase.getReference(nodo);
        System.out.println("Informacion en el nodo: " + referencia.getPath());
        referencia.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                personas = (HashMap) dataSnapshot.getValue();
                latch.countDown();
            }
            
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error al obtener la información: " + databaseError.getMessage());
            }
        });
        try {
            latch.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(AsistenteFirebase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return personas;
    }
    
    public static void agregarInfo(FirebaseDatabase firebaseDatabase, String nodo, Empresa valor){ //C
        try {
            
            // Obtener referencia al nodo especificado
            System.out.println("Obteniendo referencia al nodo: " + nodo);
            DatabaseReference referencia = firebaseDatabase.getReference(nodo);
            String clave = referencia.push().getKey(); 
            if(clave != null){
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
            }else{
                System.out.println("Error al generar la clave única");
            }
            System.out.println("Operacion de escritura completada, esperando confirmacion...");
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
        }
    }
    
    public static void leerInfo(FirebaseDatabase firebaseDatabase, String nodo, String clave){ //R
        CountDownLatch latch = new CountDownLatch(1);
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
                latch.countDown();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error al obtener la información: " + databaseError.getMessage());
            }
        });
        try {
        latch.await();
        } catch (InterruptedException ex) {
        System.out.println("Error al obtener informacion: " + ex.getMessage());
        }
    }
    
    public static void actualizarInfo(FirebaseDatabase firebaseDatabase, String nodo, String clave, Empresa nuevoValor){ //U
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
                        System.out.println("Error al eliminar la informacion: " + error.getMessage());
                    } else {
                        System.out.println("Informacion eliminada correctamente del nodo: " + ref.getPath());
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
        System.out.println("Error al borrar informacion: " + ex.getMessage());
        }
    }
    
    public static Empresa obtenerInfo(FirebaseDatabase firebaseDatabase, String nodo, String clave) throws InterruptedException{ //R
        DatabaseReference referencia = firebaseDatabase.getReference(nodo).child(clave);
        final Empresa[] empresa = new Empresa[1]; // Crear un arreglo de una posición para almacenar la persona
        final CountDownLatch latch = new CountDownLatch(1); // Para esperar la respuesta asincrónica
        referencia.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("onDataChange llamado.");
                // Obtener la persona desde Firebase y guardarla en el arreglo
                empresa[0] = dataSnapshot.getValue(Empresa.class);
                latch.countDown(); // Liberamos el latch
                
                if (empresa != null) {
                    System.out.println("Datos actuales: " + empresa[0]); 
                } else {
                    System.out.println("Empleado no encontrado.");
                    latch.countDown(); 
                }
            }
            
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error al obtener la información: " + databaseError.getMessage());
            }
        });
        latch.await(); // Esperamos a que se complete el callback
        return empresa[0]; // Retornamos la persona obtenida
        
    }
    public static String obtenerClavePorNombre(FirebaseDatabase firebaseDatabase, String nombreEmpresa) throws InterruptedException {
    DatabaseReference ref = firebaseDatabase.getReference("empresas");
    final String[] claveEncontrada = {null};
    final CountDownLatch latch = new CountDownLatch(1); // Esperar la respuesta de Firebase

    ref.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                Empresa empresa = snapshot.getValue(Empresa.class);
                
                if (empresa != null && empresa.getNombreEmp().equalsIgnoreCase(nombreEmpresa)) {
                    claveEncontrada[0] = snapshot.getKey(); // Guardamos la clave si el nombre coincide
                    break;
                }
            }
            latch.countDown(); // Libera el latch
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            System.out.println("Error al obtener la clave: " + databaseError.getMessage());
            latch.countDown();
        }
    });

    try {
        latch.await();
        } catch (InterruptedException ex) {
        System.out.println("Error al obtener informacion: " + ex.getMessage());
        }
    return claveEncontrada[0]; // Retornar la clave encontrada
    }
    public static List<Empresa> listaEmpresas = new ArrayList<>();
    public static void bajarListaFireBase(){
        DatabaseReference ref = firebaseDatabase.getReference("empresas");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                listaEmpresas.clear();
                for (DataSnapshot empresaSnapshot : snapshot.getChildren()) {
                    Empresa empresa = empresaSnapshot.getValue(Empresa.class);
                    if (empresa != null) {
                        listaEmpresas.add(empresa);
                    }
                }
                System.out.println("Empresas descargadas: " + listaEmpresas.size());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("Error al descargar empresas: " + error.getMessage());
            }
        });
    }
    public static Empresa obtenerEmpresaPorNombre(String nombreBuscado) {
        return listaEmpresas.stream().filter(empresa -> empresa.getNombreEmp().equalsIgnoreCase(nombreBuscado)).findFirst().orElse(null);
    }

    public List<Empresa> getListaEmpresas(){
        return listaEmpresas;
    }
}
