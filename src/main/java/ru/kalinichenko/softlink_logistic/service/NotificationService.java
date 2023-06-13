package ru.kalinichenko.softlink_logistic.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.kalinichenko.softlink_logistic.dao.NotificationRepository;
import ru.kalinichenko.softlink_logistic.dao.UserRepository;
import ru.kalinichenko.softlink_logistic.entity.Notification;
import ru.kalinichenko.softlink_logistic.entity.User;

import java.util.List;

@Service
public class NotificationService {
    // ...

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public List<Notification> getNotificationsForCurrentUser() {
        // Получите текущего пользователя
        User currentUser = getCurrentUser();

        // Используйте репозиторий для получения уведомлений текущего пользователя
        return notificationRepository.findByRecipient(currentUser.getUsername());
    }

    // Вспомогательный метод для получения текущего пользователя
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByUsername(authentication.getName());


        /*if (authentication != null && authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }*/
//        throw new IllegalStateException("Unable to retrieve current user.");
    }

    // ...
}
