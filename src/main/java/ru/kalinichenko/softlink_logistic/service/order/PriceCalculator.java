package ru.kalinichenko.softlink_logistic.service.order;

import org.springframework.stereotype.Service;
import ru.kalinichenko.softlink_logistic.entity.order.Order;
import ru.kalinichenko.softlink_logistic.logic.DeliveryCostCalculator;
import ru.kalinichenko.softlink_logistic.logic.distance.DistanceMatrixService;
import ru.kalinichenko.softlink_logistic.twogis.response.distance.DistanceMatrix;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PriceCalculator {
    private final DistanceMatrixService distanceMatrixService;

    public PriceCalculator(DistanceMatrixService distanceMatrixService) {
        this.distanceMatrixService = distanceMatrixService;
    }
    public BigDecimal calculate(Order order){
        DistanceMatrix distanceMatrix = distanceMatrixService
                .getByCityNames(
                        order.getAddress()
                                .getCityFrom()
                                .getName(),
                        order.getAddress()
                                .getCityTo()
                                .getName());

        if(distanceMatrix.getDistance().intValue() == 0){
            throw new IllegalArgumentException("Не удалось проложить маршрут");
        }

        order.getAddress().setDistance(
                (double) distanceMatrix.getDistance() /DistanceCalculator.PER_KM
        );
        return BigDecimal.valueOf(
                new DeliveryCostCalculator().calculateDeliveryCost(order)
        ).setScale(2, RoundingMode.HALF_UP);

    }
}
