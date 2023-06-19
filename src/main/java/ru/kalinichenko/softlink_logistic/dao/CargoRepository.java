package ru.kalinichenko.softlink_logistic.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kalinichenko.softlink_logistic.entity.cargo.Cargo;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
}