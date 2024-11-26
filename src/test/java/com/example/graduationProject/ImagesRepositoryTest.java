package com.example.graduationProject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootTest
public class ImagesRepositoryTest {

    @Autowired
    private ImagesRepository imagesRepository;

    public ImagesRepositoryTest(ImagesRepository imagesRepository) {
        this.imagesRepository = imagesRepository;
    }

    @Test
    public void testImagesRepositoryNotNull() {
        // Проверяем, что репозиторий не null
        assertNotNull(imagesRepository);
    }
}