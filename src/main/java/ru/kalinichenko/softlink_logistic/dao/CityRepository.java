package ru.kalinichenko.softlink_logistic.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kalinichenko.softlink_logistic.entity.order.address.City;

public interface CityRepository extends JpaRepository<City, String> {
}