package com.mva.api.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@Slf4j
public class FirebaseConfig {

    @PostConstruct
    public void init() {
        try {
            ClassPathResource classPathResource = new ClassPathResource("config/firebaseKey.json");
            File firebaseKeyFile = Paths.get(classPathResource.getURI()).toFile();

            FileInputStream serviceAccount = new FileInputStream(firebaseKeyFile);
            FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
            FirebaseApp.initializeApp(options);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Failed to initialize Firebase, error: " + e.getMessage());
        }
    }
}