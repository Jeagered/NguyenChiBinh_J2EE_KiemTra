package com.example.demo.controller;

import com.example.demo.model.Department;
import com.example.demo.model.Doctor;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.DoctorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/doctors")
public class AdminDoctorController {

    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;

    public AdminDoctorController(DoctorRepository doctorRepository, DepartmentRepository departmentRepository) {
        this.doctorRepository = doctorRepository;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping
    public String list(Model model) {
        List<Doctor> doctors = doctorRepository.findAll();
        model.addAttribute("doctors", doctors);
        return "admin/doctors";
    }

    @GetMapping("/create")
    public String showCreate(Model model) {
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("departments", departmentRepository.findAll());
        return "admin/doctor-form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Doctor doctor, @RequestParam("departmentId") Long departmentId) {
        doctor.setDepartment(departmentRepository.findById(departmentId).orElseThrow());
        doctorRepository.save(doctor);
        return "redirect:/admin/doctors";
    }

    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable Long id, Model model) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow();
        model.addAttribute("doctor", doctor);
        model.addAttribute("departments", departmentRepository.findAll());
        return "admin/doctor-form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute Doctor doctor, @RequestParam("departmentId") Long departmentId) {
        doctor.setId(id);
        doctor.setDepartment(departmentRepository.findById(departmentId).orElseThrow());
        doctorRepository.save(doctor);
        return "redirect:/admin/doctors";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        doctorRepository.deleteById(id);
        return "redirect:/admin/doctors";
    }

    @ModelAttribute("allDepartments")
    public List<Department> allDepartments() {
        return departmentRepository.findAll();
    }
}
