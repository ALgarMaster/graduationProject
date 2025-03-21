package com.example.graduationProject.repository;

import com.example.graduationProject.entities.Order;
import com.example.graduationProject.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Transactional
    Optional<Product> findById(int id);

    @Transactional
    List<Product> findAll();

    @Transactional
    void deleteById(Integer id);

    @Transactional
    boolean existsById(Integer id);

}
