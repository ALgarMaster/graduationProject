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
    @Column(name ="id_order", nullable = false, unique = true)
    private int id_order;

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

    @Column(name = "filling", columnDefinition = "VARCHAR(255)", nullable = true)
    private String filling;



    public Order(){}

    public Order(int idUser){
        this.idUser =idUser;
        this.stateOrder = STATETURNBOT.NEW;
    }

    public Order(String title, TYPEORDER type, SIZE size, FORWHOM fromWhom, SUBJECT subject, COLORCOMBO color, int idUser,STATETURNBOT stateOrder) {
        this.color = color;
        this.idUser = idUser;
        this.size = size;
        this.fromWhom = fromWhom;
        this.subject = subject;
        this.type = type;
        this.stateOrder = stateOrder;
    }

    public  int getId(){
        return id_order;
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

    public String getFilling() {
        return filling;
    }

    public void setFilling(String filling) {
        this.filling = filling;
    }

    public COLORCOMBO getColor() {
        return color;
    }

    public STATETURNBOT getStateOrder() {
        return stateOrder;
    }

    public void setStateOrder(STATETURNBOT stateOrder) {
        this.stateOrder = stateOrder;
    }

    public void setColor(COLORCOMBO color) {
        this.color = color;
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();

        message.append("Тип: ").append(type != null ? getReadableType(type) : "Пока не выбран").append("\n");
        message.append("Размер: ").append(size != null ? getReadableSize(size) : "Пока не выбран").append("\n");
        message.append("От кого: ").append(fromWhom != null ? getReadableFromWhom(fromWhom) : "Пока не выбран").append("\n");
        message.append("Праздник: ").append(subject != null ? getReadableSubject(subject) : "Пока не выбран").append("\n");
        message.append("Цвет: ").append(color != null ? getReadableColor(color) : "Пока не выбран").append("\n");
        
        return message.toString();
    }





    private String getReadableType(TYPEORDER type) {
        switch (type.name()) {
            case "BASKET": return "Корзина";
            case "PALLET": return "Паллет";
            case "BOUQUET": return "Букет";
            case "BOX": return "Коробка";
            case "ROUNDBOUQUET": return "Круглый букет";
            case "BOUQUETLEG": return "Букет с ножкой";
            case "ROUNDBOX": return "Круглая коробка";
            case "SQUAREBOX": return "Квадратная коробка";
            default: return "Пока не выбран";
        }
    }

    private String getReadableSize(SIZE size) {
        switch (size.name()) {
            case "SMALL": return "Маленький";
            case "MEDIUM": return "Средний";
            case "LARGE": return "Большой";
            default: return "Пока не выбран";
        }
    }

    private String getReadableColor(COLORCOMBO color) {
        switch (color.name()) {
            case "RED": return "Красный";
            case "YELLOW": return "Желтый";
            case "PINK": return "Розовый";
            case "GREEN": return "Зеленый";
            case "SKY": return "Небесный";
            case "BROWN": return "Коричневый";
            case "VIOLET": return "Фиолетовый";
            case "DARK_GREEN": return "Темно-зеленый";
            case "PURPLE": return "Пурпурный";
            case "BLUE": return "Синий";
            case "CREAM": return "Кремовый";
            default: return "Пока не выбран";
        }
    }

    private String getReadableFromWhom(FORWHOM fromWhom) {
        switch (fromWhom.name()) {
            case "HE": return "Для него";
            case "SHE": return "Для неё";
            case "NOTHING": return "Для всех";
            default: return "Пока не выбран";
        }
    }

    private String getReadableSubject(SUBJECT subject) {
        switch (subject.name()) {
            case "NEW_YEAR": return "Новый год";
            case "FEBRUARY_23": return "23 Февраля";
            case "MARCH_8": return "8 Марта";
            case "LAST_BELL": return "Последний звонок";
            case "SEPTEMBER_1": return "1 Сентября";
            case "TEACHERS_DAY": return "День учителя";
            case "EDUCATORS_DAY": return "День воспитателя";
            case "BIRTHDAY": return "День рождения";
            case "COACHS_DAY": return "День тренера";
            case "MEDICAL_WORKERS_DAY": return "День медицинского работника";
            case "WEDDING": return "Свадьба";
            case "CORPORATE": return "Корпоратив";
            case "ANY_DAY": return "Любой день";
            default: return "Пока не выбран";
        }
    }
}
