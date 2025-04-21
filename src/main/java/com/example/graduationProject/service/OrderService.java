package com.example.graduationProject.service;

import com.example.graduationProject.entities.Order;
import com.example.graduationProject.enumeration.*;
import com.example.graduationProject.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;

    public Order findById(int id){
        return orderRepository.findById(id).get();
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public void saveUpdateOrder(Order order){
        orderRepository.save(order);
    }

    public Order saveUpdateOrderReturnOrder(Order order){
        return orderRepository.save(order);
    }

    public void deleteOrderByOrder(Order order){
        orderRepository.delete(order);
    }

    public void deleteOrderById(int id){
        orderRepository.deleteById(id);
    }

    public boolean isExistOrderById(int id){
        return orderRepository.existsById(id);
    }

    public void updateFilling(int id, String filling){
        orderRepository.updateFilling(id, filling);
    }

    public void updateType(int id, TYPEORDER type){
        orderRepository.updateType(id, type);
    }

    public void updateSize(int id, SIZE size){
        orderRepository.updateSize(id, size);
    }

    public void updateForWhom(int id, FORWHOM fromWhom){
        orderRepository.updateForWhom(id, fromWhom);
    }

    public void updateSubject(int id, SUBJECT subject){
        orderRepository.updateSubject(id, subject);
    }

    public void updateColor(int id, COLORCOMBO color){
        orderRepository.updateColor(id, color);
    }

    public void updateIdUser(int id, int idUser){
        orderRepository.updateIdUser(id, idUser);
    }

    public boolean isFieldPresent(int id, String fieldName) {
        return orderRepository.isFieldPresent(id, fieldName);
    }

    // Метод для получения последнего заказа по idUser
    public List<Order> getAllOrdersByUserId(int userId) {
        return Optional.ofNullable(orderRepository.findAllByUserId(userId))
                .orElse(Collections.emptyList());
    }



}
