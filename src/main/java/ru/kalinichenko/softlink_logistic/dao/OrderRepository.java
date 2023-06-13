package ru.kalinichenko.softlink_logistic.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kalinichenko.softlink_logistic.entity.order.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
