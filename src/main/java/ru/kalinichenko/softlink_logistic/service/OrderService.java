package ru.kalinichenko.softlink_logistic.service;

import org.springframework.stereotype.Service;
import ru.kalinichenko.softlink_logistic.dao.OrderRepository;
import ru.kalinichenko.softlink_logistic.entity.order.Order;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + id));
    }

    // Другие методы сервиса для управления заказами
}
