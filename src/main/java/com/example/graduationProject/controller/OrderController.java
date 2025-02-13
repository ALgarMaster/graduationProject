package com.example.graduationProject.controller;

import com.example.graduationProject.entities.Order;
import com.example.graduationProject.enumeration.*;
import com.example.graduationProject.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    public void saveUpdateOrder(Order order) {
        orderService.saveUpdateOrder(order);
    }

    public void deleteOrderByOrder(Order order) {
        orderService.deleteOrderByOrder(order);
    }

    public void deleteOrderById(int id) {
        orderService.deleteOrderById(id);
    }

    public boolean isExistOrderById(int id) {
        return orderService.isExistOrderById(id);
    }

    public void updateTitle(int id, String title) {
        orderService.updateTitle(id, title);
    }

    public void updateType(int id, TYPE_ORDER type) {
        orderService.updateType(id, type);
    }

    public void updateSize(int id, SIZE size) {
        orderService.updateSize(id, size);
    }

    public void updateForWhom(int id, FOR_WHOM fromWhom) {
        orderService.updateForWhom(id, fromWhom);
    }

    public void updateSubject(int id, SUBJECT subject) {
        orderService.updateSubject(id, subject);
    }

    public void updateColor(int id, COLOR_COMBO color) {
        orderService.updateColor(id, color);
    }

    public void updateIdUser(int id, int idUser) {
        orderService.updateIdUser(id, idUser);
    }
}
