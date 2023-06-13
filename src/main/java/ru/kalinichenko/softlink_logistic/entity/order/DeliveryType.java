package ru.kalinichenko.softlink_logistic.entity.order;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class DeliveryType {
    @Id
    private String name;
}
