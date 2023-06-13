package ru.kalinichenko.softlink_logistic.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/auth/login")
    public String showLoginForm() {
        return "auth/login";
    }
}
