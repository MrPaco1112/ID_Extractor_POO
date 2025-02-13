
package com.mycompany.idmanager;


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
}
