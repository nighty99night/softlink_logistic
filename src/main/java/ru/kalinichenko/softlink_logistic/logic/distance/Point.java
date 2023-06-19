package ru.kalinichenko.softlink_logistic.logic.distance;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

@Data
@JsonTypeName(value = "point")
public class Point {
    @JsonProperty(value = "lat")
    private Double lat;
    @JsonProperty(value = "lon")
    private Double lon;
}
