package com.example.graduationProject.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "album")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="id_album")
    private int idAlbum;

    @Column(name ="name")
    private String name;

    public Album(){}

    public Album( String album_name){
        this.name = album_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdAlbum() {
        return idAlbum;
    }


}
