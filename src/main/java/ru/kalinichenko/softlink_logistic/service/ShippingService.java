package ru.kalinichenko.softlink_logistic.service;

import org.springframework.stereotype.Service;
import ru.kalinichenko.softlink_logistic.entity.order.Order;

@Service
public class ShippingService {
    private static final double BASE_PRICE_PER_KG = 10.0;
    private static final double DISTANCE_FACTOR = 0.5;

    public double calculateShippingCost(Order order) {
        double weight = order.getCargo().getWeight();
        double distance = order.getAddress().getDistance();

        // Рассчет стоимости доставки на основе веса и расстояния
        double price = weight * BASE_PRICE_PER_KG + distance * DISTANCE_FACTOR;

        // Дополнительные расчеты или логика...

        return price;
    }
}
