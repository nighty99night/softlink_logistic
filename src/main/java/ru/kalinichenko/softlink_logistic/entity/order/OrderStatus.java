package ru.kalinichenko.softlink_logistic.entity.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class OrderStatus {
    @Id
    @Column(nullable = false, length = 20)
    private String name;
}
