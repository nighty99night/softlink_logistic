package ru.kalinichenko.softlink_logistic.controller.order;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kalinichenko.softlink_logistic.entity.order.Order;
import ru.kalinichenko.softlink_logistic.service.order.OrderCreateService;

@Controller
@RequestMapping("/orders/create")
public class OrderCreateController {
    private final OrderCreateService orderCreateService;
    public OrderCreateController(OrderCreateService orderCreateService) {
        this.orderCreateService = orderCreateService;
    }
    @GetMapping
    public String showCreateOrderForm(Model model) {
        orderCreateService.initModel(model);
        model.addAttribute("detailsFieldVisible", true);
        return "order/form";
    }
    @PostMapping
    public String createOrder(@ModelAttribute("order") Order order) {
        orderCreateService.create(order);
        return "redirect:/orders";
    }
}
