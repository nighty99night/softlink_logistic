package ru.kalinichenko.softlink_logistic.twogis.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kalinichenko.softlink_logistic.entity.order.address.City;
import ru.kalinichenko.softlink_logistic.twogis.service.CityLoadService;

import java.util.List;

@RestController
@Log4j2
public class CityController {

    private final CityLoadService cityLoadService;

    public CityController(CityLoadService cityLoadService) {
        this.cityLoadService = cityLoadService;
    }

    @GetMapping("/cities")
    public List<City> cities(@RequestParam("term") String term){
        log.info("load cities..");
        return cityLoadService.loadByName(term);
    }


}
