package ru.kalinichenko.softlink_logistic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kalinichenko.softlink_logistic.dao.OrderRepository;
import ru.kalinichenko.softlink_logistic.entity.order.Order;
import ru.kalinichenko.softlink_logistic.service.OrderService;
import ru.kalinichenko.softlink_logistic.service.ShippingService;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    private final ShippingService shippingService;
    public OrderController(OrderRepository orderRepository, OrderService orderService, ShippingService shippingService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
        this.shippingService = shippingService;
    }

    @GetMapping
    public String getAllOrders(Model model) {
        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "order/list";
    }

    @GetMapping("/create")
    public String showCreateOrderForm(Model model) {
        model.addAttribute("order", new Order());
        return "order/form";
    }

    @PostMapping("/create")
    public String createOrder(@ModelAttribute("order") Order order) {
        orderRepository.save(order);
        return "redirect:/orders";
    }

    @GetMapping("/{id}/edit")
    public String showEditOrderForm(@PathVariable("id") Long id, Model model) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order id: " + id));
        model.addAttribute("order", order);
        return "order/form";
    }

    @PostMapping("/{id}/edit")
    public String updateOrder(@PathVariable("id") Long id, @ModelAttribute("order") Order order) {
        order.setId(id);
        orderRepository.save(order);
        return "redirect:/orders";
    }

    @GetMapping("/{id}/delete")
    public String deleteOrder(@PathVariable("id") Long id) {
        orderRepository.deleteById(id);
        return "redirect:/orders";
    }

    @GetMapping("/{id}/calculate-shipping")
    public String calculateShippingCost(@PathVariable("id") Long orderId, Model model) {
        // Получение заказа по его идентификатору
        Order order = orderRepository.getReferenceById(orderId);

        // Выполнение расчета стоимости доставки на основе параметров заказа (вес, расстояние и т.д.)
        double shippingCost = shippingService.calculateShippingCost(order);

        // Передача рассчитанной стоимости доставки в модель для отображения на странице
        model.addAttribute("shippingCost", shippingCost);

        return "order/shipping-cost";
    }

    @GetMapping("/track/{id}")
    public String trackOrder(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "order/track";
    }
}

