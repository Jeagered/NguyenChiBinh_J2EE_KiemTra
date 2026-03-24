package com.example.demo.repository;

import com.example.demo.model.Appointment;
import com.example.demo.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientOrderByAppointmentDateDesc(Patient patient);
}
