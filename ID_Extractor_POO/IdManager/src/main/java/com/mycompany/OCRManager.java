package com.mycompany;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OCRManager {
    
    // Clase para almacenar los datos extraídos
    public static class DatosCedula {
        private String nuip;
        private String fechaNacimiento;
        private String nombre;

        public DatosCedula(String nuip, String fechaNacimiento, String nombre) {
            this.nuip = nuip;
            this.fechaNacimiento = fechaNacimiento;
            this.nombre = nombre;
        }

        // Getters
        public String getNuip() { return nuip; }
        public String getFechaNacimiento() { return fechaNacimiento; }
        public String getNombre() { return nombre; }
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
        
        // 2. Configurar Tesseract
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("tessdata"); // Ruta de los archivos .traineddata
        tesseract.setLanguage("spa"); // Idioma (spa = español)
        
        try {
            // 3. Procesar imagen y extraer texto
            String texto = tesseract.doOCR(imagen);
            return texto;
        } catch (TesseractException e) {
            return "Error al leer la imagen: " + e.getMessage();
        }
        
    }

    // Nuevo método para procesar el texto OCR y extraer datos
    public static DatosCedula extraerDatosCedula(String textoOCR) {
        String[] lineas = textoOCR.split("\n");
        String nuip = "";
        String fechaNacimiento = "";
        String nombre = "";

        // Patrones de búsqueda
        Pattern patronNUIP = Pattern.compile("NUIP([0-9.]+)");
        Pattern patronFecha = Pattern.compile("\\b(\\d{2}[A-Z]{3})\\s?(\\d{4})\\b");

        for (String linea : lineas) {
            // Buscar NUIP
            Matcher matcherNUIP = patronNUIP.matcher(linea);
            if (matcherNUIP.find()) {
                nuip = matcherNUIP.group(1).trim();
            }

            // Buscar Fecha de Nacimiento
            if (linea.contains("Fecha de nacimiento")) {
                Matcher matcherFecha = patronFecha.matcher(linea);
                if (matcherFecha.find()) {
                    fechaNacimiento = matcherFecha.group(1) + " " + matcherFecha.group(2);
                }
            }

            // Buscar Nombre (formato con <)
            if (linea.matches(".*[A-Z]+<[A-Z]+<<[A-Z]+<[A-Z]+.*")) {
                String[] partes = linea.split("<+");
                List<String> partesLimpias = new ArrayList<>();
                
                for (String parte : partes) {
                    String parteLimpia = parte.replaceAll("[^A-Z]", "");
                    if (!parteLimpia.isEmpty()) {
                        partesLimpias.add(parteLimpia);
                    }
                }
                
                if (partesLimpias.size() >= 4) {
                    nombre = String.join(" ", partesLimpias);
                }
            }
        }

        return new DatosCedula(nuip, fechaNacimiento, nombre);
    }

    // Método main de prueba
    public static void main(String[] args) {
        String textoOCR = leerTextoDesdeImagen();
        
        if (!textoOCR.startsWith("Error") && !textoOCR.equals("Operación cancelada")) {
            DatosCedula datos = extraerDatosCedula(textoOCR);
            
            System.out.println("=== Datos Extraídos ===");
            System.out.println("NUIP: " + datos.getNuip());
            System.out.println("Fecha Nacimiento: " + datos.getFechaNacimiento());
            System.out.println("Nombre: " + datos.getNombre());
        } else {
            System.out.println(textoOCR);
        }
    }
}
