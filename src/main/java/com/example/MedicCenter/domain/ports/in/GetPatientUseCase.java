package com.example.MedicCenter.domain.ports.in;

import com.example.MedicCenter.domain.model.Patient;
import java.util.Optional;

public interface GetPatientUseCase {
    Optional<Patient> getPatient(Long id);
}
