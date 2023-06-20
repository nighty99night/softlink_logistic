package ru.kalinichenko.softlink_logistic.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kalinichenko.softlink_logistic.dao.ConfirmationCodeRepository;
import ru.kalinichenko.softlink_logistic.dao.UserRepository;
import ru.kalinichenko.softlink_logistic.entity.User;
import ru.kalinichenko.softlink_logistic.entity.auth.ConfirmationCode;
import ru.kalinichenko.softlink_logistic.service.EmailNotificationService;

import java.util.Optional;
import java.util.Random;

@Controller
@RequestMapping("/auth/register")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final ConfirmationCodeRepository confirmationCodeRepository;

    private final EmailNotificationService notificationService;

    public UserController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          ConfirmationCodeRepository confirmationCodeRepository, EmailNotificationService notificationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.confirmationCodeRepository = confirmationCodeRepository;
        this.notificationService = notificationService;
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping
    public String registerUser(@ModelAttribute("user") User user, Model model, HttpSession httpSession) {
        User finalUser = user;
        if (userRepository.exists(new Example<>() {
            @Override
            public User getProbe() {
                return finalUser;
            }
            @Override
            public ExampleMatcher getMatcher() {
                return ExampleMatcher.matchingAny()
                        .withMatcher("email", ExampleMatcher.GenericPropertyMatcher::exact)
                        .withMatcher("username", ExampleMatcher.GenericPropertyMatcher::exact);
            }
        })) {
            model.addAttribute("errorMessage",
                    "Пользователь с таким username или email уже существует");
            return "auth/register";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        ConfirmationCode code = new ConfirmationCode();
        code.setCode(String.valueOf(new Random().nextLong(1234214)) +
                (user.getEmail() + user.getPassword() + user.getUsername()).hashCode()
        );
        notificationService.sendEmailNotification(user.getEmail(), "Confirm Registration",
                "Пожалуйста, используйте этот код для подтверждения регистрации: "  + code.getCode());
        user = userRepository.save(user);
        code.setUser(user);
        confirmationCodeRepository.save(code);
        httpSession.setAttribute("email", user.getEmail());
        return "redirect:auth/register/confirm";
    }

    @GetMapping("/confirm")
    public String confirmPage(Model model, HttpSession session){
        String email = (String) session.getAttribute("email");
        model.addAttribute("email", email);
        return "auth/confirm";
    }

    @PostMapping("/confirm")
    public String confirmRegistration(@RequestParam(name = "code") String code) {
        Optional<ConfirmationCode> confirmationCode = confirmationCodeRepository.findById(code);
        if (confirmationCode.isEmpty()) {
            return "auth/confirm";
        }
        User user = confirmationCode.get().getUser();
        user.setEnabled(true);
        userRepository.save(user);
        return "redirect:auth/login";
    }
}

