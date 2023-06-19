package ru.kalinichenko.softlink_logistic.twogis.response.geo_code;

import lombok.Data;

import java.util.Date;

@Data
public class Meta {

    private String api_version;
    private String code;
    private Date issue_date;
}
