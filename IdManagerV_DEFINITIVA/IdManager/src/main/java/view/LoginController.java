package view;

import com.google.api.client.http.javanet.NetHttpTransport;
import java.io.IOException;
import com.mycompany.idmanager.GoogleLogin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private ImageView logoImageView;
    
    @FXML
    private TextField mail;
    @FXML
    private PasswordField password;
    
    @FXML
    private Button btnLogin;       // fx:id="btnLogin"
    @FXML
    private Button btnLoginGoogle; // fx:id="btnLoginGoogle"

    @FXML
    public void initialize() {

        // Se carga el icono del logo
        Image logo = new Image(getClass().getResourceAsStream("/view/logo.png"));
        logoImageView.setImage(logo);
    }
    
    @FXML
    private void clickLogin(ActionEvent event) {
        String usuario = mail.getText();
        String pass = password.getText();
        if (usuario.equals("admin@gmail.com") && pass.equals("admin123")) {
            // Mostrar mensaje de acceso concedido
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Acceso concedido.");
            alert.showAndWait();
            try {
                // Cargar la ventana principal desde MainView.fxml
                Parent mainRoot = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
                Scene mainScene = new Scene(mainRoot);
                
                // Cerrar la ventana de login
                Stage loginStage = (Stage) mail.getScene().getWindow();
                loginStage.close();
                
                // Abrir la ventana principal
                Stage mainStage = new Stage();
                mainStage.setTitle("ID Manager - Principal");
                mainStage.setScene(mainScene);
                String iconPath = "src/main/resources/view/logo.png";  // Ajusta la ruta si es necesario
                Image icon = new Image("file:" + iconPath);
                mainStage.getIcons().add(icon);
                mainStage.show();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Mostrar mensaje de error en caso de credenciales incorrectas
            Alert alert = new Alert(Alert.AlertType.ERROR, "Credenciales incorrectas.");
            alert.showAndWait();
        }
    }

    
    // Metodo para login con Google 
    @FXML
    private void clickLoginGoogle(ActionEvent event) {
        
        try {
            // Inicializar el transporte HTTP necesario para el flujo OAuth2
            final NetHttpTransport HTTP_TRANSPORT = new com.google.api.client.http.javanet.NetHttpTransport();
            // Iniciar el proceso de autenticación
            GoogleLogin.getCredentials(HTTP_TRANSPORT);

            // Si la autenticación es exitosa, se puede proceder a abrir la ventana principal y cerrar login
            try {
                Parent mainRoot = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
                Scene mainScene = new Scene(mainRoot);
                Stage mainStage = new Stage();
                mainStage.setTitle("Ventana Principal");
                mainStage.setScene(mainScene);
                String iconPath = "src/main/resources/view/logo.png";  // Ajusta la ruta si es necesario
                Image icon = new Image("file:" + iconPath);
                mainStage.getIcons().add(icon);
                mainStage.show();
                
                // Cierra la ventana de login
                Stage loginStage = (Stage) btnLogin.getScene().getWindow();
                loginStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error en la autenticación con Google: " + ex.getMessage() );
            alert.showAndWait();
        }
    }
}
