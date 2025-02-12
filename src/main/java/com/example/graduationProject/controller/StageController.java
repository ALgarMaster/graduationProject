package com.example.graduationProject.controller;

import com.example.graduationProject.entities.Stage;
import com.example.graduationProject.enumeration.STATEMESSAGE;
import com.example.graduationProject.repository.StageRepository;
import com.example.graduationProject.service.StageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.Optional;


@Slf4j
@Controller
@RequiredArgsConstructor
public class StageController {
    private final StageService stageService;

    public Stage getStageByID(int id){
        return stageService.getStageById(id).get();
    }

    public Stage getStageByIDAlbumStateMessage(STATEMESSAGE statemessage){
        Stage stage = stageService.getStageByStateMessage(statemessage).get();
        return stage;
    }


}
