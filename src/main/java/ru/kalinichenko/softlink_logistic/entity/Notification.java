package ru.kalinichenko.softlink_logistic.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recipient;
    private String subject;
    private String message;
    private LocalDateTime timestamp;

    // Конструкторы, геттеры, сеттеры и другие необходимые методы
}
