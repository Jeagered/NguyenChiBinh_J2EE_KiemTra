package com.example.demo.config;

import com.example.demo.model.Department;
import com.example.demo.model.Doctor;
import com.example.demo.model.Patient;
import com.example.demo.model.Role;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.PatientRepository;
import com.example.demo.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final PatientRepository patientRepository;
    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository, PatientRepository patientRepository,
                           DepartmentRepository departmentRepository, DoctorRepository doctorRepository,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.patientRepository = patientRepository;
        this.departmentRepository = departmentRepository;
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        Role adminRole = roleRepository.findByName("ADMIN").orElseGet(() -> {
            Role role = new Role();
            role.setName("ADMIN");
            return roleRepository.save(role);
        });
        Role patientRole = roleRepository.findByName("PATIENT").orElseGet(() -> {
            Role role = new Role();
            role.setName("PATIENT");
            return roleRepository.save(role);
        });

        if (!patientRepository.existsByUsername("admin")) {
            Patient admin = new Patient();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@clinic.com");
            admin.setRoles(Set.of(adminRole));
            patientRepository.save(admin);
        }

        if (!patientRepository.existsByUsername("patient")) {
            Patient patient = new Patient();
            patient.setUsername("patient");
            patient.setPassword(passwordEncoder.encode("patient123"));
            patient.setEmail("patient@clinic.com");
            patient.setRoles(Set.of(patientRole));
            patientRepository.save(patient);
        }

        if (doctorRepository.count() == 0) {
            Department cardiology = departmentRepository.findByName("Cardiology")
                    .orElseGet(() -> departmentRepository.save(new Department("Cardiology")));
            Department dermatology = departmentRepository.findByName("Dermatology")
                    .orElseGet(() -> departmentRepository.save(new Department("Dermatology")));
            Department pediatrics = departmentRepository.findByName("Pediatrics")
                    .orElseGet(() -> departmentRepository.save(new Department("Pediatrics")));

            doctorRepository.saveAll(List.of(
                    createDoctor("Nguyen Van A", "https://i.pravatar.cc/300?img=12", "Tim mach", cardiology),
                    createDoctor("Tran Thi B", "https://i.pravatar.cc/300?img=20", "Noi tong quat", cardiology),
                    createDoctor("Le Van C", "https://i.pravatar.cc/300?img=36", "Da lieu", dermatology),
                    createDoctor("Pham Thi D", "https://i.pravatar.cc/300?img=25", "Nhi khoa", pediatrics),
                    createDoctor("Do Van E", "https://i.pravatar.cc/300?img=45", "Than kinh", cardiology),
                    createDoctor("Bui Thi F", "https://i.pravatar.cc/300?img=48", "Da lieu", dermatology),
                    createDoctor("Hoang Van G", "https://i.pravatar.cc/300?img=15", "Nhi khoa", pediatrics)
            ));
        }
    }

    private Doctor createDoctor(String name, String image, String specialty, Department department) {
        Doctor doctor = new Doctor();
        doctor.setName(name);
        doctor.setImage(image);
        doctor.setSpecialty(specialty);
        doctor.setDepartment(department);
        return doctor;
    }
}
