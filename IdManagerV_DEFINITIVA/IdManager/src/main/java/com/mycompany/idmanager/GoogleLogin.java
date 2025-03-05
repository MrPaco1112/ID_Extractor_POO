package com.mycompany.idmanager;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class GoogleLogin {
    private static final String APPLICATION_NAME = "idManager";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    
    // Se crea una carpeta para almacenar las credenciales del usuario
    private static final java.io.File CREDENTIALS_FOLDER = new java.io.File(System.getProperty("user.home"), "credentials");

    // Se solicita acceso al email del usuario
    private static final List<String> SCOPES = Collections.singletonList("https://www.googleapis.com/auth/userinfo.email");

    private static final String CLIENT_SECRET_FILE_NAME = "/logingoogle-service.json";

    public static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Se carga  el archivo de credenciales
        InputStream in = GoogleLogin.class.getResourceAsStream(CLIENT_SECRET_FILE_NAME);
        if (in == null) {
            throw new IOException("No se encontró el archivo " + CLIENT_SECRET_FILE_NAME);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Se inicializa el flujo de autorización de google
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(CREDENTIALS_FOLDER))
                .setAccessType("offline")
                .build();
        
        System.out.println("Se ha iniciado sesion CORRECTAMENTE.");

        // Se configura el puerto local para recibir el código de autorización
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();

        // Se abre una ventana en el navegador para que el usuario se identifique
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        
    }
}
