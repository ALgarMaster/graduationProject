package com.example.graduationProject.repository;

import com.example.graduationProject.entities.Album;
import com.example.graduationProject.entities.Images;
import com.example.graduationProject.service.AlbumService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Integer> {


}
