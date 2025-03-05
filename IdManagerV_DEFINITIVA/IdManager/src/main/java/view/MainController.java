package view;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mycompany.idmanager.AsistenteFirebase;
import com.mycompany.idmanager.Carpeta;
import com.mycompany.idmanager.Empresa;
import com.mycompany.idmanager.Persona;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;


public class MainController {
    AsistenteFirebase manager = new AsistenteFirebase();
    
    Carpeta Archivador = new Carpeta();
    
    @FXML
    private BorderPane ventanaPrincipal;
    
    @FXML
    private Label IDManager;
    
    @FXML
    private ImageView logoImageView;

    @FXML
    private ListView<String> companyListView;

    @FXML
    private Button btnCrearEmpresa, btnArchivadas;
    Empresa empresaSeleccionada = new Empresa();
    @FXML
    public void initialize() {
        // Se carga el logo
        Image logo = new Image(getClass().getResourceAsStream("/view/logo.png"));
        logoImageView.setImage(logo);
        
        // Se carga la scene/vista de tareas de manera predeterminada
        switchToTasksView();
        
        // Listener para detectar doble clic en el titulo ID Manager
        IDManager.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                switchToTasksView();
            }
        });
        
        // Listener para detectar doble clic en la lista de las empresas
        companyListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String selectedNombre = companyListView.getSelectionModel().getSelectedItem();
                if (selectedNombre != null) {
                    empresaSeleccionada = AsistenteFirebase.obtenerEmpresaPorNombre(selectedNombre);
                    try {
                        switchToCompanyDetailsView(selectedNombre);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        
        // Cargar lista de empresas
        cargarEmpresasDesdeFirebase();
    }

    @FXML
    private void handleCerrarSesion(ActionEvent event) {
        System.out.println("Sesion Finalizada...");

        // Se eliminan las credenciales de Google almacenadas
        File credentialsDir = new File(System.getProperty("user.home"), "credentials");
        if (credentialsDir.exists()) {
            for (File file : credentialsDir.listFiles()) {
                file.delete();
            }
            credentialsDir.delete();
        }

        // Mostrar mensaje de confirmación
        System.out.println("Se han eliminado las credenciales. El usuario deberá autenticarse de nuevo.");

        // Cargar LoginView.fxml de nuevo en el mismo Stage
        try {
            // Cargar la vista de LoginView.fxml
            Parent loginRoot = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
            Scene loginScene = new Scene(loginRoot);

            // Agregar el archivo de estilo CSS
            loginScene.getStylesheets().add(getClass().getResource("/view/styles.css").toExternalForm());

            // Obtener el Stage actual (ventana principal)
            Stage currentStage = (Stage) btnCrearEmpresa.getScene().getWindow();

            // Establecer la nueva escena con LoginView
            currentStage.setScene(loginScene);
            currentStage.setTitle("Login - ID Manager");

            // Mostrar la vista de login
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    
    
    @FXML
    private void handleCrearEmpresa(ActionEvent event) {
        System.out.println("Crear nueva empresa...");
         Empresa nuevaEmpresa = Empresa.crearEmpresa();
    
        // Verifica que realmente se haya creado la empresa
        if (nuevaEmpresa != null) {
            System.out.println("Empresa creada: " + nuevaEmpresa);
            
            
            AsistenteFirebase.agregarInfo(manager.firebaseDatabase, "empresas", nuevaEmpresa);
        } else {
            System.out.println("Error al crear empresa, reintentar");
        }
    }

    @FXML
    private void handleArchivadas(ActionEvent event) {
        System.out.println("Abrir archivo o ver empresas archivadas...");
        Archivador.CaminoCarpeta();
        // Lógica para mostrar empresas archivadas o abrir carpeta
    }

    private void switchToTasksView() {
        try {
            Parent tasksView = FXMLLoader.load(getClass().getResource("/view/TasksView.fxml"));
            ventanaPrincipal.setCenter(tasksView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void switchToCompanyDetailsView(String empresa) throws InterruptedException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CompanyDetailsView.fxml"));
            Parent companyDetails = loader.load();

            // Obtener el controlador de CompanyDetails para pasarle datos
            CompanyDetailsController controller = loader.getController();
            controller.setEmpresa(empresaSeleccionada);

            ventanaPrincipal.setCenter(companyDetails);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void cargarEmpresasDesdeFirebase() {
    DatabaseReference ref = manager.firebaseDatabase.getReference("empresas");

    ref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot snapshot) {
            // Limpiar lista antes de añadir nuevas empresas
            companyListView.getItems().clear();

            // Recorrer cada nodo dentro de "empresas"
            for (DataSnapshot empresaSnapshot : snapshot.getChildren()) {
                Empresa empresa = empresaSnapshot.getValue(Empresa.class);
                if (empresa != null) {
                    companyListView.getItems().add(empresa.getNombreEmp()); // Suponiendo que tienes un getter getNombre()
                    Archivador.CrearCarpeta(empresa.getNombreEmp());
                    for (Persona empleado : empresa.getEmpleados()){
                        Archivador.CrearCarpeta(empresa.getNombreEmp()+"\\"+empleado.getNombre());
                    }
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError error) {
            System.out.println("Error al leer empresas: " + error.getMessage());
        }
    });
}

}
