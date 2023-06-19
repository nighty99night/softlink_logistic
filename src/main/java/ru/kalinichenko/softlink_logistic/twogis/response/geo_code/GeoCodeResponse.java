package ru.kalinichenko.softlink_logistic.twogis.response.geo_code;

import lombok.Data;

@Data
public class GeoCodeResponse {
    private Meta meta;
    private Result result;
}
