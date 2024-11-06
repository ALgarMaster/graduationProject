package com.example.graduationProject.entities;

import com.example.graduationProject.enumeration.COLORCOMBO;
import com.example.graduationProject.enumeration.FORWHOM;
import com.example.graduationProject.enumeration.SUBJECT;
import com.example.graduationProject.enumeration.TYPEORDER;

import java.util.random.RandomGenerator;

public class Order {
    private TYPEORDER type;
    private byte size;
    private FORWHOM fromWhom;
    private SUBJECT subject;
    private COLORCOMBO color;

    public TYPEORDER getType() {
        return type;
    }

    public void setType(TYPEORDER type) {
        this.type = type;
    }

    public byte getSize() {
        return size;
    }

    public void setSize(byte size) {
        this.size = size;
    }

    public FORWHOM getFromWhom() {
        return fromWhom;
    }

    public void setFromWhom(FORWHOM fromWhom) {
        this.fromWhom = fromWhom;
    }

    public SUBJECT getSubject() {
        return subject;
    }

    public void setSubject(SUBJECT subject) {
        this.subject = subject;
    }

    public COLORCOMBO getColor() {
        return color;
    }

    public void setColor(COLORCOMBO color) {
        this.color = color;
    }
}
