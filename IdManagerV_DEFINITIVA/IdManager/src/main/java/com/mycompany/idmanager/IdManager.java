package com.mycompany.idmanager;

import javafx.application.Application;
import view.IdManagerApp;


public class IdManager {
    public static void main(String[] args) {
        
        System.out.println("idManager INICIADO CORRECTAMENTE!");
        
        // Inicializamos la conexión con Firebase
        AsistenteFirebase.initRTD();
        AsistenteFirebase.bajarListaFireBase();
        // Inicia la aplicación IdManagerApp, la cual abrirá LoginFX
        Application.launch(IdManagerApp.class, args);    
        
    }  
}

