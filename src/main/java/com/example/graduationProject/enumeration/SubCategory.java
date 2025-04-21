package com.example.graduationProject.enumeration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum SubCategory {
    // Чай
    ALL_TEA("all", "Весь чай"),
    BLACK_TEA("black", "Черный чай"),
    GREEN_TEA("green", "Зеленый чай"),
    BLACK_TEA_WITH_ADDITIVES("black-additives", "Чёрный с добавками"),
    GREEN_TEA_WITH_ADDITIVES("green-additives", "Зелёный с добавками"),
    BLENDED_TEA("blended_tea", "купажный чай"),

    // Кофе
    ALL_COFFEE("all", "Весь кофе"),
    CLASSIC("classic", "Классический кофе"),
    DESSERT("dessert-lamarca", "Десертный кофе La Marca"),

    ALL_SWEETS("all", "Все сладости");

    private final String inputSubCategory;
    private final String name;

    SubCategory(String id, String name) {
        this.inputSubCategory = id;
        this.name = name;
    }

    public String getInputSubCategory() {
        return inputSubCategory;
    }

    public String getName() {
        return name;
    }

    public static SubCategory fromId(String id) {
        for (SubCategory subCategory : values()) {
            if (subCategory.inputSubCategory.equals(id)) {
                return subCategory;
            }
        }
        throw new IllegalArgumentException("Неизвестная подкатегория: " + id);
    }

    public static List<String> getAllNames() {
        return Arrays.stream(values())
                .map(SubCategory::getName)
                .collect(Collectors.toList());
    }
}
