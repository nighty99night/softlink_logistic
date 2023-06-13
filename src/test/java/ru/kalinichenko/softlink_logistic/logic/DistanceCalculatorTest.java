package ru.kalinichenko.softlink_logistic.logic;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class DistanceCalculatorTest {

    @Test
    void getDistance() {

        assertDoesNotThrow(()->{
            new DistanceCalculator().getDistance("Samara", "Moscow");
        });

    }
}