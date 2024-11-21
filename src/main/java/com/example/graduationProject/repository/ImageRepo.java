package com.example.graduationProject.repository;

import com.example.graduationProject.entities.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ImageRepo extends JpaRepository<Images, Integer> {

}
