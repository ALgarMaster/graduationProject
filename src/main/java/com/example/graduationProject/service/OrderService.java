package com.example.graduationProject.service;

import com.example.graduationProject.entities.Order;
import com.example.graduationProject.enumeration.*;
import com.example.graduationProject.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public void saveUpdateOrder(Order order){
        orderRepository.save(order);
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

    public void updateTitle(int id, String title){
        orderRepository.updateTitle(id, title);
    }

    public void updateType(int id, TYPE_ORDER type){
        orderRepository.updateType(id, type);
    }

    public void updateSize(int id, SIZE size){
        orderRepository.updateSize(id, size);
    }

    public void updateForWhom(int id, FOR_WHOM fromWhom){
        orderRepository.updateForWhom(id, fromWhom);
    }

    public void updateSubject(int id, SUBJECT subject){
        orderRepository.updateSubject(id, subject);
    }

    public void updateColor(int id, COLOR_COMBO color){
        orderRepository.updateColor(id, color);
    }

    public void updateIdUser(int id, int idUser){
        orderRepository.updateIdUser(id, idUser);
    }

    public boolean isFieldPresent(int id, String fieldName) {
        return orderRepository.isFieldPresent(id, fieldName);
    }

}
