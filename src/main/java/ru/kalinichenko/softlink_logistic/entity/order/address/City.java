package ru.kalinichenko.softlink_logistic.entity.order.address;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class City {
    @Id
    private String name;
}
