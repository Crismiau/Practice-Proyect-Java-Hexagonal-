package com.example.MedicCenter.application.services;

import com.example.MedicCenter.domain.model.Appointment;
import com.example.MedicCenter.domain.model.InsuranceAuth;
import com.example.MedicCenter.domain.model.Patient;
import com.example.MedicCenter.domain.ports.out.AppointmentRepositoryPort;
import com.example.MedicCenter.domain.ports.out.InsuranceServicePort;
import com.example.MedicCenter.domain.ports.out.PatientRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AppointmentServiceTest {

    @Mock
    private AppointmentRepositoryPort appointmentRepository;

    @Mock
    private PatientRepositoryPort patientRepository;

    @Mock
    private InsuranceServicePort insuranceService;

    @InjectMocks
    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void processScheduling_ShouldSchedule_WhenCoverageIsHighEnough() {
        // Arrange
        Long appointmentId = 1L;
        Patient patient = new Patient(1L, "12345", "Test Patient", 30, "555", "test@test.com", "ACTIVO");
        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        appointment.setPatient(patient);
        appointment.setStatus("PENDIENTE_PAGO");
        appointment.setProcedureType("SURGERY");
        appointment.setEstimatedCost(1000.0);

        InsuranceAuth auth = new InsuranceAuth("12345", 50, "AUTORIZADO", "AUTH123");

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));
        when(insuranceService.validateCoverage(any(), any(), any())).thenReturn(auth);
        when(appointmentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Appointment result = appointmentService.processScheduling(appointmentId);

        // Assert
        assertEquals("AGENDADA", result.getStatus());
        assertEquals("AUTH123", result.getAuthCode());
        verify(appointmentRepository, times(1)).save(appointment);
    }

    @Test
    void processScheduling_ShouldCancel_WhenCoverageIsLow() {
        // Arrange
        Long appointmentId = 1L;
        Patient patient = new Patient(1L, "12345", "Test Patient", 30, "555", "test@test.com", "ACTIVO");
        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        appointment.setPatient(patient);
        appointment.setStatus("PENDIENTE_PAGO");

        InsuranceAuth auth = new InsuranceAuth("12345", 10, "AUTORIZADO", "AUTH_LOW");

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));
        when(insuranceService.validateCoverage(any(), any(), any())).thenReturn(auth);
        when(appointmentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Appointment result = appointmentService.processScheduling(appointmentId);

        // Assert
        assertEquals("CANCELADA", result.getStatus());
        assertTrue(result.getCancellationReason().contains("Insufficient coverage"));
    }
}
