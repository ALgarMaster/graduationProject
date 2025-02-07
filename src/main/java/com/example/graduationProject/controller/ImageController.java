package com.example.graduationProject.controller;


import com.example.graduationProject.entities.Images;
import com.example.graduationProject.repository.ImagesRepository;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Slf4j
@Controller
public class ImageController {

    @Value("${file.upload-dir}")
    private String rootDirectory = "C:\\images";  // Директория для загрузки файлов

    @Autowired
    private ImagesRepository imgRepository;

    // Конструктор для внедрения зависимостей
    public ImageController(ImagesRepository imgRepository) {
        this.imgRepository = imgRepository;
    }

    // Загрузка изображения
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("albumId") int albumId) throws IOException {

        if (rootDirectory == null) {
            throw new IllegalStateException("rootDirectory is not set. Please configure the root directory.");
        }

        // Генерация уникального имени для файла
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // Путь на диске для сохранения изображения
        Path path = Paths.get(rootDirectory, fileName);

        // Сохраняем файл на диске
        Files.copy(file.getInputStream(), path);

        // Сохраняем информацию о файле в базе данных
        Images image = new Images(fileName, path.toString(), albumId);
        imgRepository.save(image);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Image uploaded successfully with file name: " + fileName);
    }

    // Получение изображения по имени
    @GetMapping("/view/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {

        Path path = Paths.get(rootDirectory, imageName);
        File file = path.toFile();

        if (file.exists()) {
            byte[] imageBytes = Files.readAllBytes(path);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)  // Тип содержимого (можно изменить на PNG или другой формат)
                    .body(imageBytes);
        } else {
            return ResponseEntity.notFound().build();  // Возвращаем 404, если файл не найден
        }
    }

//    // Получение всех изображений для конкретного альбома
//    @GetMapping("/album/{albumId}")
//    public ResponseEntity<List<Images>> getImagesByAlbumId(@PathVariable int albumId) {
//        List<Images> images = imgRepository.findById_album(albumId);
//        if (images.isEmpty()) {
//            return ResponseEntity.notFound().build();  // Если изображений нет для данного альбома
//        }
//        return ResponseEntity.ok(images);  // Возвращаем список изображений для альбома
//    }

}
