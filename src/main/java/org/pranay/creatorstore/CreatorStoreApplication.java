package org.pranay.creatorstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class CreatorStoreApplication {

    public static void main(String[] args) {

        // Debug - Print Railway Environment Variables
        System.out.println("DATABASE_URL = " + System.getenv("DATABASE_URL"));
        System.out.println("DATABASE_USERNAME = " + System.getenv("DATABASE_USERNAME"));
        System.out.println("DATABASE_PASSWORD = " + System.getenv("DATABASE_PASSWORD"));

        SpringApplication.run(CreatorStoreApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("*")
                        .allowedMethods("*");
            }
        };
    }
}