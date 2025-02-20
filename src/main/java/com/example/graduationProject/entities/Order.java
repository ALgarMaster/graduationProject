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

    @Column(name ="title", nullable = true)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name ="type_", nullable = true)
    private TYPEORDER type;

    @Enumerated(EnumType.STRING)
    @Column(name ="size_", nullable = true)
    private SIZE size;

    @Enumerated(EnumType.STRING)
    @Column(name ="for_whom", nullable = true)
    private FORWHOM fromWhom;

    @Enumerated(EnumType.STRING)
    @Column(name ="subject", nullable = true)
    private SUBJECT subject;

    @Enumerated(EnumType.STRING)
    @Column(name ="color", nullable = true)
    private COLORCOMBO color;

    @Enumerated(EnumType.STRING)
    @Column(name ="state_order", nullable = true)
    private STATETURNBOT stateOrder;

    // Обычное поле для хранения idUser, которое будет внешним ключом
    @Column(name = "id_user", nullable = false)
    private int idUser;

    // Связь с сущностью User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", insertable = false, updatable = false)
    private Users user;



    public Order(){}

    public Order(int idUser){
        this.idUser =idUser;
        this.stateOrder = STATETURNBOT.NEW;
    }

    public Order(String title, TYPEORDER type, SIZE size, FORWHOM fromWhom, SUBJECT subject, COLORCOMBO color, int idUser,STATETURNBOT stateOrder) {
        this.color = color;
        this.idUser = idUser;
        this.title = title;
        this.size = size;
        this.fromWhom = fromWhom;
        this.subject = subject;
        this.type = type;
        this.stateOrder = stateOrder;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }


    public TYPEORDER getType() {
        return type;
    }

    public void setType(TYPEORDER type) {
        this.type = type;
    }

    public SIZE getSize() {
        return size;
    }

    public void setSize(SIZE size) {
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

    @Override
    public String toString() {
        return "Order{" +
                "id_order=" + id_order +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", size=" + size +
                ", stateOrder=" + stateOrder +
                ", fromWhom=" + fromWhom +
                ", subject=" + subject +
                ", color=" + color +
                ", idUser=" + idUser +
                '}';
    }
}
