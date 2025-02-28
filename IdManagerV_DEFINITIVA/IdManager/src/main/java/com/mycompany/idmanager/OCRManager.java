
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
        // 1. Seleccionar archivo con JFileChooser
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Imágenes", "jpg", "jpeg", "png", "bmp", "gif"
        );
        fileChooser.setFileFilter(filter);
        
        int resultado = fileChooser.showOpenDialog(null);
        if (resultado != JFileChooser.APPROVE_OPTION) {
            return "Operación cancelada";
        }
        
        File imagen = fileChooser.getSelectedFile();
        foto = imagen;
        
        // 2. Copiar la imagen seleccionada a la carpeta resources del proyecto Maven
        // Ajusta la ruta de destino según la estructura de tu proyecto.
        File destFile = new File("src/main/resources/ccFondoBlanco.jpg");
        try {
            Files.copy(imagen.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Imagen copiada a: " + destFile.getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
            return "Error al copiar la imagen: " + ex.getMessage();
        }
        
        
        // 3. Ejecutar el script Python "cc.py" ubicado en la carpeta resources.
        // Asegúrate de que "python" esté en el PATH o usa la ruta completa al intérprete de Python.
        try {
            ProcessBuilder pb = new ProcessBuilder("python", "src/main/resources/OCR.py");
            // Opcional: establecer el directorio de trabajo al directorio del proyecto.
            pb.directory(new File(System.getProperty("user.dir")));
            pb.redirectErrorStream(true);
            Process process = pb.start();
            
            // Leer la salida del script Python.
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
            return "Error al ejecutar cc.py: " + ex.getMessage();
        }


    }

    

    // Nuevo método para procesar el texto OCR y extraer datos
    public static DatosCedula extraerDatosCedula(String textoOCR) {
        // Dividimos el texto en líneas
        String[] lineas = textoOCR.split("\n");

        // Variables donde guardaremos cada dato
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