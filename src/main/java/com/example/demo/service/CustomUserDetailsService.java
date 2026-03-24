package com.example.demo.service;

import com.example.demo.model.Patient;
import com.example.demo.model.Role;
import com.example.demo.repository.PatientRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PatientRepository patientRepository;

    public CustomUserDetailsService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Patient patient = patientRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        Set<GrantedAuthority> authorities = patient.getRoles().stream()
                .map(Role::getName)
                .map(name -> new SimpleGrantedAuthority("ROLE_" + name))
                .collect(Collectors.toSet());
        return User.withUsername(patient.getUsername())
                .password(patient.getPassword())
                .authorities(authorities)
                .build();
    }
}
