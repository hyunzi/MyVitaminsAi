package com.mva.api.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
@Slf4j
public class FirebaseConfig {

    @PostConstruct
    public void init() {
        try {
            Dotenv dotenv = Dotenv.load();
            String firebaseConfig64 = dotenv.get("FIREBASE_CONFIG_BASE64");
            byte[] decodesBytes = Base64.getDecoder().decode(firebaseConfig64);
            String firebaseConfigJson = new String(decodesBytes);

            InputStream serviceAccount = new ByteArrayInputStream(firebaseConfigJson.getBytes(StandardCharsets.UTF_8));
            FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
            FirebaseApp.initializeApp(options);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Failed to initialize Firebase, error: " + e.getMessage());
        }
    }
}