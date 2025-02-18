package com.example.graduationProject.service;

import com.example.graduationProject.entities.Order;
import com.example.graduationProject.enumeration.*;
import com.example.graduationProject.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    // Метод для получения последнего заказа по idUser
    public List<Order> getAllOrderByUserId(int idUser) {
        log.info("Fetching the last order for user with id: {}", idUser);  // Логируем запрос для диагностики

        try {
            // Получаем заказы пользователя
            List<Order> orders = orderRepository.findAllByIdUser(idUser);

            // Проверяем, что заказы найдены
//            if (orders.isEmpty()) {
//                log.warn("No orders found for user with id: {}", idUser);
//                return null; // Возвращаем null, если заказ не найден
//            }

            // Возвращаем последний заказ
            // Получаем последний элемент в списке
            log.info("Last order found for user id: {}", idUser);
            return orders;

        } catch (Exception e) {
            // Логируем ошибку, если что-то пошло не так
            log.error("Error occurred while fetching the last order for user id {}: {}", idUser, e.getMessage(), e);
            return null; // Возвращаем null в случае ошибки
        }
    }



}
