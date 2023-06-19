package ru.kalinichenko.softlink_logistic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kalinichenko.softlink_logistic.dao.CargoTypeRepository;
import ru.kalinichenko.softlink_logistic.entity.order.Order;
import ru.kalinichenko.softlink_logistic.service.order.DistanceCalculator;
import ru.kalinichenko.softlink_logistic.service.order.OrderCreateService;
import ru.kalinichenko.softlink_logistic.service.order.PriceCalculator;

@Controller
@RequestMapping("/shipping")
public class ShippingController {
    private final OrderCreateService orderCreateService;
    private final CargoTypeRepository cargoTypeRepository;
    private final PriceCalculator priceCalculator;
    private final DistanceCalculator distanceCalculator;

    public ShippingController(OrderCreateService orderCreateService, CargoTypeRepository cargoTypeRepository, PriceCalculator priceCalculator, DistanceCalculator distanceCalculator) {
        this.orderCreateService = orderCreateService;
        this.cargoTypeRepository = cargoTypeRepository;
        this.priceCalculator = priceCalculator;
        this.distanceCalculator = distanceCalculator;
    }

    @GetMapping
    public String home(Model model){
        if(!model.containsAttribute("order")){
            orderCreateService.initModel(model);
        }
        model.addAttribute("detailsFieldVisible", false);
        return "calculate";
    }
    @PostMapping
    public String calculate(@ModelAttribute("order") Order order, Model model) {
        order.getAddress().setDistance(distanceCalculator.calc(order.getAddress()));
        model.addAttribute("order", order);
        model.addAttribute("types", cargoTypeRepository.findAll());
        model.addAttribute("result", priceCalculator.calculate(order));
        return "calculate";
    }
}
