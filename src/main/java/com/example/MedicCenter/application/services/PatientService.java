package com.example.MedicCenter.application.services;

import com.example.MedicCenter.domain.exception.BusinessRuleException;
import com.example.MedicCenter.domain.exception.ResourceNotFoundException;
import com.example.MedicCenter.domain.model.Patient;
import com.example.MedicCenter.domain.ports.in.PatientUseCase;
import com.example.MedicCenter.domain.ports.out.PatientRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService implements PatientUseCase {

    private final PatientRepositoryPort repository;

    @Override
    public Patient registerPatient(Patient patient) {
        if (repository.existsByDni(patient.getDni())) {
            throw new BusinessRuleException("DNI already exists: " + patient.getDni());
        }
        if (patient.getAge() <= 0 || patient.getAge() >= 120) {
            throw new BusinessRuleException(
                    "Invalid age provided: " + patient.getAge() + ". Must be between 1 and 119.");
        }
        patient.setStatus("ACTIVO");
        return repository.save(patient);
    }

    @Override
    public Patient updatePatient(Patient patient) {
        if (!repository.existsById(patient.getId())) {
            throw new ResourceNotFoundException("Patient not found with ID: " + patient.getId());
        }
        return repository.save(patient);
    }

    @Override
    public Optional<Patient> getPatientById(Long id) {
        return Optional.ofNullable(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + id)));
    }

    @Override
    public Optional<Patient> getPatientByDni(String dni) {
        return Optional.ofNullable(repository.findByDni(dni)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with DNI: " + dni)));
    }
}
