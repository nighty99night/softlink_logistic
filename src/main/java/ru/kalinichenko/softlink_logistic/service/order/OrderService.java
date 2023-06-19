package ru.kalinichenko.softlink_logistic.service.order;

import org.springframework.boot.Banner;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.kalinichenko.softlink_logistic.dao.OrderRepository;
import ru.kalinichenko.softlink_logistic.entity.order.Order;
import ru.kalinichenko.softlink_logistic.service.UserService;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    public OrderService(OrderRepository orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + id));
    }

    public List<Order> getOrdersByAuthorizedUser(){
        return orderRepository
                .findAll()
                .stream()
                .filter(o->o.getUser().getUsername().equals(userService.getAuthorized().getUsername()))
                .collect(Collectors.toList());
    }

    public void initModel(Model model, Order order){
        model.addAttribute("order", order);
    }



    // Другие методы сервиса для управления заказами
}
