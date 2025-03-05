package view;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mycompany.idmanager.AsistenteFirebase;
import com.mycompany.idmanager.Carpeta;
import com.mycompany.idmanager.Empresa;
import com.mycompany.idmanager.OCRManager;
import com.mycompany.idmanager.Persona;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

public class CompanyDetailsController {
    AsistenteFirebase manager = new AsistenteFirebase();
    
    private DatabaseReference ref;
    
    Carpeta Archivador = new Carpeta();
    
    @FXML
    private Label lblEmpresa;
    @FXML
    private Label lblEPS;
    @FXML
    private Label lblCaja;
    @FXML
    private Label lblARL;
    
    @FXML
    private  TableView<Persona> workerTableView;
    @FXML
    private TableColumn<Persona, String> colNombre;
    
    @FXML
    private TableColumn<Persona, String> colEdad;
    @FXML
    private TableColumn<Persona, String> colId;
    @FXML
    private TableColumn<Persona, String> colCorreo;
    @FXML
    private TableColumn<Persona, String> colDireccion;
    

    @FXML
    private Button btnEliminar, btnActualizar, btnCrear, btnEscanear;
    
    String em;
    ObservableList<Persona> trabajadores = FXCollections.observableArrayList();
    Empresa emp = new Empresa();
    @FXML
    public void initialize() {

        
        workerTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        workerTableView.setItems(trabajadores);
        
    }
    
        @FXML
    private void handleEscanear(ActionEvent event) throws InterruptedException {

         // Se llama a OCRManager para abrir JFileChooser y procesar la imagen
        String texto = OCRManager.leerTextoDesdeImagen();
        System.out.println("Texto OCR: " + texto);

        // Extraer datos de la cédula
        OCRManager.DatosCedula datos = OCRManager.extraerDatosCedula(texto);
        File foto = OCRManager.getFoto();
        // Mostrar el resultado en un cuadro de diálogo
        String mensaje = "Texto OCR:\n" + texto
                + "\n\nNUIP: " + datos.getNuip()
                + "\nFecha Nac.: " + datos.getFechaNacimiento()
                + "\nNombre: " + datos.getNombre();
        Persona empleadoOCR = new Persona(datos.getNombre(),datos.getFechaNacimiento(),datos.getNuip(),"","");
        Alert alert = new Alert(Alert.AlertType.INFORMATION, mensaje);
        alert.setTitle("Resultado del Escaneo");
        alert.setHeaderText("Datos extraídos de la imagen");
        alert.showAndWait();
        
        System.out.println("Crear nuevo trabajador en empresa " + emp.getNombreEmp());
        agregarPersona(empleadoOCR);
        
        Empresa empresaTrabajador = AsistenteFirebase.obtenerEmpresaPorNombre(em);
        
        // Se crea una carpeta dentro de la carpeta de la empresa correspondiente
        Archivador.CrearCarpeta(em+"\\"+empleadoOCR.getNombre());
        actualizarTabla(empresaTrabajador);
        initTable();
        Archivador.CrearArchivo(em+"\\"+datos.getNombre(),foto);
    }


    // Este método se invoca desde MainController al cargar la vista de la empresa.
    // Para mostrar los atributos de la empresa en la interfaz
    
    public void setEmpresa(Empresa empresa) throws InterruptedException {
        if (empresa != null) {
            lblEmpresa.setText("Detalles de la empresa: " + empresa.getNombreEmp());
            lblEPS.setText("EPS:" + empresa.getEps());
            lblCaja.setText("Caja: " +empresa.getCaja());
            lblARL.setText("ARL: " +empresa.getArl());
            em = empresa.getNombreEmp();
            
            refrescarTabla(empresa);
            emp = empresa;
            
            
        }
    }

