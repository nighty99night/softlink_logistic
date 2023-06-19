package ru.kalinichenko.softlink_logistic.twogis;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.kalinichenko.softlink_logistic.logic.distance.Point;
import ru.kalinichenko.softlink_logistic.twogis.service.CityParser;

@Component
@Log4j2
public class GeoCoder {
    private final CityParser cityParser;
    public GeoCoder(CityParser cityParser) {
        this.cityParser = cityParser;
    }
    public Point parsePointFromName(String name){
        return cityParser.allCitiesByName(name).get(0).getPoint();
    }
}
