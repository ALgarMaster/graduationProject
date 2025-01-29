package com.example.graduationProject.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "images")
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_image")
    private int idImage;

    @Column(name ="file_name")
    private String fileName;

    @Column(name ="file_src")
    private String fileSrc;

    @Column(name ="id_album")
    private String id_album;

    public Images(String file_name, String file_src, String id_album){
        this.fileName = file_name;
        this.fileSrc = file_src;
        this.id_album = id_album;
    }

    public int getIdImage() {
        return idImage;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSrc() {
        return fileSrc;
    }

    public void setFileSrc(String fileSrc) {
        this.fileSrc = fileSrc;
    }
}
