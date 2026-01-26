package com.example.MedicCenter.application.services;

import com.example.MedicCenter.domain.exception.BusinessRuleException;
import com.example.MedicCenter.domain.exception.ResourceNotFoundException;
import com.example.MedicCenter.domain.model.Appointment;
import com.example.MedicCenter.domain.model.InsuranceAuth;
import com.example.MedicCenter.domain.model.Patient;
import com.example.MedicCenter.domain.ports.in.AppointmentUseCase;
import com.example.MedicCenter.domain.ports.out.AppointmentRepositoryPort;
import com.example.MedicCenter.domain.ports.out.InsuranceServicePort;
import com.example.MedicCenter.domain.ports.out.PatientRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentService implements AppointmentUseCase {

    private final AppointmentRepositoryPort appointmentRepository;
    private final PatientRepositoryPort patientRepository;
    private final InsuranceServicePort insuranceService;

    @Override
    public Appointment createAppointmentIntent(Appointment appointment) {
        log.info("Creating appointment intent for patient ID: {}", appointment.getPatient().getId());

        Patient patient = patientRepository.findById(appointment.getPatient().getId())
                .orElseThrow(() -> {
                    log.error("Patient not found: {}", appointment.getPatient().getId());
                    return new ResourceNotFoundException(
                            "Patient not found with ID: " + appointment.getPatient().getId());
                });

        if (!"ACTIVO".equals(patient.getStatus())) {
            log.warn("Patient {} is not ACTIVE, status: {}", patient.getId(), patient.getStatus());
            throw new BusinessRuleException("Patient must be ACTIVO to schedule appointments.");
        }

        appointment.setPatient(patient);
        appointment.setStatus("PENDIENTE_PAGO");

        Appointment saved = appointmentRepository.save(appointment);
        log.info("Appointment intent created successfully with ID: {}", saved.getId());
        return saved;
    }

    @Override
    @Transactional
    public Appointment processScheduling(Long appointmentId) {
        log.info("Processing scheduling for appointment ID: {}", appointmentId);

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        InsuranceAuth auth = insuranceService.validateCoverage(
                appointment.getPatient().getDni(),
                appointment.getProcedureType(),
                appointment.getEstimatedCost());

        log.info("Insurance validation result for appointment {}: status={}, coverage={}%",
                appointmentId, auth.getBaseStatus(), auth.getCoveragePercentage());

        appointment.setAuthCode(auth.getAuthCode());
        appointment.setCoveragePercentage(auth.getCoveragePercentage());

        if (auth.getCoveragePercentage() < 20) {
            appointment.setStatus("CANCELADA");
            appointment
                    .setCancellationReason("Insufficient coverage percentage: " + auth.getCoveragePercentage() + "%");
            log.warn("Appointment {} cancelled due to low coverage: {}%", appointmentId, auth.getCoveragePercentage());
        } else if ("NO_AUTORIZADO".equals(auth.getBaseStatus())) {
            appointment.setStatus("CANCELADA");
            appointment.setCancellationReason("Insurance company declined authorization");
            log.warn("Appointment {} cancelled by insurance company", appointmentId);
        } else {
            appointment.setStatus("AGENDADA");
            log.info("Appointment {} successfully scheduled", appointmentId);
        }

        return appointmentRepository.save(appointment);
    }
}
