package ru.kalinichenko.softlink_logistic.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.kalinichenko.softlink_logistic.dao.UserRepository;
import ru.kalinichenko.softlink_logistic.entity.User;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User getByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User getAuthorized(){
        return getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
