package com.example.graduationProject.entities;

import com.example.graduationProject.enumeration.Category;
import com.example.graduationProject.enumeration.STATETURNBOT;
import com.example.graduationProject.enumeration.SubCategory;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product", nullable = false, unique = true)
    private Long idProduct;

    @Column(name = "id_album", nullable = false)
    private int idAlbum;

    @Column(name = "title", nullable = true)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name ="category", nullable = true)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "subCategory", nullable = true)
    private SubCategory subcategory;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "price", nullable = true)
    private BigDecimal price;

    @Column(name = "header")
    private String header;

    @Column(name = "ingredient")
    private String ingredient;

    @Column(name = "unit", nullable = true)
    private boolean unit;

    public Product(){}

    public Product(String title, Category category, SubCategory subcategory, String description, BigDecimal price, String header, String ingredient) {
        this.idAlbum = 0;
        this.title = title;
        this.category = category;
        this.subcategory = subcategory;
        this.description = description;
        this.price = price;
        this.header = header;
        this.ingredient = ingredient;
        this.unit = false;
    }
    public Product(int idAlbum, String title, Category category, SubCategory subcategory, String description, BigDecimal price, String header, String ingredient) {
        this.idAlbum = idAlbum;
        this.title = title;
        this.category = category;
        this.subcategory = subcategory;
        this.description = description;
        this.price = price;
        this.header = header;
        this.ingredient = ingredient;
        this.unit = false;
    }
}

