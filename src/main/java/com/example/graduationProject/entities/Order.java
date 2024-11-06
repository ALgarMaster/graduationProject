package com.example.graduationProject.entities;

import com.example.graduationProject.enumeration.COLOR_COMBO;
import com.example.graduationProject.enumeration.FOR_WHOM;
import com.example.graduationProject.enumeration.SUBJECT;
import com.example.graduationProject.enumeration.TYPE_ORDER;

public class Order {
    private TYPE_ORDER type;
    private byte size;
    private FOR_WHOM fromWhom;
    private SUBJECT subject;
    private COLOR_COMBO color;

    public TYPE_ORDER getType() {
        return type;
    }

    public void setType(TYPE_ORDER type) {
        this.type = type;
    }

    public byte getSize() {
        return size;
    }

    public void setSize(byte size) {
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
