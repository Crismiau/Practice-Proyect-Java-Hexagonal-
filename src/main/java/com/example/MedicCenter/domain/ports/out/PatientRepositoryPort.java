package com.example.MedicCenter.domain.ports.out;

import com.example.MedicCenter.domain.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientRepositoryPort {

    Patient save(Patient patient);

    Optional<Patient> findById(Long id);

    Optional<Patient> findByDocumento(String documento);

    List<Patient> findAll();

    void deleteById(Long id);
}
