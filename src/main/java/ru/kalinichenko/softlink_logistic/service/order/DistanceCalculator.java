package ru.kalinichenko.softlink_logistic.service.order;

import org.springframework.stereotype.Service;
import ru.kalinichenko.softlink_logistic.entity.order.address.OrderAddress;
import ru.kalinichenko.softlink_logistic.logic.distance.DistanceMatrixService;
import ru.kalinichenko.softlink_logistic.twogis.response.distance.DistanceMatrix;

@Service
public class DistanceCalculator {

    public static final Integer PER_KM = 1000;

    private final DistanceMatrixService distanceMatrixService;

    public DistanceCalculator(DistanceMatrixService distanceMatrixService) {
        this.distanceMatrixService = distanceMatrixService;
    }

    public Double calc(OrderAddress address) {
        DistanceMatrix distanceMatrix = distanceMatrixService
                .getByCityNames(
                        address
                                .getCityFrom()
                                .getName(),
                        address
                                .getCityTo()
                                .getName()
                );

        if (distanceMatrix.getDistance().intValue() == 0) {
            throw new IllegalArgumentException("Не удалось проложить маршрут");
        }
        address.setDistance(
                (double) distanceMatrix.getDistance()
        );
        return distanceMatrix.getDistance().doubleValue() / PER_KM;
    }
}
