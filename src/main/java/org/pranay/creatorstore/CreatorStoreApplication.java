package org.pranay.creatorstore;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CreatorStoreApplication {

    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue()));

        System.out.println("URL: " + dotenv.get("DATABASE_URL"));
        System.out.println("USER: " + dotenv.get("DATABASE_USERNAME"));
        SpringApplication.run(CreatorStoreApplication.class, args);
    }
}