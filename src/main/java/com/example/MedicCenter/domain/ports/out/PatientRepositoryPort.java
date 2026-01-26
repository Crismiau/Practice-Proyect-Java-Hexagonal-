package com.example.MedicCenter.domain.ports.out;

import com.example.MedicCenter.domain.model.Patient;
import java.util.Optional;

public interface PatientRepositoryPort {
    Patient save(Patient patient);

    Optional<Patient> findById(Long id);

    Optional<Patient> findByDni(String dni);

    boolean existsByDni(String dni);

    boolean existsById(Long id);
}
