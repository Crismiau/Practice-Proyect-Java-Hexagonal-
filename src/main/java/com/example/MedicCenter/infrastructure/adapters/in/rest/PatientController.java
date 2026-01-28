package com.example.MedicCenter.infrastructure.adapters.in.rest;

import com.example.MedicCenter.domain.model.Patient;
import com.example.MedicCenter.domain.ports.in.PatientUseCase;
import com.example.MedicCenter.infrastructure.adapters.in.rest.dtos.PatientRequest;
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

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
@Tag(name = "Patients", description = "Gestión de pacientes")
@SecurityRequirement(name = "bearerAuth")
public class PatientController {

        private final PatientUseCase patientUseCase;

        @Operation(summary = "Registrar nuevo paciente", description = "Registra un paciente validando documento único y fecha de afiliación válida")
        @ApiResponse(responseCode = "200", description = "Paciente registrado exitosamente")
        @PostMapping
        @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
        public ResponseEntity<Patient> registerPatient(
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = @ExampleObject(value = "{\"documento\": \"1017654311\", \"nombreCompleto\": \"Juan Pérez\", \"tipoAfiliacion\": \"CONTRIBUTIVO\", \"fechaAfiliacion\": \"2023-01-15\", \"estado\": \"ACTIVO\"}"))) @Valid @RequestBody PatientRequest request) {

                Patient patient = new Patient();
                patient.setDocumento(request.getDocumento());
                patient.setNombreCompleto(request.getNombreCompleto());
                patient.setTipoAfiliacion(request.getTipoAfiliacion());
                patient.setFechaAfiliacion(request.getFechaAfiliacion());
                patient.setEstado(request.getEstado() != null ? request.getEstado() : "ACTIVO");

                return ResponseEntity.ok(patientUseCase.registerPatient(patient));
        }

        @Operation(summary = "Actualizar paciente")
        @PutMapping("/{id}")
        @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
        public ResponseEntity<Patient> updatePatient(
                        @PathVariable Long id,
                        @Valid @RequestBody PatientRequest request) {

                Patient patient = new Patient();
                patient.setDocumento(request.getDocumento());
                patient.setNombreCompleto(request.getNombreCompleto());
                patient.setTipoAfiliacion(request.getTipoAfiliacion());
                patient.setFechaAfiliacion(request.getFechaAfiliacion());
                patient.setEstado(request.getEstado());

                return ResponseEntity.ok(patientUseCase.updatePatient(id, patient));
        }

        @Operation(summary = "Obtener paciente por ID")
        @GetMapping("/{id}")
        @PreAuthorize("hasAnyRole('PACIENTE', 'MEDICO', 'ADMIN')")
        public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
                return ResponseEntity.ok(patientUseCase.getPatientById(id));
        }

        @Operation(summary = "Listar todos los pacientes")
        @GetMapping
        @PreAuthorize("hasAnyRole('MEDICO', 'ADMIN')")
        public ResponseEntity<List<Patient>> getAllPatients() {
                return ResponseEntity.ok(patientUseCase.getAllPatients());
        }

        @Operation(summary = "Eliminar paciente")
        @DeleteMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
                patientUseCase.deletePatient(id);
                return ResponseEntity.noContent().build();
        }
}
