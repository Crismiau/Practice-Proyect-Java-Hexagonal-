package com.example.MedicCenter.application.services;

import com.example.MedicCenter.domain.exception.BusinessRuleException;
import com.example.MedicCenter.domain.model.InsuranceValidationResponse;
import com.example.MedicCenter.domain.model.Patient;
import com.example.MedicCenter.domain.model.SolicitudAutorizacion;
import com.example.MedicCenter.domain.ports.out.InsuranceValidationPort;
import com.example.MedicCenter.domain.ports.out.PatientRepositoryPort;
import com.example.MedicCenter.domain.ports.out.SolicitudAutorizacionRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SolicitudAutorizacionServiceTest {

    @Mock
    private SolicitudAutorizacionRepositoryPort solicitudRepository;

    @Mock
    private PatientRepositoryPort patientRepository;

    @Mock
    private InsuranceValidationPort insuranceValidationPort;

    @InjectMocks
    private SolicitudAutorizacionService solicitudService;

    private Patient patient;
    private SolicitudAutorizacion solicitud;

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setId(1L);
        patient.setDocumento("123456");
        patient.setEstado("ACTIVO");
        patient.setTipoAfiliacion("CONTRIBUTIVO");

        solicitud = new SolicitudAutorizacion();
        solicitud.setId(100L);
        solicitud.setPaciente(patient);
        solicitud.setTipoServicio("CONSULTA");
        solicitud.setCodigoServicio("CONS-01");
        solicitud.setCostoEstimado(100000.0);
        solicitud.setEstado("PENDIENTE");
    }

    @Test
    void registrarSolicitud_WhenPatientInactive_ShouldThrowException() {
        // Arrange
        patient.setEstado("INACTIVO");
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        // Act & Assert
        BusinessRuleException exception = assertThrows(BusinessRuleException.class, () -> {
            solicitudService.registrarSolicitud(solicitud);
        });

        assertTrue(exception.getMessage().contains("estado ACTIVO"));
        verify(solicitudRepository, never()).save(any());
    }

    @Test
    void evaluarAutorizacion_WhenHighCoverage_ShouldApprove() {
        // Arrange
        when(solicitudRepository.findById(100L)).thenReturn(Optional.of(solicitud));

        InsuranceValidationResponse mockResponse = new InsuranceValidationResponse();
        mockResponse.setPorcentajeCobertura(90); // Above 60 for CONSULTA
        mockResponse.setNivelCobertura("ALTA");
        mockResponse.setRequiereCopago(false);

        when(insuranceValidationPort.validarCobertura(any(), any(), any(), any()))
                .thenReturn(mockResponse);

        when(solicitudRepository.save(any())).thenReturn(solicitud);

        // Act
        SolicitudAutorizacion result = solicitudService.evaluarAutorizacion(100L);

        // Assert
        assertEquals("APROBADA", result.getEstado());
        assertNotNull(result.getEvaluacionCobertura());
        assertEquals("ALTA", result.getEvaluacionCobertura().getNivelCobertura());
    }

    @Test
    void evaluarAutorizacion_WhenLowCoverage_ShouldReject() {
        // Arrange
        when(solicitudRepository.findById(100L)).thenReturn(Optional.of(solicitud));

        InsuranceValidationResponse mockResponse = new InsuranceValidationResponse();
        mockResponse.setPorcentajeCobertura(30); // Below 60 for CONSULTA
        mockResponse.setNivelCobertura("BAJA");

        when(insuranceValidationPort.validarCobertura(any(), any(), any(), any()))
                .thenReturn(mockResponse);

        when(solicitudRepository.save(any())).thenReturn(solicitud);

        // Act
        SolicitudAutorizacion result = solicitudService.evaluarAutorizacion(100L);

        // Assert
        assertEquals("RECHAZADA", result.getEstado());
        assertTrue(result.getEvaluacionCobertura().getMotivo().contains("insuficiente"));
    }

    @Test
    void evaluarAutorizacion_WhenSubsidiadoHighCostSurgery_ShouldReject() {
        // Arrange
        patient.setTipoAfiliacion("SUBSIDIADO");
        solicitud.setTipoServicio("CIRUGIA");
        solicitud.setCostoEstimado(3000000.0); // Above 2M limit

        when(solicitudRepository.findById(100L)).thenReturn(Optional.of(solicitud));

        InsuranceValidationResponse mockResponse = new InsuranceValidationResponse();
        mockResponse.setPorcentajeCobertura(100);
        mockResponse.setNivelCobertura("ALTA");

        when(insuranceValidationPort.validarCobertura(any(), any(), any(), any()))
                .thenReturn(mockResponse);

        when(solicitudRepository.save(any())).thenReturn(solicitud);

        // Act
        SolicitudAutorizacion result = solicitudService.evaluarAutorizacion(100L);

        // Assert
        assertEquals("RECHAZADA", result.getEstado());
        assertTrue(result.getEvaluacionCobertura().getMotivo().contains("alto costo"));
    }
}
