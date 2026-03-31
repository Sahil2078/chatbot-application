package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        // This command launches the Tomcat server and starts the Spring context
        SpringApplication.run(Main.class, args);

        System.out.println("Chatbot API is running on http://localhost:8081");
    }
}