package com.example.MedicCenter.infrastructure.adapters.in.rest;

import com.example.MedicCenter.domain.model.Patient;
import com.example.MedicCenter.domain.ports.in.PatientUseCase;
import com.example.MedicCenter.infrastructure.adapters.in.rest.dtos.PatientRequest;
import com.example.MedicCenter.infrastructure.adapters.in.rest.dtos.PatientResponse;
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
@RequestMapping("/api/patients")
@RequiredArgsConstructor
@Tag(name = "Patients", description = "Endpoints for patient management")
@SecurityRequirement(name = "bearerAuth")
public class PatientController {

        private final PatientUseCase patientUseCase;

        @Operation(summary = "Register a new patient", description = "Allows admins to register a new patient in the system")
        @ApiResponse(responseCode = "200", description = "Patient registered successfully")
        @PostMapping
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<PatientResponse> registerPatient(
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = @ExampleObject(value = "{\n  \"dni\": \"12345678\",\n  \"name\": \"Juan Perez\",\n  \"age\": 30,\n  \"phone\": \"3001234567\",\n  \"email\": \"juan.perez@example.com\",\n  \"status\": \"ACTIVO\"\n}"))) @Valid @RequestBody PatientRequest request) {
                Patient patient = new Patient(null, request.getDni(), request.getName(), request.getAge(),
                                request.getPhone(), request.getEmail(), request.getStatus());

                Patient saved = patientUseCase.registerPatient(patient);
                return ResponseEntity.ok(mapToResponse(saved));
        }

        @Operation(summary = "Update patient data", description = "Allows admins to update an existing patient's information")
        @PutMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<PatientResponse> updatePatient(@PathVariable Long id,
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = @ExampleObject(value = "{\n  \"dni\": \"12345678\",\n  \"name\": \"Juan Perez Updated\",\n  \"age\": 31,\n  \"phone\": \"3119876543\",\n  \"email\": \"juan.perez@example.com\",\n  \"status\": \"ACTIVO\"\n}"))) @Valid @RequestBody PatientRequest request) {
                Patient patient = new Patient(id, request.getDni(), request.getName(), request.getAge(),
                                request.getPhone(), request.getEmail(), request.getStatus());

                Patient updated = patientUseCase.updatePatient(patient);
                return ResponseEntity.ok(mapToResponse(updated));
        }

        @Operation(summary = "Get patient by ID", description = "Allows admins and doctors to retrieve patient details")
        @GetMapping("/{id}")
        @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
        public ResponseEntity<PatientResponse> getPatient(@PathVariable Long id) {
                return patientUseCase.getPatientById(id)
                                .map(this::mapToResponse)
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());
        }

        private PatientResponse mapToResponse(Patient patient) {
                return PatientResponse.builder()
                                .id(patient.getId())
                                .dni(patient.getDni())
                                .name(patient.getName())
                                .age(patient.getAge())
                                .phone(patient.getPhone())
                                .email(patient.getEmail())
                                .status(patient.getStatus())
                                .build();
        }
}
