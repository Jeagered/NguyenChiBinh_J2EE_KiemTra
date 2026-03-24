package com.example.demo.controller;

import com.example.demo.model.Doctor;
import com.example.demo.service.DoctorService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class DoctorApiController {

    private final DoctorService doctorService;

    public DoctorApiController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/api/doctors/search")
    public List<Map<String, Object>> search(@RequestParam(defaultValue = "") String keyword) {
        Page<Doctor> doctors = doctorService.getDoctors(0, 50, keyword);
        return doctors.stream()
                .map(d -> Map.<String, Object>of(
                        "id", d.getId(),
                        "name", d.getName(),
                        "specialty", d.getSpecialty(),
                        "department", d.getDepartment() != null ? d.getDepartment().getName() : "",
                        "image", d.getImage() == null ? "" : d.getImage()))
                .toList();
    }
}
