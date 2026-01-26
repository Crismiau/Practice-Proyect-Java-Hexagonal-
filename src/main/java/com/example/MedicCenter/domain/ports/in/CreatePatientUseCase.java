package com.example.MedicCenter.domain.ports.in;

import com.example.MedicCenter.domain.model.Patient;

public interface CreatePatientUseCase {
    Patient createPatient(Patient patient);
}
