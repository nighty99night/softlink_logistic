package ru.kalinichenko.softlink_logistic.entity.auth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.kalinichenko.softlink_logistic.entity.User;

@Entity
@Table
@Getter
@Setter
public class ConfirmationCode {
    @Id
    private String code;
    @ManyToOne
    private User user;
}
