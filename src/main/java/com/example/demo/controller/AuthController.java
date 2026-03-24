package com.example.demo.controller;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final PatientService patientService;

    public AuthController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute RegisterRequest registerRequest, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        try {
            patientService.registerPatient(registerRequest);
            return "redirect:/login?registered";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
