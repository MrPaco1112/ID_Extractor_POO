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
        
        // Se carga y establece el icono en la ventana principal
        String iconPath = "src/main/resources/view/logo.png";
        Image icon = new Image("file:" + iconPath);
        primaryStage.getIcons().add(icon);
        
        // Se carga la ventana de login
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
        Scene scene = new Scene(root);

        // Se carga la hoja de estilos CSS
        scene.getStylesheets().add(getClass().getResource("/view/styles.css").toExternalForm());

        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Se le asigna el foco al botón con fx:id "btnLoginGoogle"
        Platform.runLater(() -> {
            Node btnLoginGoogle = scene.lookup("#btnLoginGoogle");
            if (btnLoginGoogle != null) {
                btnLoginGoogle.requestFocus();
            }
        });        

        System.out.println("Application IdManagerApp INICIADO CORRECTAMENTE!");
    }  
}
