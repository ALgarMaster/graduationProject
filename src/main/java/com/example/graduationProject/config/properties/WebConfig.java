package com.example.graduationProject.config.properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Разрешаем все запросы с указанного источника
        registry.addMapping("/**")
                .allowedOrigins("http://192.168.1.228:5173") // Разрешаем доступ с фронтенда
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Разрешаем методы
                .allowedHeaders("*") // Разрешаем все заголовки
                .allowCredentials(true); // Разрешаем отправку куки
    }
}