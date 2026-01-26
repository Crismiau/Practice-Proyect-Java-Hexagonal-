package com.example.MedicCenter.infrastructure.adapters.out.persistence;

import com.example.MedicCenter.domain.model.Patient;
import com.example.MedicCenter.domain.ports.out.PatientRepositoryPort;
import com.example.MedicCenter.infrastructure.adapters.out.persistence.entities.PatientEntity;
import com.example.MedicCenter.infrastructure.adapters.out.persistence.repositories.JpaPatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PatientPersistenceAdapter implements PatientRepositoryPort {

    private final JpaPatientRepository repository;

    @Override
    public Patient save(Patient patient) {
        PatientEntity entity = PatientEntity.fromDomain(patient);
        return repository.save(entity).toDomain();
    }

    @Override
    public Optional<Patient> findById(Long id) {
        return repository.findById(id).map(PatientEntity::toDomain);
    }

    @Override
    public Optional<Patient> findByDni(String dni) {
        return repository.findByDni(dni).map(PatientEntity::toDomain);
    }

    @Override
    public boolean existsByDni(String dni) {
        return repository.existsByDni(dni);
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
