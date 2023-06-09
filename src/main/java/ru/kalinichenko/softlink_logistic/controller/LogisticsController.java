package ru.kalinichenko.softlink_logistic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LogisticsController {

    @GetMapping("/")
    public String home(){
        return "calculate";
    }


    @PostMapping("/calculate")
    public String calculate(
            @RequestParam("cityFrom") String cityFrom,
            @RequestParam("cityTo") String cityTo,
            Model model) {

        // Здесь вы можете выполнить расчет стоимости доставки
        double deliveryCost = calculateDeliveryCost(cityFrom, cityTo);

        // Добавьте переменные в модель
        model.addAttribute("cityFrom", cityFrom);
        model.addAttribute("cityTo", cityTo);
        model.addAttribute("deliveryCost", deliveryCost);

        return "calculate"; // Вернуть имя шаблона с результатом расчета
    }

    private double calculateDeliveryCost(String cityFrom, String cityTo) {
        // Реализуйте логику расчета стоимости доставки здесь
        // Верните результат расчета
        return 0.0;
    }
}