    @FXML
    private void handleEliminar(ActionEvent event) {
        System.out.println("Eliminar trabajador en empresa " + em);
        Persona personaSeleccionada = workerTableView.getSelectionModel().getSelectedItem();
    
        if (personaSeleccionada != null) {
            
            try {
                Archivador.BorrarCarpeta(em+"\\"+personaSeleccionada.getNombre());
            }catch(Exception ex){
            }
            
            trabajadores.remove(personaSeleccionada);
            try {
                String clave = AsistenteFirebase.obtenerClavePorNombre(manager.firebaseDatabase,em);
                actualizarTabla(AsistenteFirebase.obtenerInfo(manager.firebaseDatabase, "empresas", clave));
            } catch (InterruptedException ex) {
                Logger.getLogger(CompanyDetailsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
        System.out.println("No se ha seleccionado ningún trabajador.");
        }
    }

    @FXML
    private void handleActualizar(ActionEvent event) throws InterruptedException {
        System.out.println("Actualizar trabajador en empresa " + emp.getNombreEmp());
        AsistenteFirebase.bajarListaFireBase();
        setEmpresa(emp);
    }

    @FXML
    private void handleCrear(ActionEvent event) throws InterruptedException {
        System.out.println("Crear nuevo trabajador en empresa " + emp.getNombreEmp());
        Persona nuevoEmpleado = Persona.crearEmpleado();
        agregarPersona(nuevoEmpleado);
        Empresa empresaTrabajador = AsistenteFirebase.obtenerEmpresaPorNombre(em);
        Archivador.CrearCarpeta(em+"\\"+nuevoEmpleado.getNombre());
        actualizarTabla(empresaTrabajador);
        initTable();
    }

    public void agregarPersona(Persona persona) {
        trabajadores.add(persona);
    }
    public void initTable() throws InterruptedException {
        // Se permite que la tabla sea editable
        workerTableView.setEditable(true);
        String clave = AsistenteFirebase.obtenerClavePorNombre(manager.firebaseDatabase,em);
        // Se Configura la columna de nombre para edición del objeto Persona
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colNombre.setCellFactory(TextFieldTableCell.forTableColumn());
        colNombre.setOnEditCommit(event -> {
            Persona empleado = event.getRowValue(); // Obtiene el objeto Persona de la fila
            empleado.setNombre(event.getNewValue()); // Actualiza el nombre con el nuevo valor
            //emp.agregarEmpleado(empleado);
            AsistenteFirebase.actualizarInfo(manager.firebaseDatabase, "empresas", clave, emp);
        });

        // Se configura columna de edad para edición
        colEdad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEdad()));
        colEdad.setCellFactory(TextFieldTableCell.forTableColumn());
        colEdad.setOnEditCommit(event -> {
            Persona empleado = event.getRowValue();
            empleado.setEdad(event.getNewValue());
            //emp.agregarEmpleado(empleado);
            AsistenteFirebase.actualizarInfo(manager.firebaseDatabase, "empresas", clave, emp);
        });

        // Se configura columna de ID 
        colId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colId.setCellFactory(TextFieldTableCell.forTableColumn());
        colId.setOnEditCommit(event -> {
            Persona empleado = event.getRowValue();
            empleado.setId(event.getNewValue());
            //emp.agregarEmpleado(empleado);
            AsistenteFirebase.actualizarInfo(manager.firebaseDatabase, "empresas", clave, emp);
        });

        // Se configura columna de correo para edición
        colCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCorreo()));
        colCorreo.setCellFactory(TextFieldTableCell.forTableColumn());
        colCorreo.setOnEditCommit(event -> {
            Persona empleado = event.getRowValue();
            empleado.setCorreo(event.getNewValue());
            //emp.agregarEmpleado(empleado);
            AsistenteFirebase.actualizarInfo(manager.firebaseDatabase, "empresas", clave, emp);
        });

        // Se configura columna de dirección para edición
        colDireccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion()));
        colDireccion.setCellFactory(TextFieldTableCell.forTableColumn());
        colDireccion.setOnEditCommit(event -> {
            Persona empleado = event.getRowValue();
            empleado.setDireccion(event.getNewValue());
            //emp.agregarEmpleado(empleado);
            AsistenteFirebase.actualizarInfo(manager.firebaseDatabase, "empresas", clave, emp);
        });

        // Se asigna la lista de trabajadores a la tabla
        workerTableView.setItems(trabajadores);
    }

    public void actualizarTabla(Empresa nuevoValor) throws InterruptedException{
        
        
    // Convertir la lista de la tabla a un ArrayList para Firebase
        ArrayList<Persona> listaActualizada = new ArrayList<>(workerTableView.getItems());
        
        nuevoValor.setArray(listaActualizada);
        String nombreEmp = nuevoValor.getNombreEmp();
        String empresaId = AsistenteFirebase.obtenerClavePorNombre(manager.firebaseDatabase,nombreEmp);
        AsistenteFirebase.actualizarInfo(manager.firebaseDatabase, "empresas", empresaId, nuevoValor);
    }
    public void refrescarTabla(Empresa empresa) throws InterruptedException{
        List<Persona> empleados = empresa.getEmpleados();
        trabajadores.clear();
        trabajadores.addAll(empleados);
        initTable();
    }
}
