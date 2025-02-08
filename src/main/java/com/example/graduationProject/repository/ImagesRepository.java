package com.example.graduationProject.repository;

import com.example.graduationProject.entities.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface ImagesRepository extends JpaRepository<Images, Integer> {

    Optional<Images> findById(int id);  // Автоматически реализуется

    Optional<Images> findByFileName(String fileName);  // Автоматически реализуется

//    List<Images> findById_album(int idAlbum);  // Используем стандартное имя для поиска по полю id_album

    // Методы для сохранения, удаления и обновления
    // save() и delete() уже предоставляются JpaRepository, но мы можем создать кастомные методы:
}