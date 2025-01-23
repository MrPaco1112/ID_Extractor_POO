
package id.extractor;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;


import java.io.*;

public class AsistenteFirebase {
    
    private static FirebaseFirestore firebaseFirestore;  // Instancia de Firestore, esto me perimte solo inicializar 1 vez la conexion
    
    // Método estático para obtener la instancia de FirebaseFirestore
    public static FirebaseFirestore getFirebaseFirestore() {
        if (firebaseFirestore == null) {  // Si no se ha inicializado aún
            initFirebase();  // Llamar a initFirebase para inicializar la conexión
        }else{
            System.out.println("Conexion a Firebase exitosa");//Si ya se inicializó me confirma que se encontró la instancia
        }
        return firebaseFirestore;  // Devolver la instancia de Firestore
    }
    
    
    // Método para inicializar Firebase
    private static void initFirebase() {
        try {
            // Configuración de Firebase
            FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                    .setProjectId("pacopruebas-165de")
                    .setCredentials(GoogleCredentials.fromStream(new FileInputStream(new File("D:\\Monica Sarmiento\\Documents\\NetBeansProjects\\Clave Firestore"))))
                    .build();
            // Inicializa la aplicación Firebase con las opciones
            FirebaseApp.initializeApp(firebaseOptions);
            // Obtener la instancia de Firestore
            firebaseFirestore = FirebaseFirestore.getInstance();
            System.out.println("Conexión a Firestore exitosa.");
        }catch (FileNotFoundException e) {
            e.printStackTrace();  // Si el archivo de la clave no se encuentra, mostrar error
        } catch (RuntimeException e) {
            e.printStackTrace();  // Manejo de excepciones en tiempo de ejecución
        }
    }
    private static void agregarEmpleado(){ //C
        
    }
    private static void verInfo(){ //R
        
    }
    private static void actualizarInfo(){ //U
        
    }
    private static void eliminarInfo(){ //D
        
    }
}
