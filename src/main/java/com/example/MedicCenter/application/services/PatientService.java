package com.example.MedicCenter.application.services;

import com.example.MedicCenter.domain.exception.BusinessRuleException;
import com.example.MedicCenter.domain.exception.ResourceNotFoundException;
import com.example.MedicCenter.domain.model.Patient;
import com.example.MedicCenter.domain.ports.in.PatientUseCase;
import com.example.MedicCenter.domain.ports.out.PatientRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService implements PatientUseCase {

    private final PatientRepositoryPort patientRepository;

    @Override
    @Transactional
    public Patient registerPatient(Patient patient) {
        log.info("Registrando nuevo paciente con documento: {}", patient.getDocumento());

        // Validar documento único
        if (patientRepository.findByDocumento(patient.getDocumento()).isPresent()) {
            throw new BusinessRuleException("Ya existe un paciente con el documento: " + patient.getDocumento());
        }

        // Validar fecha de afiliación válida (no puede ser futura)
        if (patient.getFechaAfiliacion().isAfter(LocalDate.now())) {
            throw new BusinessRuleException("La fecha de afiliación no puede ser futura");
        }

        // Validar tipo de afiliación
        if (!isValidTipoAfiliacion(patient.getTipoAfiliacion())) {
            throw new BusinessRuleException(
                    "Tipo de afiliación inválido. Debe ser: CONTRIBUTIVO, SUBSIDIADO o PARTICULAR");
        }

        // Establecer estado inicial si no está definido
        if (patient.getEstado() == null || patient.getEstado().isBlank()) {
            patient.setEstado("ACTIVO");
        }

        Patient saved = patientRepository.save(patient);
        log.info("Paciente registrado exitosamente con ID: {}", saved.getId());

        return saved;
    }

    @Override
    @Transactional
    public Patient updatePatient(Long id, Patient patient) {
        log.info("Actualizando paciente ID: {}", id);

        Patient existing = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + id));

        // Validar que no se cambie el documento a uno ya existente
        if (!existing.getDocumento().equals(patient.getDocumento())) {
            if (patientRepository.findByDocumento(patient.getDocumento()).isPresent()) {
                throw new BusinessRuleException("Ya existe un paciente con el documento: " + patient.getDocumento());
            }
        }

        // Validar fecha de afiliación
        if (patient.getFechaAfiliacion().isAfter(LocalDate.now())) {
            throw new BusinessRuleException("La fecha de afiliación no puede ser futura");
        }

        // Actualizar campos
        existing.setDocumento(patient.getDocumento());
        existing.setNombreCompleto(patient.getNombreCompleto());
        existing.setTipoAfiliacion(patient.getTipoAfiliacion());
        existing.setFechaAfiliacion(patient.getFechaAfiliacion());
        existing.setEstado(patient.getEstado());

        Patient updated = patientRepository.save(existing);
        log.info("Paciente actualizado exitosamente");

        return updated;
    }

    @Override
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + id));
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    @Transactional
    public void deletePatient(Long id) {
        if (!patientRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Paciente no encontrado con ID: " + id);
        }
        patientRepository.deleteById(id);
        log.info("Paciente eliminado con ID: {}", id);
    }

    private boolean isValidTipoAfiliacion(String tipoAfiliacion) {
        return "CONTRIBUTIVO".equals(tipoAfiliacion) ||
                "SUBSIDIADO".equals(tipoAfiliacion) ||
                "PARTICULAR".equals(tipoAfiliacion);
    }
}
