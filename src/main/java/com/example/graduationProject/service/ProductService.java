package com.example.graduationProject.service;

import com.example.graduationProject.entities.Product;
import com.example.graduationProject.repository.OrderRepository;
import com.example.graduationProject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final ProductRepository productRepository;


    public Product save(Product product){
        return productRepository.save(product);
    }


    public Product findProductById(int id){
        return productRepository.findById(id).get();
    }


    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public void deleteById(int id){
        productRepository.deleteById(id);
    }


    public void deleteByProduct(Product product){
        productRepository.delete(product);
    }


    public boolean existById(int id){
        return productRepository.existsById(id);
    }

}
