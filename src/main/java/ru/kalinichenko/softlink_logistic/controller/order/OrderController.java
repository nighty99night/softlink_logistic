package ru.kalinichenko.softlink_logistic.controller.order;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kalinichenko.softlink_logistic.dao.CargoTypeRepository;
import ru.kalinichenko.softlink_logistic.dao.CityRepository;
import ru.kalinichenko.softlink_logistic.dao.OrderRepository;
import ru.kalinichenko.softlink_logistic.entity.order.Order;
import ru.kalinichenko.softlink_logistic.service.order.OrderEditService;
import ru.kalinichenko.softlink_logistic.service.order.OrderService;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    private final CityRepository cityRepository;

    private final CargoTypeRepository cargoTypeRepository;

    private final OrderEditService orderEditService;

    public OrderController(OrderRepository orderRepository,
                           OrderService orderService,
                           CityRepository cityRepository,
                           CargoTypeRepository cargoTypeRepository, OrderEditService orderEditService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
        this.cityRepository = cityRepository;
        this.cargoTypeRepository = cargoTypeRepository;
        this.orderEditService = orderEditService;
    }


    @GetMapping
    public String getAllOrders(Model model) {
        List<Order> orders = orderService.getOrdersByAuthorizedUser();
        model.addAttribute("orders", orders);
        return "order/list";
    }


    @GetMapping("/{id}/edit")
    public String showEditOrderForm(@PathVariable("id") Long id, Model model) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order id: " + id));
        model.addAttribute("order", order);
        model.addAttribute("types", cargoTypeRepository.findAll());
        model.addAttribute("cities", cityRepository.findAll());
        model.addAttribute("action", id+"/edit");
        model.addAttribute("detailsFieldVisible", true);
        return "order/form";
    }

    @PostMapping("/{id}/edit")
    public String updateOrder(@PathVariable("id") Long id, @ModelAttribute("order") Order order) {
        orderEditService.edit(id, order);
        return "redirect:/orders";
    }

    @GetMapping("/{id}/delete")
    public String deleteOrder(@PathVariable("id") Long id) {
        orderRepository.deleteById(id);
        return "redirect:/orders";
    }


    @GetMapping("/{id}")
    public String order(@PathVariable("id") Long id, Model model){
        model.addAttribute("order", orderRepository.getReferenceById(id));
        return "/order/order";
    }
}

