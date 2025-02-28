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
                "El boton crear te permite crear empresas, para ver sus datos asociados haz doble click en su nombre",
                "Para volver a esta vista haz doble click en ID MANAGER",
                "Para editar informacion de empleados haz doble click en la casilla a editar");
        
    }
}
