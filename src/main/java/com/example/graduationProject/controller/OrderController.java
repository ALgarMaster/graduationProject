package com.example.graduationProject.controller;

import com.example.graduationProject.entities.Order;
import com.example.graduationProject.enumeration.*;
import com.example.graduationProject.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;

//    @PutMapping("/{id}/filling")
//    public void updateFilling(@PathVariable int id, @RequestBody String filling){
//        Order order = getLastOrderByUserId(id);
//        try{
//            orderService.updateFilling(order.getId_order(), filling);
//        }catch (Exception e) {
//            log.error("Ошибка при получении всех продуктов", e);
//        }
//    }

    @PostMapping("/api/fill")
    public ResponseEntity<?> receiveOrderFilling(@RequestBody Map<String, Object> orderFilling) {
        // Получаем данные из JSON
        int orderId = ((Number) orderFilling.get("order_id")).intValue();  // Получаем ID заказа
        List<Map<String, Object>> filling = (List<Map<String, Object>>) orderFilling.get("filling");  // Получаем товары
        log.info("Айди заказа:"+orderId+". Корзина: ");

        // Найдем заказ по orderId
        Optional<Order> optionalOrder = Optional.ofNullable(orderService.findById(orderId));
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            try {
                // Сохраняем обновленный заказ, записывая корзину как строку
                order.setFilling(new ObjectMapper().writeValueAsString(filling)); // Преобразуем список в строку
                orderService.saveUpdateOrder(order);
                return ResponseEntity.ok("OK");
            } catch (JsonProcessingException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка обработки данных");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }
    }

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

    public void updateType(int id, TYPEORDER type) {
        orderService.updateType(id, type);
    }

    public void updateSize(int id, SIZE size) {
        orderService.updateSize(id, size);
    }

    public void updateForWhom(int id, FORWHOM fromWhom) {
        orderService.updateForWhom(id, fromWhom);
    }

    public void updateSubject(int id, SUBJECT subject) {
        orderService.updateSubject(id, subject);
    }

    public void updateColor(int id, COLORCOMBO color) {
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
            // Получаем список заказов пользователя
            List<Order> orders = orderService.getAllOrdersByUserId(userId);

            // Проверяем, есть ли заказы
            if (orders.isEmpty()) {
                log.warn("No orders found for user with id: {}", userId);
                return null;
            }

            // Получаем последний заказ из списка
            Order lastOrder = orders.get(orders.size() - 1);
            log.info("Last order found for user id {}: {}", userId, lastOrder);

            return lastOrder;
        } catch (Exception e) {
            // Логируем ошибку
            log.error("Error occurred while fetching the last order for user id {}: {}", userId, e.getMessage(), e);

            throw  e;
        }
    }


}
