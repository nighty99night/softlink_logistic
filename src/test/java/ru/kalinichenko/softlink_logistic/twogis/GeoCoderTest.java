package ru.kalinichenko.softlink_logistic.twogis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kalinichenko.softlink_logistic.logic.distance.Point;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GeoCoderTest {

    @Autowired
    private GeoCoder geoCoder;

    @Test
    void parsePointFromName() {
        assertDoesNotThrow(()->{
            Point point = geoCoder.parsePointFromName("Самара");
            System.out.println(point.getLat() + " " + point.getLon());
        });
    }
}