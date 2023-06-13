package ru.kalinichenko.softlink_logistic.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kalinichenko.softlink_logistic.entity.order.DeliveryType;

public interface DeliveryTypeRepository extends JpaRepository<DeliveryType, String> {
}