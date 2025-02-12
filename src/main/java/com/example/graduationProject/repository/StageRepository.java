package com.example.graduationProject.repository;


import com.example.graduationProject.entities.Images;
import com.example.graduationProject.entities.Stage;
import com.example.graduationProject.enumeration.STATEMESSAGE;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface StageRepository extends JpaRepository<Stage, Integer>{

    Optional<Stage> findById(int id);
    Optional<Stage> findByStateMessages(STATEMESSAGE statemessage);

}
