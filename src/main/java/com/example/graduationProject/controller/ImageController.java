package com.example.graduationProject.controller;


import com.example.graduationProject.entities.Images;
import com.example.graduationProject.repository.ImageRepo;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j

public class ImageController {

    private ImageRepo imageRepository;

    @org.springframework.beans.factory.annotation.Value("${root.directory}")
    private String rootDirectory;


    public ImageController(ImageRepo imageRepository) {
        this.imageRepository = imageRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        // Генерация уникального имени для файла
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // Путь на диске для сохранения изображения
        Path path = Paths.get(rootDirectory, fileName);

        // Сохраняем файл на диске
        Files.copy(file.getInputStream(), path);

        // Сохраняем путь изображения в базе данных


        Images image = new Images(fileName,path.toString());
        imageRepository.save(image);

        // Генерация URL для доступа к изображению
        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/images/").path(fileName).toUriString();



        return ResponseEntity.ok(imageUrl);
    }

    // Метод для получения изображения по имени
    @GetMapping("/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {
        Path path = Paths.get(rootDirectory, imageName);
        File file = path.toFile();

        if (file.exists()) {
            byte[] imageBytes = Files.readAllBytes(path);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"")
                    .contentType(MediaType.IMAGE_JPEG)  // Если файл JPEG, можно изменить тип на другой (например, PNG)
                    .body(imageBytes);
        } else {
            return ResponseEntity.notFound().build();  // Возвращаем 404, если изображение не найдено
        }
    }

}
