package com.example.graduationProject.service;

import com.example.graduationProject.entities.Images;
import com.example.graduationProject.repository.ImagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImagesService {
    private final ImagesRepository imgRepository;

    public List<Images> getAllImages(){
        return imgRepository.findAll();
    }

    public Images saveImage(Images image){
        return imgRepository.save(image);
    }

    public Optional<Images> getImageById(int id){
        return imgRepository.findById(id);
    }

    public List<Images> getImagesByIdAlbum(int idAlbum){ return  imgRepository.findAllByIdAlbum(idAlbum);}


}
