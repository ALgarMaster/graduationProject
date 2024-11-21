package com.example.graduationProject.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_image;

    private String file_name;
    private String file_src;

    public Images(){};

    public Images(String file_name, String file_src){
        this.file_name = file_name;
        this.file_src = file_src;
    }

    public int getId_image() {
        return id_image;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_src() {
        return file_src;
    }

    public void setFile_src(String file_src) {
        this.file_src = file_src;
    }
}
