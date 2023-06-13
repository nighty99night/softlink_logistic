package ru.kalinichenko.softlink_logistic.controller.notif;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kalinichenko.softlink_logistic.entity.Notification;
import ru.kalinichenko.softlink_logistic.service.NotificationService;

import java.util.List;

@Controller
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/notifications")
    public String showNotifications(Model model) {
        List<Notification> notifications = notificationService.getNotificationsForCurrentUser();
        model.addAttribute("notifications", notifications);
        return "notifications";
    }
}
