package ru.kalinichenko.softlink_logistic.twogis.service;

import org.springframework.stereotype.Service;
import ru.kalinichenko.softlink_logistic.entity.order.address.City;
import ru.kalinichenko.softlink_logistic.twogis.response.geo_code.Item;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityLoadService {
    private final CityParser cityParser;
    public CityLoadService(CityParser cityParser) {
        this.cityParser = cityParser;
    }

    public List<City> loadByName(String name){
        List<Item> items = cityParser.allCitiesByName(name);
        return items.stream().map(item -> {
            City city = new City();
            city.setName(item.getName());
            return city;
        }).collect(Collectors.toList());
    }
}
