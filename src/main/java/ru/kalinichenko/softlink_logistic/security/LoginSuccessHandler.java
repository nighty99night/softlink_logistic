package ru.kalinichenko.softlink_logistic.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler{

    private final HttpSession httpSession;

    public LoginSuccessHandler(HttpSession httpSession) {
        this.httpSession = httpSession;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        httpSession.setAttribute("username", authentication.getName());
        response.sendRedirect("/");
    }
}
