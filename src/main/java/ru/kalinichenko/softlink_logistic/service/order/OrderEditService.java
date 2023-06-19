package ru.kalinichenko.softlink_logistic.service.order;

import org.springframework.stereotype.Service;
import ru.kalinichenko.softlink_logistic.dao.CargoRepository;
import ru.kalinichenko.softlink_logistic.dao.CityRepository;
import ru.kalinichenko.softlink_logistic.dao.OrderAddressRepository;
import ru.kalinichenko.softlink_logistic.dao.OrderRepository;
import ru.kalinichenko.softlink_logistic.entity.order.Order;
import ru.kalinichenko.softlink_logistic.logic.DeliveryCostCalculator;
import ru.kalinichenko.softlink_logistic.logic.distance.DistanceMatrixService;
import ru.kalinichenko.softlink_logistic.service.UserService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class OrderEditService {
    private final Integer PER_KM = 1000;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final CityRepository cityRepository;
    private final OrderAddressRepository orderAddressRepository;
    private final CargoRepository cargoRepository;

    private final DistanceMatrixService distanceMatrixService;

    public OrderEditService(UserService userService, OrderRepository orderRepository,
                            CityRepository cityRepository,
                            OrderAddressRepository orderAddressRepository,
                            CargoRepository cargoRepository, DistanceMatrixService distanceMatrixService) {
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.cityRepository = cityRepository;
        this.orderAddressRepository = orderAddressRepository;
        this.cargoRepository = cargoRepository;
        this.distanceMatrixService = distanceMatrixService;
    }

    public Order edit(Long id, Order order){
        order.setUser(userService.getAuthorized());
        order.setStatus(orderRepository.getReferenceById(id).getStatus());
        order.setPrice(
                BigDecimal.valueOf(
                        new DeliveryCostCalculator().calculateDeliveryCost(order)
                ).setScale(2, RoundingMode.HALF_UP)
        );

        order.getAddress().setDistance(
                (double) distanceMatrixService
                        .getByCityNames(
                                order.getAddress()
                                        .getCityFrom()
                                        .getName(),
                                order.getAddress()
                                        .getCityTo()
                                        .getName())
                        .getDistance() /PER_KM
        );

        order.setPrice(
                BigDecimal.valueOf(
                        new DeliveryCostCalculator().calculateDeliveryCost(order)
                ).setScale(2, RoundingMode.HALF_UP)
        );

        cityRepository.saveAll(List.of(
                order.getAddress().getCityFrom(),
                order.getAddress().getCityTo()
        ));

        orderAddressRepository.save(order.getAddress());
        cargoRepository.save(order.getCargo());

        return orderRepository.save(order);
    }

}
