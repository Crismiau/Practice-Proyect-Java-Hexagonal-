package com.example.MedicCenter.infrastructure.adapters.in.rest;

import com.example.MedicCenter.domain.model.Appointment;
import com.example.MedicCenter.domain.model.Patient;
import com.example.MedicCenter.domain.ports.in.AppointmentUseCase;
import com.example.MedicCenter.infrastructure.adapters.in.rest.dtos.AppointmentRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@Tag(name = "Appointments", description = "Endpoints for scheduling and processing medical appointments")
@SecurityRequirement(name = "bearerAuth")
public class AppointmentController {

    private final AppointmentUseCase appointmentUseCase;

    @Operation(summary = "Create an appointment intent", description = "Standardizes the request to create a pending appointment for a patient")
    @ApiResponse(responseCode = "200", description = "Appointment intent created successfully")
    @PostMapping
    @PreAuthorize("hasAnyRole('PACIENTE', 'ADMIN')")
    public ResponseEntity<Appointment> createIntent(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = @ExampleObject(value = "{\n  \"patientId\": 1,\n  \"procedureType\": \"CONSULTA_GENERAL\",\n  \"estimatedCost\": 500.0,\n  \"desiredDate\": \"2026-06-01T10:00:00\"\n}"))) @Valid @RequestBody AppointmentRequest request) {
        // Map DTO to Domain Model
        Patient patient = new Patient();
        patient.setId(request.getPatientId());

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setProcedureType(request.getProcedureType());
        appointment.setEstimatedCost(request.getEstimatedCost());
        appointment.setDesiredDate(request.getDesiredDate());

        return ResponseEntity.ok(appointmentUseCase.createAppointmentIntent(appointment));
    }

    @Operation(summary = "Process an appointment", description = "Validates coverage with insurance service and schedules the appointment")
    @ApiResponse(responseCode = "200", description = "Appointment processed successfully")
    @PostMapping("/{id}/process")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Appointment> process(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentUseCase.processScheduling(id));
    }
}
