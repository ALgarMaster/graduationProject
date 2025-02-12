package com.example.graduationProject.entities;

import com.example.graduationProject.enumeration.*;

public class Order {
    private int id_order;
    private String title;
    private TYPE_ORDER type;
    private SIZE size;
    private FOR_WHOM fromWhom;
    private SUBJECT subject;
    private COLOR_COMBO color;
    private int id_user;
    //определиться с описанием и хранимым объектом

    public Order(){

    }

    public Order(String title, TYPE_ORDER type, SIZE size, FOR_WHOM fromWhom, SUBJECT subject,COLOR_COMBO color, int id_user){

    }


    public TYPE_ORDER getType() {
        return type;
    }

    public void setType(TYPE_ORDER type) {
        this.type = type;
    }

    public SIZE getSize() {
        return size;
    }

    public void setSize(SIZE size) {
        this.size = size;
    }

    public FOR_WHOM getFromWhom() {
        return fromWhom;
    }

    public void setFromWhom(FOR_WHOM fromWhom) {
        this.fromWhom = fromWhom;
    }

    public SUBJECT getSubject() {
        return subject;
    }

    public void setSubject(SUBJECT subject) {
        this.subject = subject;
    }

    public COLOR_COMBO getColor() {
        return color;
    }

    public void setColor(COLOR_COMBO color) {
        this.color = color;
    }
}
