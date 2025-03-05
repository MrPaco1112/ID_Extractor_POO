
package com.mycompany.idmanager;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class OCRManager {
    private static File foto;
    
    // Clase para almacenar los datos extraídos
    public static class DatosCedula {
        private String nuip;
        private String fechaNacimiento;
        private String nombreCompleto;

        public DatosCedula(String nuip, String fechaNacimiento, String nombre) {
            this.nuip = nuip;
            this.fechaNacimiento = fechaNacimiento;
            this.nombreCompleto = nombre;
        }

        // Getters
        public String getNuip() { return nuip; }
        public String getFechaNacimiento() { return fechaNacimiento; }
        public String getNombre() { return nombreCompleto; }
    }

    public static File getFoto(){
        return foto;
    }
    
    
    
public static String leerTextoDesdeImagen() {
    
    // Se selecciona una imagen con JFileChooser
    JFileChooser fileChooser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "Imágenes", "jpg", "jpeg", "png"
    );
    fileChooser.setFileFilter(filter);

    int resultado = fileChooser.showOpenDialog(null);
    if (resultado != JFileChooser.APPROVE_OPTION) {
        return "Operación cancelada";
    }

    File imagen = fileChooser.getSelectedFile();
    foto = imagen;  // Guardamos la imagen seleccionada en la variable foto

    // Se ejecuta el script Python "OCR.py" pasandole la ruta de la imagen seleccionada
    try {
        // Usamos la ruta del archivo seleccionada por el usuario
        ProcessBuilder pb = new ProcessBuilder("python", "src/main/resources/OCR.py", imagen.getAbsolutePath());
        pb.directory(new File(System.getProperty("user.dir"))); // Establecer el directorio de trabajo
        pb.redirectErrorStream(true);
        Process process = pb.start();

        // Se lee la salida del script Python.
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        StringBuilder output = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        int exitCode = process.waitFor();
        System.out.println("El script Python finalizó con código: " + exitCode);
        return output.toString();
    } catch (IOException | InterruptedException ex) {
        ex.printStackTrace();
        return "Error al ejecutar OCR.py: " + ex.getMessage();
    }
}

    // Método para procesar el texto extraido por el OCR y extraer los datos
    public static DatosCedula extraerDatosCedula(String textoOCR) {
        // Dividimos el texto en líneas
        String[] lineas = textoOCR.split("\n");

        // Se definen las variables donde guardaremos cada dato
        String nuip = "";
        String apellidos = "";
        String nombre = "";
        String fechaNacimiento = "";

        // Recorremos cada línea buscando las etiquetas
        for (String linea : lineas) {
            if (linea.startsWith("ID:")) {
                nuip = linea.replace("ID:", "").trim();
            } else if (linea.startsWith("Apellidos:")) {
                apellidos = linea.replace("Apellidos:", "").trim();
            } else if (linea.startsWith("Nombre:")) {
                nombre = linea.replace("Nombre:", "").trim();
            } else if (linea.startsWith("Fecha de nacimiento:")) {
                fechaNacimiento = linea.replace("Fecha de nacimiento:", "").trim();
            }
        }

        // Unimos "Nombre + Apellidos" en una misma variable
        String nombreCompleto = nombre + " " + apellidos;

        // Retornamos el objeto con los datos extraídos
        return new DatosCedula(nuip, fechaNacimiento, nombreCompleto);
    }
  
}