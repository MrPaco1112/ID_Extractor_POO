package com.mycompany.idmanager;

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Empresa {
    private String nombreEmp;
    private String eps;
    private String caja;
    private String arl;
    private Boolean archivada;
    private ArrayList<Persona> empleados = new ArrayList<>();
    
    // Constructor
    public Empresa(String nombreEmp, String eps, String caja, String arl, boolean archivada, ArrayList<Persona> empleados) {
        this.nombreEmp = nombreEmp;
        this.eps = eps;
        this.caja = caja;
        this.arl = arl;
        this.archivada = false;
        this.empleados = (empleados != null) ? empleados : new ArrayList<>();
    }
    
    // Constructor vacío para Firebase 
    public Empresa() {
    }

    // Getters y Setters
    public String getNombreEmp() {
        return nombreEmp;
    }

    public void setNombreEmp(String nombreEmp) {
        this.nombreEmp = nombreEmp;
    }

    public String getEps() {
        return eps;
    }

    public void setEps(String eps) {
        this.eps = eps;
    }
    public String getCaja() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja = caja;
    }

    public String getArl() {
        return arl;
    }

    public void setArl(String arl) {
        this.arl = arl;
    }

    public Boolean getArchivada() {
        return archivada;
    }

    public void setArchivada(Boolean archivada) {
        this.archivada = archivada;
    }
    public ArrayList<Persona> getEmpleados() {
        return empleados;
    }
    
    public void agregarEmpleado(Persona empleado) {
        empleados.add(empleado);
    }    
    public void setArray(ArrayList<Persona> empleados){
        this.empleados = (empleados != null) ? empleados : new ArrayList<>();
    }
    
    //Para que se pueda leer el objeto para subir
    @Override
    public String toString() {
        return "Empresa{" +
                "nombre empresa='" + nombreEmp + '\'' +
                ", EPS=" + eps +
                ", Caja=" + caja +
                ", ARL='" + arl + '\'' +
                ", Estado de archivamiento='" + archivada + '\'' +
                '}';
    }
    
// Metodo para recolectar los datos de la empresa al crear la empresa
    public static Empresa crearEmpresa(){
        
        Empresa empresa = new Empresa();
        
        Carpeta Archivador = new Carpeta();
        
        JTextField nombreField = new JTextField(10);
        JTextField epsField = new JTextField(10);
        JTextField cajaField = new JTextField(10);
        JTextField arlField = new JTextField(10);
        

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("EPS:"));
        panel.add(epsField);
        panel.add(new JLabel("Caja:"));
        panel.add(cajaField);
        panel.add(new JLabel("ARL:"));
        panel.add(arlField);
        

        int result = JOptionPane.showConfirmDialog(null, panel, 
                 "Ingrese los datos de la empresa: ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            empresa.setNombreEmp(nombreField.getText());
            empresa.setEps(epsField.getText());
            empresa.setCaja(cajaField.getText());
            empresa.setArl(arlField.getText());
            empresa.setArchivada(false);
            
            Archivador.CrearCarpeta(nombreField.getText());
            
        }
        return empresa;
    }
}
