package com.example.MedicCenter.infrastructure.adapters.out.persistence.repositories;

import com.example.MedicCenter.infrastructure.adapters.out.persistence.entities.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientJpaRepository extends JpaRepository<PatientEntity, Long> {

    Optional<PatientEntity> findByDocumento(String documento);
}
