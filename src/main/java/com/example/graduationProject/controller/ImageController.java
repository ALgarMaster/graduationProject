package com.example.graduationProject.controller;


import com.example.graduationProject.entities.Images;
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
    private String rootDirectory = "C:\\images";



    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {

        if (rootDirectory == null) {
            throw new IllegalStateException("rootDirectory is not set. Please configure the root directory.");
        }

        Configuration configuration = new Configuration();
        configuration.configure();

        // Генерация уникального имени для файла
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // Путь на диске для сохранения изображения
        Path path = Paths.get(rootDirectory, fileName);

        // Сохраняем файл на диске
        Files.copy(file.getInputStream(), path);

        // Сохраняем путь изображения в базе данных
        Images image = new Images(fileName,path.toString(),"0");

        try(var sessionFactory = configuration.buildSessionFactory();
            var session = sessionFactory.openSession();) {
            session.beginTransaction();
            session.save(image);
            log.info("Add Image name " + "engwioew");
            session.getTransaction().commit();
        }catch (Exception e){
            log.error(" "+ e.getStackTrace());
        }





        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Image uploaded successfully with file name: " + fileName);
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
