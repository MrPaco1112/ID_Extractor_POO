package com.mycompany.idmanager;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Persona {
    private String nombre;
    private String edad;
    private String id;
    private String correo;
    private String direccion;

    // Constructor
    public Persona(String nombre, String edad, String id, String correo, String direccion) {
        this.nombre = nombre;
        this.edad = edad;
        this.id = id;
        this.correo = correo;
        this.direccion = direccion;
    }
    // Constructor vacío necesario para Firebase y deserialización
    public Persona() {
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    //Para que pueda leer el objeto para subir
    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", id=" + id +
                ", correo='" + correo + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
public static Persona crearEmpleado(){
        
        Persona persona = new Persona();
        
        JTextField nombreField = new JTextField(10);
        JTextField fechNacField = new JTextField(10);
        JTextField cedulaField = new JTextField(10);
        JTextField corrField = new JTextField(10);
        JTextField dirField = new JTextField(10);
        

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Fecha de Nacimiento:"));
        panel.add(fechNacField);
        panel.add(new JLabel("Cedula:"));
        panel.add(cedulaField);
        panel.add(new JLabel("Correo:"));
        panel.add(corrField);
        panel.add(new JLabel("Direccion:"));
        panel.add(dirField);
        

        int result = JOptionPane.showConfirmDialog(null, panel, 
                 "Ingrese los datos de la empresa: ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            persona.setNombre(nombreField.getText());
            persona.setEdad(fechNacField.getText());
            persona.setId(cedulaField.getText());
            persona.setCorreo(corrField.getText());
            persona.setDireccion(dirField.getText());
            
        }
        return persona;
    }
}
