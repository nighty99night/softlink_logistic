package ru.kalinichenko.softlink_logistic.service.order;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.kalinichenko.softlink_logistic.dao.*;
import ru.kalinichenko.softlink_logistic.entity.order.Order;
import ru.kalinichenko.softlink_logistic.logic.distance.DistanceMatrixService;
import ru.kalinichenko.softlink_logistic.service.UserService;

import java.util.List;

@Service
public class OrderCreateService {

    private final OrderStatusRepository orderStatusRepository;
    private final OrderRepository orderRepository;
    private final CityRepository cityRepository;
    private final CargoTypeRepository cargoTypeRepository;
    private final OrderAddressRepository orderAddressRepository;
    private final CargoRepository cargoRepository;

    private final DistanceCalculator distanceCalculator;
    private final PriceCalculator priceCalculator;
    private final UserService userService;

    public OrderCreateService(OrderStatusRepository orderStatusRepository,
                              OrderRepository orderRepository,
                              CityRepository cityRepository,
                              CargoTypeRepository cargoTypeRepository,
                              OrderAddressRepository orderAddressRepository,
                              CargoRepository cargoRepository,
                              DistanceCalculator distanceCalculator, PriceCalculator priceCalculator, UserService userService) {
        this.orderStatusRepository = orderStatusRepository;
        this.orderRepository = orderRepository;
        this.cityRepository = cityRepository;
        this.cargoTypeRepository = cargoTypeRepository;
        this.orderAddressRepository = orderAddressRepository;
        this.cargoRepository = cargoRepository;
        this.distanceCalculator = distanceCalculator;
        this.priceCalculator = priceCalculator;
        this.userService = userService;
    }

    public Order create(Order order){
        order.getAddress().setDistance(distanceCalculator.calc(order.getAddress()));
        order.setPrice(priceCalculator.calculate(order));

        cityRepository.saveAll(List.of(
                order.getAddress().getCityFrom(),
                order.getAddress().getCityTo()
        ));

        orderAddressRepository.save(order.getAddress());
        cargoRepository.save(order.getCargo());

        order.setStatus(orderStatusRepository.getReferenceById("Создан"));

        order.setUser(userService.getAuthorized());
        return orderRepository.save(order);
    }

    public void initModel(Model model){
        model.addAttribute("order", new Order());
        model.addAttribute("types", cargoTypeRepository.findAll());
        model.addAttribute("cities", cityRepository.findAll());
        model.addAttribute("action", "create");
    }
}
