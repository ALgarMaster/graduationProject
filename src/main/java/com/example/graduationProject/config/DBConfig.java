package com.example.graduationProject.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
@Slf4j
@Configuration
public class DBConfig {

    private static final String URL = "jdbc:postgresql://localhost:5432/vintageBot";

    private static final String USER = "postgres";

    private static final String PASSWORD = "1111";

    // Метод для получения подключения
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public boolean isConnectionValid() {
        try (Connection connection = getConnection()) {
            // Если соединение открыто без ошибок, то считаем его валидным
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            log.error("Error DBConfig" + e.getMessage());
            return false; // В случае ошибки подключения возвращаем false
        }
    }



}
