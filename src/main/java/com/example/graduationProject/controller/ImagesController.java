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


    @GetMapping("/by-id/{id}")
    public ResponseEntity<Resource> getImageById(@PathVariable int id) {
        try {

            Optional<Images> imageOpt = imagesService.getImageById(id);
            Images image = imageOpt.orElseThrow(() -> new IllegalArgumentException("Image not found"));


            Path path = Paths.get(image.getFileSrc());


            if (!Files.exists(path)) {
                return ResponseEntity.notFound().build();
            }


            Resource resource = new UrlResource(path.toUri());


            String contentType = Files.probeContentType(path);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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

    public List<Integer> getImagesFileNameByAlbumIdWithUrl(int id){
        List<Images> imagesList = new ArrayList<>(imagesService.getImagesByIdAlbum(id));
        List<Integer> data = new ArrayList<>();

        imagesList.forEach(images -> data.add( images.getIdImage()));

        return data;
    }

}
