package com.mva.api;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();  // .env 파일 로드
        System.setProperty("GEMINI_API_KEY", dotenv.get("GEMINI_API_KEY"));
        System.setProperty("GOOGLE_IMAGE_API_KEY", dotenv.get("GOOGLE_IMAGE_API_KEY"));
        System.setProperty("GOOGLE_IMAGE_ENGINE_ID", dotenv.get("GOOGLE_IMAGE_ENGINE_ID"));

        SpringApplication.run(ApiApplication.class, args);
    }
    // todo db insert -> 추후 스케줄링으로 전환
}
