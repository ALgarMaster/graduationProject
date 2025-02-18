package com.example.graduationProject.controller;

import com.example.graduationProject.entities.Order;
import com.example.graduationProject.enumeration.*;
import com.example.graduationProject.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;

    public Order getOrderById(int id){
        return orderService.findById(id);
    }

    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    public void saveUpdateOrder(Order order) {
        orderService.saveUpdateOrder(order);
    }

    public Order saveUpdateOrderReturnOrder(Order order) {
        return  orderService.saveUpdateOrderReturnOrder(order);
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

    public boolean isFieldPresent(int id, String fieldName) {
        return orderService.isFieldPresent(id, fieldName);
    }

    public Order getLastOrderByUserId(int userId) {
        // Логируем получение запроса
        log.info("Received request to fetch the last order for user id: {}", userId);

        try {
            // Получаем последний заказ для пользователя
            ArrayList<Order> orders = new ArrayList<>(orderService.getAllOrderByUserId(userId));


            log.info(orders.get(0)+"");
            // Проверяем, найден ли заказ
            if (orders != null) {
                log.info(orders+"");
                log.info("Last order found for user id: {}", userId);
                return orders.get(orders.size()-1); // Возвращаем заказ, если найден
            } else {
                log.warn("No orders found for user with id: {}", userId);
                return null; // Возвращаем null, если заказ не найден
            }
        } catch (Exception e) {
            // Логируем ошибку, если что-то пошло не так
            log.error("Error occurred while fetching the last order for user id {}: {}", userId, e.getMessage(), e);
            return null; // Возвращаем null в случае ошибки
        }
    }

}
