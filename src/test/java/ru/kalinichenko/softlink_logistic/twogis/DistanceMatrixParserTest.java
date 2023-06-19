package ru.kalinichenko.softlink_logistic.twogis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kalinichenko.softlink_logistic.twogis.response.distance.DistanceMatrix;
import ru.kalinichenko.softlink_logistic.twogis.service.DistanceMatrixParser;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DistanceMatrixParserTest {

    @Autowired
    private DistanceMatrixParser distanceMatrixParser;

    @Autowired
    private GeoCoder geoCoder;
    @Test
    void parse() {
        assertDoesNotThrow(()->{

            DistanceMatrix d = distanceMatrixParser.parse(
                    geoCoder.parsePointFromName("Самара"),
                    geoCoder.parsePointFromName("Москва")
            );
            System.out.println(d);
        });
    }
}