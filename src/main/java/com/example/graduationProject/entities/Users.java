package com.example.graduationProject.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="id_user", nullable = false, unique = true)
    private int idUser;

    @Column(name ="niсkname_")
    private String niсkname_;

    @Column(name ="chat_id")
    private int chatId;

    @Column(name = "is_admin", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isAdmin = false;

    public Users(){}

    public Users(String nikname_, int chat_id){
        this.chatId = chat_id;
        this.isAdmin = false;
        this.niсkname_ = nikname_;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getNiсkname_() {
        return niсkname_;
    }

    public void setNiсkName_(String niсkname_) {
        this.niсkname_ = niсkname_;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id_user=" + idUser +
                ", niсkname_='" + niсkname_ + '\'' +
                ", chat_id=" + chatId +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
