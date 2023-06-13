package ru.kalinichenko.softlink_logistic.logic;

import ru.kalinichenko.softlink_logistic.entity.order.Order;

public class DeliveryCostCalculator {

    private static final double BASE_RATE = 10.0; // Базовая ставка доставки
    private static final double RATE_PER_KM = 0.5; // Стоимость доставки за километр
    private static final double RATE_PER_KG = 2.0; // Стоимость доставки за килограмм

    public double calculateDeliveryCost(Order order) {
        double distanceCost = order.getAddress().getDistance() * RATE_PER_KM;
        double weightCost = order.getCargo().getWeight() * RATE_PER_KG;
        double totalCost = BASE_RATE + distanceCost + weightCost;
        return totalCost;
    }
}
