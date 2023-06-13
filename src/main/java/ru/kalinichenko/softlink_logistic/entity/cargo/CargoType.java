package ru.kalinichenko.softlink_logistic.entity.cargo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class CargoType {
    @Id
    private String name;
}
