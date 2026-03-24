package com.example.demo.controller;

import com.example.demo.model.Doctor;
import com.example.demo.service.DoctorService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final DoctorService doctorService;

    public HomeController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping({"/", "/home", "/courses"})
    public String home(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "") String keyword,
                       Model model) {
        Page<Doctor> doctorPage = doctorService.getDoctors(page, 5, keyword);
        model.addAttribute("doctorPage", doctorPage);
        model.addAttribute("keyword", keyword);
        return "home";
    }
}
