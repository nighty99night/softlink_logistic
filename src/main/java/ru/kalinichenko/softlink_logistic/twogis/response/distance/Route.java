package ru.kalinichenko.softlink_logistic.twogis.response.distance;

import lombok.Data;

@Data
public class Route {

    private Double distance;
    private String duration;
    private Integer sourceId;
    private String status;
    private Integer targetId;
}
