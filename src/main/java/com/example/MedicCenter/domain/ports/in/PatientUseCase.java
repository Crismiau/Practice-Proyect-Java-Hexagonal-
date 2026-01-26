package com.example.MedicCenter.domain.ports.in;

import com.example.MedicCenter.domain.model.Patient;
import java.util.Optional;

public interface PatientUseCase {
    Patient registerPatient(Patient patient);

    Patient updatePatient(Patient patient);

    Optional<Patient> getPatientById(Long id);

    Optional<Patient> getPatientByDni(String dni);
}
