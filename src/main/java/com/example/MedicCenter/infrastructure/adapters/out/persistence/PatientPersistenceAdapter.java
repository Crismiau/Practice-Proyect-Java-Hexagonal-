package com.example.MedicCenter.infrastructure.adapters.out.persistence;

import com.example.MedicCenter.domain.model.Patient;
import com.example.MedicCenter.domain.ports.out.PatientRepositoryPort;
import com.example.MedicCenter.infrastructure.adapters.out.persistence.entities.PatientEntity;
import com.example.MedicCenter.infrastructure.adapters.out.persistence.mappers.PatientMapper;
import com.example.MedicCenter.infrastructure.adapters.out.persistence.repositories.PatientJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PatientPersistenceAdapter implements PatientRepositoryPort {

    private final PatientJpaRepository jpaRepository;
    private final PatientMapper mapper;

    @Override
    public Patient save(Patient patient) {
        PatientEntity entity = mapper.toEntity(patient);
        PatientEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Patient> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Patient> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Patient> findByDocumento(String documento) {
        return jpaRepository.findByDocumento(documento)
                .map(mapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
