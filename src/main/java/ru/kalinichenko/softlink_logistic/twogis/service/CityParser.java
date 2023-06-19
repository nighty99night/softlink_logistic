package ru.kalinichenko.softlink_logistic.twogis.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kalinichenko.softlink_logistic.twogis.response.geo_code.GeoCodeResponse;
import ru.kalinichenko.softlink_logistic.twogis.response.geo_code.Item;
import ru.kalinichenko.softlink_logistic.twogis.response.geo_code.Result;

import java.util.*;

@Service
@Log4j2
public class CityParser {
    private final RestTemplate restTemplate;

    @Value("${2gis.api.geocoder.key}")
    private String KEY;
    private final String URL = "https://catalog.api.2gis.com/3.0/items/geocode?q={name}&fields={fields}&type={type}&key={key}";

    public CityParser(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Item> allCitiesByName(String name){
        Map<String, String> map = new HashMap<>();
        map.put("name",name);
        map.put("fields", "items.point");
        map.put("key", KEY);
        map.put("type", "adm_div.city");
        ResponseEntity<GeoCodeResponse> response = restTemplate.getForEntity(
                URL,
                GeoCodeResponse.class,
                map
        );
        log.info(response);
        if(response.getStatusCode().is4xxClientError()){
            throw new IllegalArgumentException("Некорректное название города");
        }
        Result result = Objects.requireNonNull(response.getBody()).getResult();
        if(result == null){
            throw new IllegalArgumentException("Некорректное название города");
        }
        return Arrays.stream(result.getItems()).toList();
    }
}
