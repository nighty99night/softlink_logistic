package ru.kalinichenko.softlink_logistic.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class ErrorControllerAdvice {
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegal(IllegalArgumentException e, HttpServletRequest request, RedirectAttributes attributes, Model model) {
        attributes.addFlashAttribute("errorMessage", e.getMessage());
        attributes.mergeAttributes(model.asMap());
        model.addAttribute("errorMessage", e.getMessage());
        model.mergeAttributes(attributes.asMap());
        return "redirect:" + request.getRequestURI();
    }
}
