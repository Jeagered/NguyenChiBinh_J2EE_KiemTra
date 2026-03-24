package com.example.demo.service;

import com.example.demo.model.Doctor;
import com.example.demo.repository.DoctorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Page<Doctor> getDoctors(int page, int size, String keyword) {
        Pageable pageable = PageRequest.of(page, size);
        if (keyword == null || keyword.isBlank()) {
            return doctorRepository.findAll(pageable);
        }
        return doctorRepository.findByNameContainingIgnoreCase(keyword.trim(), pageable);
    }
}
