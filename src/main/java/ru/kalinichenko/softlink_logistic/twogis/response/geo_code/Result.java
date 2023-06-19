package ru.kalinichenko.softlink_logistic.twogis.response.geo_code;

import lombok.Data;

@Data
public class Result {
    private Item[] items;
    private int total;
}
