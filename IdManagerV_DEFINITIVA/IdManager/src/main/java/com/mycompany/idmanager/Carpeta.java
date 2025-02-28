
package com.mycompany.idmanager;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Super PC 2
 */
public class Carpeta {
    static Path Camino;
    public static String CaminoCarpeta(){
       JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        int resultado = fileChooser.showOpenDialog(null);
        if (resultado != JFileChooser.APPROVE_OPTION) {
            return null;
        }
        
        File carpeta = fileChooser.getSelectedFile();
        Camino = Paths.get(carpeta.getPath());
        return carpeta.getPath();
    }
    
    public static void CrearCarpeta(String nuevaCarpeta){
        if (Camino == null){
            CaminoCarpeta();
        }
        Path ruta = Paths.get(Camino+"\\"+nuevaCarpeta);
        System.out.println(ruta);
        try {
            Files.createDirectories(ruta);
            System.out.println("Carpeta creada con éxito.");
        } catch (Exception e) {
            System.out.println("Error al crear la carpeta: " + e.getMessage());
        }
    }
    
    public static void BorrarCarpeta(String Carpeta) throws IOException{
        if (Camino == null){
            CaminoCarpeta();
        }
        System.out.println("eliminando...");
        Path ruta = Paths.get(Camino.toString()+"\\"+Carpeta);
        if (Files.exists(ruta)) {
            Files.walkFileTree(ruta, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file); // Elimina archivo
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir); // Elimina carpeta vacía
                    return FileVisitResult.CONTINUE;
                }
            });

            System.out.println("Carpeta eliminada correctamente.");
        } else {
            System.out.println("La carpeta no existe.");
        }
    }
    
    public static void CrearArchivo(String path,File archivo){
        if (Camino == null){
            CaminoCarpeta();
        }
        Path ruta = Paths.get(Camino+"\\"+path);
        System.out.println("Guardando imagen");
        try {
            Files.copy(archivo.toPath(), ruta, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.out.println("Error al crear la carpeta: " + e.getMessage());
        }
    }
    
    public static void Renombrar(String path,String nombre){
        if (Camino == null){
            CaminoCarpeta();
        }
        Path ruta = Paths.get(Camino+"\\"+path);
    }
}