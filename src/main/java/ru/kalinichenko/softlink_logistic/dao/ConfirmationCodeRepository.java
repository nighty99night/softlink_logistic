package ru.kalinichenko.softlink_logistic.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kalinichenko.softlink_logistic.entity.auth.ConfirmationCode;

public interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCode, String> {
}