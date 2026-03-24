package com.example.demo.service;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.Patient;
import com.example.demo.model.Role;
import com.example.demo.repository.PatientRepository;
import com.example.demo.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public PatientService(PatientRepository patientRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.patientRepository = patientRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerPatient(RegisterRequest request) {
        if (patientRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (patientRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        Role patientRole = roleRepository.findByName("PATIENT")
                .orElseThrow(() -> new IllegalStateException("PATIENT role is missing"));
        Patient patient = new Patient();
        patient.setUsername(request.getUsername());
        patient.setPassword(passwordEncoder.encode(request.getPassword()));
        patient.setEmail(request.getEmail());
        patient.setRoles(Set.of(patientRole));
        patientRepository.save(patient);
    }
}
