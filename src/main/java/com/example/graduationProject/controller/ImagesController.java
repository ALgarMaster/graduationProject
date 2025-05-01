package com.example.graduationProject.controller;


import com.example.graduationProject.entities.Images;
import com.example.graduationProject.repository.ImagesRepository;
import com.example.graduationProject.service.ImagesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ImagesController {

//    @Value("${file.upload-dir}")
    private String rootDirectory = "C:\\images";
    private final ImagesService imagesService;
    private final S3StorageService s3StorageService;


    @GetMapping("/by-id/{id}")
    public ResponseEntity<byte[]> getImageByIdS3(@PathVariable int id) {
        try {
            Optional<Images> imageOpt = imagesService.getImageById(id);
            Images image = imageOpt.orElseThrow(() -> new IllegalArgumentException("Image not found"));

            String fileName = image.getFileName(); // предполагается, что в поле fileName лежит имя файла в S3
            InputStream s3InputStream = s3StorageService.getFile(fileName);

            byte[] imageBytes = s3InputStream.readAllBytes(); // Java 9+
            s3InputStream.close();

            // Пробуем определить тип контента (опционально — можно также сохранить тип в БД)
            String contentType = Files.probeContentType(Paths.get(fileName));
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(imageBytes);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Ошибка при получении изображения из S3", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @PostMapping("/upload")
    public ResponseEntity<String> uploadImageToS3(@RequestParam("file") MultipartFile file,
                                              @RequestParam("albumId") int albumId) {


        try {
            // Генерация уникального имени файла
            String fileName = System.currentTimeMillis()+"";

            String contentType = file.getContentType();

            if (fileName.endsWith(".png")) {
                contentType = "image/png";
            } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
                contentType = "image/jpeg";
            } else if (fileName.endsWith(".webp")) {
                contentType = "image/webp";
            }

            s3StorageService.uploadFile(
                    fileName,
                    file.getInputStream(),
                    file.getSize()
                    , contentType
            );


            // Генерация публичного URL
            String fileUrl = s3StorageService.getFileUrl(fileName);

            // Сохранение информации в БД
            Images image = new Images(fileName, fileUrl, albumId);
            imagesService.saveImage(image);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Image uploaded successfully: " + fileUrl);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading file: " + e.getMessage());
        }
    }

    public ResponseEntity<String> uploadImage( MultipartFile file,  int albumId) throws IOException {

        if (rootDirectory == null) {
            throw new IllegalStateException("rootDirectory is not set. Please configure the root directory.");
        }


        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();


        Path path = Paths.get(rootDirectory, fileName);


        Files.copy(file.getInputStream(), path);


        Images image = new Images(fileName, path.toString(), albumId);
        imagesService.saveImage(image);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Image uploaded successfully with file name: " + fileName);
    }


    public File getImage(int id) {

        Optional<Images> imageOpt = imagesService.getImageById(id);
        Images image = imageOpt.orElseThrow(() -> new IllegalArgumentException("Image not found"));


        Path path = Paths.get(image.getFileSrc());


        if (!Files.exists(path)) {
            throw new IllegalStateException("File not found");
        }


        return path.toFile();
    }


    // Получение всех изображений для по альбому в виде списка файлов
    public List<File> getImagesByAlbumId(int albumId) {
        ArrayList<Images> imagesList = new ArrayList<>(imagesService.getImagesByIdAlbum(albumId));

        if (imagesList.isEmpty()) {
            throw new RuntimeException("The array list of images is empty");
        }



        List<File> list = new ArrayList<>();


        imagesList.forEach(image -> {
            Path path = Paths.get(image.getFileSrc());
            list.add(path.toFile());
        } );

        if (list.isEmpty()) {
            throw new RuntimeException("The array list of images is empty");
        }

        return list;
    }

    public List<byte[]> getImagesByAlbumIdS3(int albumId) {
        List<Images> imagesList = imagesService.getImagesByIdAlbum(albumId);
        if (imagesList.isEmpty()) {
            log.warn("No images found in DB for albumId: {}", albumId);
            throw new RuntimeException("The image list is empty");
        }

        List<byte[]> imageBytesList = new ArrayList<>();

        for (Images image : imagesList) {
            String key = image.getFileName();
            if (key == null || key.isBlank()) {
                log.warn("Image with id={} has empty S3 key", image.getIdImage());
                continue;
            }

            try (InputStream inputStream = s3StorageService.getFile(key)) {
                byte[] imageBytes = inputStream.readAllBytes();
                imageBytesList.add(imageBytes);
            } catch (Exception e) {
                log.error("Failed to load image from S3 with key: {}", key, e);
            }
        }

        if (imageBytesList.isEmpty()) {
            log.error("No images could be loaded from S3 for albumId: {}", albumId);
            throw new RuntimeException("The image list is empty");
        }

        return imageBytesList;
    }

    public List<String> getImagesURLByAlbumIdS3(int albumId) {
        List<Images> imagesList = imagesService.getImagesByIdAlbum(albumId);

        if (imagesList.isEmpty()) {
            log.warn("No images found in DB for albumId: {}", albumId);
            throw new RuntimeException("The image list is empty");
        }

        List<String> imageUrls = new ArrayList<>();

        for (Images image : imagesList) {
            String key = image.getFileName();
            if (key == null || key.isBlank()) {
                log.warn("Image with id={} has empty S3 key", image.getIdImage());
                continue;
            }

            try {
                String fileUrl = s3StorageService.getFileUrl(key);
                imageUrls.add(fileUrl);
            } catch (Exception e) {
                log.error("Failed to generate URL for S3 key: {}", key, e);
            }
        }

        if (imageUrls.isEmpty()) {
            log.error("No image URLs could be generated from S3 for albumId: {}", albumId);
            throw new RuntimeException("The image URL list is empty");
        }

        return imageUrls;
    }


    public List<Integer> getImagesFileNameByAlbumIdWithUrl(int id){
        List<Images> imagesList = new ArrayList<>(imagesService.getImagesByIdAlbum(id));
        List<Integer> data = new ArrayList<>();

        imagesList.forEach(images -> data.add( images.getIdImage()));

        return data;
    }




}
