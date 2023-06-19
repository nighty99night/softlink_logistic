package ru.kalinichenko.softlink_logistic.logic.distance;

import org.springframework.stereotype.Component;
import ru.kalinichenko.softlink_logistic.twogis.GeoCoder;
import ru.kalinichenko.softlink_logistic.twogis.response.distance.DistanceMatrix;
import ru.kalinichenko.softlink_logistic.twogis.service.DistanceMatrixParser;

@Component
public class DistanceMatrixService {

    private final DistanceMatrixParser parser;
    private final GeoCoder geoCoder;

    public DistanceMatrixService(DistanceMatrixParser parser,
                                 GeoCoder geoCoder) {
        this.parser = parser;
        this.geoCoder = geoCoder;
    }

    public DistanceMatrix getByCityNames(String c1, String c2){
        return parser.parse(geoCoder.parsePointFromName(c1), geoCoder.parsePointFromName(c2));
    }
}
