package ru.kalinichenko.softlink_logistic.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/home")
public class MainController {
    @GetMapping
    public String getPage(HttpSession httpSession){
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("username in session: "+ httpSession.getAttribute("username"));
//        httpSession.setAttribute("username", username);
        return "main";
    }

}
