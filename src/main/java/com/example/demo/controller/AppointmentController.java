package com.example.demo.controller;

import com.example.demo.model.Appointment;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.service.AppointmentService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final DoctorRepository doctorRepository;

    public AppointmentController(AppointmentService appointmentService, DoctorRepository doctorRepository) {
        this.appointmentService = appointmentService;
        this.doctorRepository = doctorRepository;
    }

    @GetMapping("/enroll/{doctorId}")
    public String showBooking(@PathVariable Long doctorId, Model model) {
        model.addAttribute("doctor", doctorRepository.findById(doctorId).orElseThrow());
        return "book-appointment";
    }

    @PostMapping("/enroll/{doctorId}")
    public String book(@PathVariable Long doctorId,
                       @RequestParam("appointmentDate") LocalDate appointmentDate,
                       Authentication authentication) {
        appointmentService.book(authentication.getName(), doctorId, appointmentDate);
        return "redirect:/my-appointments?success";
    }

    @GetMapping("/my-appointments")
    public String myAppointments(Authentication authentication, Model model) {
        List<Appointment> appointments = appointmentService.getMyAppointments(authentication.getName());
        model.addAttribute("appointments", appointments);
        return "my-appointments";
    }
}
