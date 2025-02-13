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
    @Column(name ="id_user")
    private int id_user;

    @Column(name ="niсkname_")
    private String niсkname_;

    @Column(name ="chat_id")
    private int chat_id;

    @Column(name = "is_admin", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isAdmin = false;

    public Users(){}

    public Users(String nikname_, int chat_id){
        this.chat_id = chat_id;
        this.isAdmin = false;
        this.niсkname_ = nikname_;
    }

    public int getId_user() {
        return id_user;
    }

    public String getNiсkname_() {
        return niсkname_;
    }

    public void setNiсkname_(String niсkname_) {
        this.niсkname_ = niсkname_;
    }

    public int getChat_id() {
        return chat_id;
    }

    public void setChat_id(int chat_id) {
        this.chat_id = chat_id;
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
                "id_user=" + id_user +
                ", niсkname_='" + niсkname_ + '\'' +
                ", chat_id=" + chat_id +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
