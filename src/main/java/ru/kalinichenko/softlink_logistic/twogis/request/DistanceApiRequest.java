package ru.kalinichenko.softlink_logistic.twogis.request;

import lombok.Data;
import ru.kalinichenko.softlink_logistic.logic.distance.Point;

@Data
public class DistanceApiRequest {
    private Point[] points;
    private Integer[] sources;
    private Integer[] targets;
    private String startTime;
    private String mode;
    private String type;
    private Boolean allow_locked_roads;
}
