package com.example.graduationProject.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("application.properties")
public class BotConfiguration {

    @Value("${bot.name}")
    String botName;

    @Value("${bot.token}")
    String token;

    public String getBotName() {
        return botName;
    }

    public String getToken() {
        return token;
    }

}
