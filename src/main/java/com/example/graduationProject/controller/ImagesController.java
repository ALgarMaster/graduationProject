package com.example.graduationProject.controller;


import com.example.graduationProject.entities.Images;
import com.example.graduationProject.repository.ImagesRepository;
import com.example.graduationProject.service.ImagesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ImagesController {

//    @Value("${file.upload-dir}")
    private String rootDirectory = "C:\\images";  // Директория для загрузки файлов
    private final ImagesService imagesService;


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
        imagesService.saveImage(image);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Image uploaded successfully with file name: " + fileName);
    }

    // Получение изображения по имени
    public File getImage(int id) {
        // Извлечение изображения по ID
        Optional<Images> imageOpt = imagesService.getImageById(id);
        Images image = imageOpt.orElseThrow(() -> new IllegalArgumentException("Image not found"));

        // Получаем путь к файлу
        Path path = Paths.get(image.getFileSrc());

        // Проверяем, существует ли файл
        if (!Files.exists(path)) {
            throw new IllegalStateException("File not found");
        }

        // Возвращаем файл
        return path.toFile();
    }


    // Получение всех изображений для по альбому в виде списка файлов
    public List<File> getImagesByAlbumId(int albumId) {
        ArrayList<Images> imagesList = new ArrayList<>(imagesService.getImagesByIdAlbum(albumId));

        if (imagesList.isEmpty()) {
            throw new RuntimeException("The array list of images is empty"); // Если изображений нет для данного альбома
        }
        // Получаем путь к файлу


        List<File> list = new ArrayList<>();

        //нужно теперь заполнить  list из imagesList с помощью метода getImage
        imagesList.forEach(image -> {
            Path path = Paths.get(image.getFileSrc());
            list.add(path.toFile());
        } );

        if (list.isEmpty()) {
            throw new RuntimeException("The array list of images is empty"); // Если изображений нет для данного альбома
        }

        return list;  // Возвращаем список изображений для альбома
    }

}
