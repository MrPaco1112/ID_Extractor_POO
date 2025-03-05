package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.event.ActionEvent;

public class TasksController {

    @FXML
    private ListView<String> pendingTasksList;


    @FXML
    public void initialize() {
        // Datos de ejemplo
        pendingTasksList.getItems().addAll("Intrucciones:", 
                "El boton crear te permite crear empresas, para ver sus datos asociados haz doble click en el nombre de la empresa",
                "Para volver a la vista actual haz click en ID MANAGER",
                "Al crear una nueva empresa presiona el boton actualizar y dirigente a esta vista",
                "Para editar informacion de empleados haz doble click en la casilla a editar, al finalizar de editar presiona enter");

        
    }
}
