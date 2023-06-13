package ru.kalinichenko.softlink_logistic.entity.cargo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double weight;
    @ManyToOne
    private CargoType type;
    private double volume;

}
