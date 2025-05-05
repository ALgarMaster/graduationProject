package com.example.graduationProject.controller;

import com.example.graduationProject.entities.Order;
import com.example.graduationProject.entities.Product;
import com.example.graduationProject.enumeration.*;
import com.example.graduationProject.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;
    private final ProductController productController;


    @PostMapping("/api/fill")
    public ResponseEntity<?> receiveOrderFilling(@RequestBody Map<String, Object> orderFilling) {
        Object orderIdObj = orderFilling.get("order_id");
        int orderId;

        // Получаем order_id
        try {
            if (orderIdObj instanceof Number) {
                orderId = ((Number) orderIdObj).intValue();
            } else if (orderIdObj instanceof String) {
                orderId = Integer.parseInt((String) orderIdObj);
            } else {
                return ResponseEntity.badRequest().body("Неверный формат order_id");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка парсинга order_id");
        }

        // Получаем filling
        List<Map<String, Object>> filling = (List<Map<String, Object>>) orderFilling.get("filling");

        // Получаем chatId (если передан с фронта)
        Long chatId = null;
        if (orderFilling.containsKey("chatId")) {
            Object chatIdObj = orderFilling.get("chatId");
            try {
                if (chatIdObj instanceof Number) {
                    chatId = ((Number) chatIdObj).longValue();
                } else if (chatIdObj instanceof String) {
                    chatId = Long.parseLong((String) chatIdObj);
                }
            } catch (Exception e) {
                log.warn("Ошибка парсинга chatId: {}", chatIdObj);
            }
        }

        log.info("Получен заказ. ID: {}, chatId: {}, filling: {}", orderId, chatId, filling);










        Optional<Order> optionalOrder = Optional.ofNullable(orderService.findById(orderId));
        if (optionalOrder.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }

        Order order = optionalOrder.get();

        try {
            order.setFilling(new ObjectMapper().writeValueAsString(filling));
            orderService.saveUpdateOrder(order);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка обработки данных");
        }

        // Отправка сообщения
        if (chatId != null) {
            String messageText = formatFullOrderMessage(order.getId_order());

            String token = System.getenv("BOT_TOKEN");

            String telegramApiUrl = "https://api.telegram.org/bot" + token + "/sendMessage";

            RestTemplate restTemplate = new RestTemplate();

            // Параметры запроса
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("chat_id", chatId);
            requestBody.put("text", messageText);

            try {
                restTemplate.postForObject(telegramApiUrl, requestBody, String.class);
            } catch (Exception e) {
                log.error("Ошибка при отправке сообщения в Telegram: {}", e.getMessage());
            }
        }

        return ResponseEntity.ok("OK");
    }


    public String formatFullOrderMessage(int idOrder) {
        Order order = getOrderById(idOrder);
        StringBuilder message = new StringBuilder();
        message.append("Вы собрали заказа:\n");
        message.append(order.toString()).append("\n"); // Подключаем кастомный toString()

        message.append("📦 Состав заказа:\n");
        ObjectMapper mapper = new ObjectMapper();
        BigDecimal totalOrderPrice = BigDecimal.ZERO;

        try {
            JsonNode fillingArray = mapper.readTree(order.getFilling());
            for (JsonNode item : fillingArray) {
                int productId = item.has("product") ? item.get("product").asInt() : -1;
                int quantity = item.has("quantity") ? item.get("quantity").asInt() : 0;

                if (productId == -1 || quantity == 0) continue;

                Product product = productController.getProductById(productId);
                if (product == null) {
                    message.append("• Неизвестный товар (ID: ").append(productId).append(")\n");
                    continue;
                }

                BigDecimal price = product.getPrice() != null ? product.getPrice() : BigDecimal.ZERO;
                BigDecimal total = price.multiply(BigDecimal.valueOf(quantity));
                totalOrderPrice = totalOrderPrice.add(total);

                message.append("• ").append(product.getTitle())
                        .append(" — ").append(quantity).append(product.isUnit() ? " шт." : " г.")
                        .append(" × ").append(price).append("₽ = ")
                        .append(total).append("₽\n");
            }
        } catch (Exception e) {
            message.append("⚠️ Ошибка при обработке состава заказа.\n");
            e.printStackTrace();
        }

        message.append("\n💰 Общая сумма: ").append(totalOrderPrice).append("₽");

        return message.toString();
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
