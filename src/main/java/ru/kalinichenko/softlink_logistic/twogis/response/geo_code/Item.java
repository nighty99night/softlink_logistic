package ru.kalinichenko.softlink_logistic.twogis.response.geo_code;

import lombok.Data;
import ru.kalinichenko.softlink_logistic.logic.distance.Point;

@Data
public class Item {
    private String full_name;
    private String id;
    private String name;
    private Point point;
    private String subtype;
    private String type;
}
