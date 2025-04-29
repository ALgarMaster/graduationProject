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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_product", nullable = false, unique = true)
    private int idProduct;

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
    public Product(int idAlbum, String title, Category category, SubCategory subcategory) {
        this.idAlbum = idAlbum;
        this.title = title;
        this.category = category;
        this.subcategory = subcategory;
        this.unit = false;
    }
    public Product(int idAlbum, String title, Category category, SubCategory subcategory, boolean unit) {
        this.idAlbum = idAlbum;
        this.title = title;
        this.category = category;
        this.subcategory = subcategory;
        this.unit = unit;
    }

    public boolean isUnit() {
        return unit;
    }

    public void setUnit(boolean unit) {
        this.unit = unit;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SubCategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(SubCategory subcategory) {
        this.subcategory = subcategory;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }

    public int getIdProduct() {
        return idProduct;
    }
}

