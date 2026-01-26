package com.example.MedicCenter.infrastructure.adapters.out.persistence.repositories;

import com.example.MedicCenter.infrastructure.adapters.out.persistence.entities.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JpaPatientRepository extends JpaRepository<PatientEntity, Long> {
    Optional<PatientEntity> findByDni(String dni);

    boolean existsByDni(String dni);
}
