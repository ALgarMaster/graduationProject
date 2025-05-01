package com.example.graduationProject.controller;

import com.example.graduationProject.config.properties.S3Config;
import com.example.graduationProject.service.TelegramBot;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class S3StorageService {

    private static final Logger log = LoggerFactory.getLogger(TelegramBot.class);

    private final S3Client s3Client = S3Config.createClient(); // ← вот тут мы используем твой конфиг
    private final String bucketName = System.getenv("BUCKET_NAME"); // можно через Dotenv или System.getenv
    private final String endpointUrl = "https://storage.yandexcloud.net/";

    public void uploadFile(String fileName, InputStream inputStream, long contentLength, String contentType) {
        String safeFileName = fileName.replaceAll("[^a-zA-Z0-9_\\-\\.]", "_");

        log.info("Attempting to upload to S3: bucket={}, fileName={}, contentType={}, contentLength={}",
                bucketName, safeFileName, contentType, contentLength);

        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(safeFileName)
                .contentType(contentType)
//                .acl(ObjectCannedACL.PUBLIC_READ) // ← делаем файл публичным
                .build();

        log.info("S3 upload: bucket={}, key={}, contentType={}", bucketName, safeFileName, contentType);

        try {
            s3Client.putObject(putRequest, RequestBody.fromInputStream(inputStream, contentLength));
            log.info("✅ Upload successful: {}", safeFileName);
        } catch (S3Exception e) {
            log.error("❌ Failed to upload file to S3 (code={}): {}", e.statusCode(), e.awsErrorDetails().errorMessage());
            throw e;
        } catch (Exception e) {
            log.error("❌ Unexpected error while uploading to S3: {}", e.getMessage(), e);
            throw new RuntimeException("S3 upload failed", e);
        }
    }


    public boolean checkConnection() {
        try {
            HeadBucketRequest headBucketRequest = HeadBucketRequest.builder()
                    .bucket(bucketName)
                    .build();

            // Проверка доступности бакета
            s3Client.headBucket(headBucketRequest);
            log.info("✅ Успешное подключение к S3 и бакет доступен: {}", bucketName);
            return true;
        } catch (NoSuchBucketException e) {
            log.error("❌ Бакет не существует: {}", bucketName);
        } catch (S3Exception e) {
            log.error("❌ Ошибка доступа к S3: {}", e.awsErrorDetails().errorMessage());
        } catch (Exception e) {
            log.error("❌ Неизвестная ошибка подключения к S3: {}", e.getMessage(), e);
        }
        return false;
    }

    public String getFileUrl(String fileName) {
        return endpointUrl + "/" + bucketName + "/" + fileName;
    }

    public List<String> listFiles() {
        ListObjectsV2Response response = s3Client.listObjectsV2(ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build());

        return response.contents().stream()
                .map(S3Object::key)
                .collect(Collectors.toList());
    }

    public InputStream getFile(String key) {
        GetObjectRequest getRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        return s3Client.getObject(getRequest);
    }
}
