package ru.kalinichenko.softlink_logistic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.kalinichenko.softlink_logistic.logic.DistanceCalculator;

@SpringBootApplication
public class SoftlinkLogisticApplication {

    // todo: google api key = AIzaSyA65lEHUEizIsNtlbNo-l2K18dT680nsaM
    public static void main(String[] args) {
        SpringApplication.run(SoftlinkLogisticApplication.class, args);
    }

}
