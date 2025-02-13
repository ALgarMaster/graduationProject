package com.example.graduationProject.repository;

import com.example.graduationProject.entities.Order;
import com.example.graduationProject.enumeration.*;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer>{

    // Найти заказ по ID
    Optional<Order> findById(int id);

    // Найти все заказы
    List<Order> findAll();

    // Удалить заказ по ID
    void deleteById(Integer id);

    // Проверить существование заказа по ID
    boolean existsById(Integer id);

    // Обновление title
    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.title = :title WHERE o.id_order = :id")
    void updateTitle(@Param("id") int id, @Param("title") String title);

    // Обновление type
    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.type = :type WHERE o.id_order = :id")
    void updateType(@Param("id") int id, @Param("type") TYPE_ORDER type);

    // Обновление size
    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.size = :size WHERE o.id_order = :id")
    void updateSize(@Param("id") int id, @Param("size") SIZE size);

    // Обновление for_whom
    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.fromWhom = :fromWhom WHERE o.id_order = :id")
    void updateForWhom(@Param("id") int id, @Param("fromWhom") FOR_WHOM fromWhom);

    // Обновление subject
    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.subject = :subject WHERE o.id_order = :id")
    void updateSubject(@Param("id") int id, @Param("subject") SUBJECT subject);

    // Обновление color
    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.color = :color WHERE o.id_order = :id")
    void updateColor(@Param("id") int id, @Param("color") COLOR_COMBO color);

    // Обновление idUser
    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.idUser = :idUser WHERE o.id_order = :id")
    void updateIdUser(@Param("id") int id, @Param("idUser") int idUser);
}
