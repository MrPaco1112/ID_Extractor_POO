
package id.extractor;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AsistenteFirebase {
    
    private static FirebaseFirestore firebaseFirestore;  // Instancia de Firestore, esto me perimte solo inicializar 1 vez la conexion
    
    // Método estático para obtener la instancia de FirebaseFirestore
    public static FirebaseFirestore getFirebaseFirestore() {
        if (firebaseFirestore == null) {  // Si no se ha inicializado aún
            initFirebase();  // Llamar a initFirebase para inicializar la conexión
        }
        return firebaseFirestore;  // Devolver la instancia de Firestore
    }
    
    
    // Método para inicializar Firebase
    private static void initFirebase() {
        try {
            // Configuración de Firebase
            FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                    .setProjectId("tu-proyecto-id")
                    .setCredentials(FirebaseCredentials.fromCertificate(new FileInputStream(new File("ruta/a/tu/clave.json"))))
                    .build();
            // Inicializa la aplicación Firebase con las opciones
            FirebaseApp.initializeApp(firebaseOptions);
            // Obtener la instancia de Firestore
            firebaseFirestore = FirebaseFirestore.getInstance();
            System.out.println("Conexión a Firestore exitosa.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();  // Si el archivo de la clave no se encuentra, mostrar error
        } catch (RuntimeException e) {
            e.printStackTrace();  // Manejo de excepciones en tiempo de ejecución
        }
    }
    
}
