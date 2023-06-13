package ru.kalinichenko.softlink_logistic.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kalinichenko.softlink_logistic.entity.order.address.OrderAddress;

public interface OrderAddressRepository extends JpaRepository<OrderAddress, Long> {
}