package com.example.graduationProject.service;

import com.example.graduationProject.entities.Images;
import com.example.graduationProject.entities.Stage;
import com.example.graduationProject.enumeration.STATEMESSAGE;
import com.example.graduationProject.repository.StageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StageService {
    private final StageRepository stageRepository;

    public Optional<Stage> getStageById(int id){
        return stageRepository.findById(id);
    }

    public Optional<Stage> getStageByStateMessage(STATEMESSAGE statemessage){
        return stageRepository.findByStateMessages(statemessage);
    }
}
