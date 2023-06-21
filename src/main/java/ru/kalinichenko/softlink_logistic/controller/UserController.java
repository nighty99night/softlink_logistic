package ru.kalinichenko.softlink_logistic.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kalinichenko.softlink_logistic.dao.ConfirmationCodeRepository;
import ru.kalinichenko.softlink_logistic.dao.UserRepository;
import ru.kalinichenko.softlink_logistic.entity.User;
import ru.kalinichenko.softlink_logistic.entity.auth.ConfirmationCode;
import ru.kalinichenko.softlink_logistic.service.UserService;

import java.util.Optional;

@Controller
@RequestMapping("/auth/register")
public class UserController {

    private final UserRepository userRepository;

    private final ConfirmationCodeRepository confirmationCodeRepository;

    private final UserService userService;

    public UserController(UserRepository userRepository,
                          ConfirmationCodeRepository confirmationCodeRepository,
                          UserService userService) {
        this.userRepository = userRepository;
        this.confirmationCodeRepository = confirmationCodeRepository;
        this.userService = userService;
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping
    public String registerUser(@ModelAttribute("user") User user, Model model, HttpSession httpSession) {
        if (userService.exists(user)) {
            model.addAttribute("errorMessage",
                    "Пользователь с таким username или email уже существует");
            return "auth/register";
        }
        user = userService.create(user);
        httpSession.setAttribute("email", user.getEmail());
        return "redirect:/auth/register/confirm";
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
        return "redirect:/auth/login";
    }
}

