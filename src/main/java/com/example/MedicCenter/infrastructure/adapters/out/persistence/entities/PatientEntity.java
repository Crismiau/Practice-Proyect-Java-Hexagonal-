package com.example.MedicCenter.infrastructure.adapters.out.persistence.entities;

import com.example.MedicCenter.domain.model.Patient;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "patients")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dni;
    private String name;
    private Integer age;
    private String phone;
    private String email;
    private String status;

    public static PatientEntity fromDomain(Patient patient) {
        return PatientEntity.builder()
                .id(patient.getId())
                .dni(patient.getDni())
                .name(patient.getName())
                .age(patient.getAge())
                .phone(patient.getPhone())
                .email(patient.getEmail())
                .status(patient.getStatus())
                .build();
    }

    public Patient toDomain() {
        return new Patient(id, dni, name, age, phone, email, status);
    }
}
