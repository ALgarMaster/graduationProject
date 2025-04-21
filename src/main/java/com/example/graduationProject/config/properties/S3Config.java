package com.example.graduationProject.config.properties;

import io.github.cdimascio.dotenv.Dotenv;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

public class S3Config {

    public static S3Client createClient() {

        AwsBasicCredentials credentials = AwsBasicCredentials.create(
                "$ACCESS_KEY",
                "$SECRET_KEY"
        );

        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.of("$REG")) // Например: "ru-central" или "us-east-1"
                .endpointOverride(URI.create("https://s3.yandexcloud.net")) // Например: "https://s3.twcstorage.ru"
                .serviceConfiguration(
                        S3Configuration.builder()
                                .pathStyleAccessEnabled(true) // <-- Включаем путь-стиль доступа
                                .build()
                )
                .httpClient(UrlConnectionHttpClient.create())
                .build();
    }
}
