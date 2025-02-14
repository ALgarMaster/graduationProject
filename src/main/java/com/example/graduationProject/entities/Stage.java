package com.example.graduationProject.entities;

import com.example.graduationProject.enumeration.STATEMESSAGE;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "stage")
public class Stage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="id_stage")
    private int idStage;

    @Column(name ="title")
    private String title;

    @Column(name ="stage_content")
    private String stageContent;

    @Enumerated(EnumType.STRING)
    @Column(name ="state_messages")
    private STATEMESSAGE stateMessages;

    @Column(name ="id_album")
    private int idAlbum;

    public Stage() {
    }

    public Stage(String title, String stageContent, STATEMESSAGE stateMessages, int id_album){
        this.title = title;
        this.stageContent = stageContent;
        this.stateMessages = stateMessages;
        this.idAlbum = id_album;
    }

    public int getIdStage() { return idStage; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStageContent() {
        return stageContent;
    }

    public void setStageContent(String stageContent) {
        this.stageContent = stageContent;
    }

    public STATEMESSAGE getStateMessages() {
        return stateMessages;
    }

    public void setStateMessages(STATEMESSAGE stateMessages) {
        this.stateMessages = stateMessages;
    }

    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }
}
