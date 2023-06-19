package ru.kalinichenko.softlink_logistic.logic;

import lombok.extern.log4j.Log4j2;
import ru.kalinichenko.softlink_logistic.entity.cargo.Cargo;
import ru.kalinichenko.softlink_logistic.entity.order.Order;

@Log4j2
public class DeliveryCostCalculator {

    private static final double BASE_RATE = 2000; // Базовая ставка доставки
    private static final double RATE_PER_KM = 30; // Стоимость доставки за километр
    private static final double RATE_PER_DENCITY = 20; // Стоимость доставки за килограмм

    public Double calculateDeliveryCost(Order order) {

        Cargo cargo = order.getCargo();

        double density = cargo.getWeight()/cargo.getVolume() ;
        double d = order.getAddress().getDistance();
        double t = cargo.getType().getBasePrice().doubleValue();

        double price = (BASE_RATE + t) + d * RATE_PER_KM + (density* RATE_PER_DENCITY);
        log.info("price = " + price);
        return price;
    }
}
