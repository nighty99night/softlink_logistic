package ru.kalinichenko.softlink_logistic.entity.order.address;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class OrderAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private City cityFrom;
    @ManyToOne
    private City cityTo;
    private double distance;
}
