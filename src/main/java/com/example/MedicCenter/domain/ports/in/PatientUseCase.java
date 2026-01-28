package com.example.MedicCenter.domain.ports.in;

import com.example.MedicCenter.domain.model.Patient;

import java.util.List;

public interface PatientUseCase {

    /**
     * Registra un nuevo paciente validando:
     * - Documento único
     * - Fecha de afiliación válida (no futura)
     * - Tipo de afiliación válido
     */
    Patient registerPatient(Patient patient);

    /**
     * Actualiza la información básica del paciente
     */
    Patient updatePatient(Long id, Patient patient);

    /**
     * Obtiene un paciente por ID
     */
    Patient getPatientById(Long id);

    /**
     * Lista todos los pacientes
     */
    List<Patient> getAllPatients();

    /**
     * Elimina un paciente
     */
    void deletePatient(Long id);
}
