package com.example.graduationProject.controller;

import com.example.graduationProject.entities.Product;
import com.example.graduationProject.service.ProductService;
import com.example.graduationProject.service.StageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.http.client.methods.RequestBuilder.put;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ImagesController imagesController;

    @GetMapping("/api/testConnect")
    public String productListener(){
        return "Hello world";
    }

    // Получение одного продукта по ID
    @GetMapping("/api/getAllProducts")
    public ResponseEntity<?> getAllProducts() {
        try {
            List<Product> products = productService.findAll();

            String url = "http://localhost:8080/api/images/";
            List<Map<String, Object>> moddedProducts = modifyProduct(products, url);

            return ResponseEntity.ok(moddedProducts);
        } catch (Exception e) {
            log.error("Ошибка при получении всех продуктов", e);
            return ResponseEntity.status(500).body("Ошибка сервера: " + e.getMessage());
        }
    }


    @GetMapping("/api/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Product product = productService.findProductById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/api/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productService.save(product);
        return ResponseEntity.ok(savedProduct);
    }

    @DeleteMapping("/api/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    private List<Map<String, Object>> modifyProduct(List<Product> products, String url){
        List<Map<String, Object>> moddedProducts = new ArrayList<>();

        products.forEach(product -> {
            Map<String, Object> productMap = new HashMap<>();
            productMap.put("idProduct", product.getIdProduct());
            productMap.put("images", imagesController.getImagesFileNameByAlbumIdWithUrl(product.getIdAlbum()));
            productMap.put("title", product.getTitle());
            productMap.put("category", product.getCategory());
            productMap.put("subcategory",product.getSubcategory());
            productMap.put("description", product.getDescription());
            productMap.put("price", product.getPrice());
            productMap.put("header", product.getHeader());
            productMap.put("ingredient", product.getIngredient());
            productMap.put("unit", product.isUnit());
            moddedProducts.add(productMap);
        });


        return moddedProducts;
    }

}
