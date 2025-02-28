package view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;


public class IdManagerApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Ruta del icono
        String iconPath = "src/main/resources/view/logo.png";  // Ajusta la ruta si es necesario
        // Cargar el icono
        Image icon = new Image("file:" + iconPath);
        // Establecer el icono en el Stage (ventana principal)
        primaryStage.getIcons().add(icon);
        // Carga tu FXML con ruta absoluta desde /view
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
        Scene scene = new Scene(root);

        // Carga tu CSS
        scene.getStylesheets().add(getClass().getResource("/view/styles.css").toExternalForm());

        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();

//==================================

        // Programamos la solicitud de foco para que, en vez de la raíz, se le asigne al botón con fx:id "btnLoginGoogle"
        Platform.runLater(() -> {
            Node btnLoginGoogle = scene.lookup("#btnLoginGoogle");
            if (btnLoginGoogle != null) {
                btnLoginGoogle.requestFocus();
            }
        });    

//==================================    

//==================================
        System.out.println("Application IdManagerApp INICIADO CORRECTAMENTE!");
    }
    
}
