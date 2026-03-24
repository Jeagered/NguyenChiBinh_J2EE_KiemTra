package com.example.demo.service;

import com.example.demo.model.Appointment;
import com.example.demo.model.Doctor;
import com.example.demo.model.Patient;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    @Transactional
    public void book(String username, Long doctorId, LocalDate date) {
        Patient patient = patientRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(date);
        appointmentRepository.save(appointment);
    }

    public List<Appointment> getMyAppointments(String username) {
        Patient patient = patientRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        return appointmentRepository.findByPatientOrderByAppointmentDateDesc(patient);
    }
}
