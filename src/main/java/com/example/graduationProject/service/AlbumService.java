package com.example.graduationProject.service;

import com.example.graduationProject.entities.Album;
import com.example.graduationProject.repository.AlbumRepository;
import com.example.graduationProject.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlbumService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final AlbumRepository albumRepository;

    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    public Optional<Album> getAlbumById(int id) {
        return albumRepository.findById(id);
    }

    public Album createAlbum(Album album) {
        return albumRepository.save(album);
    }

    public Album updateAlbum(int id, Album albumDetails) {
        return albumRepository.findById(id)
                .map(album -> {
                    album.setName(albumDetails.getName());
                    return albumRepository.save(album);
                }).orElseThrow(() -> new RuntimeException("Album not found"));
    }

    public void deleteAlbum(int id) {
        albumRepository.deleteById(id);
    }

}
