package ru.kalinichenko.softlink_logistic.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kalinichenko.softlink_logistic.entity.cargo.CargoType;

@Repository
public interface CargoTypeRepository extends JpaRepository<CargoType, String> {
}
