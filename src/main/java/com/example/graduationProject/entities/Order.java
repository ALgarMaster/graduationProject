package com.example.graduationProject.entities;

import com.example.graduationProject.enumeration.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="id_order")
    private int id_order;

    @Column(name ="title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name ="type_")
    private TYPE_ORDER type;

    @Enumerated(EnumType.STRING)
    @Column(name ="size_")
    private SIZE size;

    @Enumerated(EnumType.STRING)
    @Column(name ="for_whom")
    private FOR_WHOM fromWhom;

    @Enumerated(EnumType.STRING)
    @Column(name ="subject")
    private SUBJECT subject;

    @Enumerated(EnumType.STRING)
    @Column(name ="color")
    private COLOR_COMBO color;

    @Column(name ="id_user", nullable = false)
    private int idUser;



    public Order(){}

    public Order(String title, TYPE_ORDER type, SIZE size, FOR_WHOM fromWhom, SUBJECT subject, COLOR_COMBO color, int idUser) {
        this.color = color;
        this.idUser = idUser;
        this.title = title;
        this.size = size;
        this.fromWhom = fromWhom;
        this.subject = subject;
        this.type = type;
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
