package ru.kalinichenko.softlink_logistic.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kalinichenko.softlink_logistic.dao.ConfirmationCodeRepository;
import ru.kalinichenko.softlink_logistic.dao.UserRepository;
import ru.kalinichenko.softlink_logistic.entity.User;
import ru.kalinichenko.softlink_logistic.entity.auth.ConfirmationCode;

import java.util.Random;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailNotificationService notificationService;

    private final ConfirmationCodeRepository confirmationCodeRepository;
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       EmailNotificationService notificationService,
                       ConfirmationCodeRepository confirmationCodeRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.notificationService = notificationService;
        this.confirmationCodeRepository = confirmationCodeRepository;
    }


    public User getByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User getAuthorized(){
        return getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public boolean exists(User user){
        return userRepository.exists(new Example<>() {
            @Override
            public User getProbe() {
                return user;
            }
            @Override
            public ExampleMatcher getMatcher() {
                return ExampleMatcher.matchingAny()
                        .withMatcher("email", ExampleMatcher.GenericPropertyMatcher::exact)
                        .withMatcher("username", ExampleMatcher.GenericPropertyMatcher::exact);
            }
        });
    }
    public User create(User user){
        if(exists(user)){
            throw new IllegalArgumentException("Пользователь уже существует: проверьте имя или почту");
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
        return user;
    }
}
