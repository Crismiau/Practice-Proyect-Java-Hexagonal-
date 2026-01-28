package com.example.MedicCenter.infrastructure.adapters.in.rest;

import com.example.MedicCenter.domain.model.Patient;
import com.example.MedicCenter.domain.model.SolicitudAutorizacion;
import com.example.MedicCenter.domain.ports.in.SolicitudAutorizacionUseCase;
import com.example.MedicCenter.infrastructure.adapters.in.rest.dtos.SolicitudAutorizacionRequest;
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
@RequestMapping("/api/solicitudes-autorizacion")
@RequiredArgsConstructor
@Tag(name = "Solicitudes de Autorización", description = "Gestión de solicitudes de autorización médica")
@SecurityRequirement(name = "bearerAuth")
public class SolicitudAutorizacionController {

    private final SolicitudAutorizacionUseCase solicitudUseCase;

    @Operation(summary = "Registrar nueva solicitud", description = "Registra una solicitud de autorización médica en estado PENDIENTE")
    @ApiResponse(responseCode = "200", description = "Solicitud registrada exitosamente")
    @PostMapping
    @PreAuthorize("hasAnyRole('PACIENTE', 'ADMIN')")
    public ResponseEntity<SolicitudAutorizacion> registrarSolicitud(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = @ExampleObject(value = "{\"pacienteId\": 1, \"tipoServicio\": \"CONSULTA\", \"codigoServicio\": \"CONS-001\", \"costoEstimado\": 150000}"))) @Valid @RequestBody SolicitudAutorizacionRequest request) {

        // Mapear DTO a modelo de dominio
        Patient paciente = new Patient();
        paciente.setId(request.getPacienteId());

        SolicitudAutorizacion solicitud = new SolicitudAutorizacion();
        solicitud.setPaciente(paciente);
        solicitud.setTipoServicio(request.getTipoServicio());
        solicitud.setCodigoServicio(request.getCodigoServicio());
        solicitud.setCostoEstimado(request.getCostoEstimado());

        return ResponseEntity.ok(solicitudUseCase.registrarSolicitud(solicitud));
    }

    @Operation(summary = "Evaluar autorización", description = "Evalúa una solicitud: valida cobertura, aplica políticas y decide APROBADA/RECHAZADA")
    @ApiResponse(responseCode = "200", description = "Solicitud evaluada exitosamente")
    @PostMapping("/{id}/evaluar")
    @PreAuthorize("hasAnyRole('MEDICO', 'ADMIN')")
    public ResponseEntity<SolicitudAutorizacion> evaluarAutorizacion(@PathVariable Long id) {
        return ResponseEntity.ok(solicitudUseCase.evaluarAutorizacion(id));
    }

    @Operation(summary = "Obtener solicitud por ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PACIENTE', 'MEDICO', 'ADMIN')")
    public ResponseEntity<SolicitudAutorizacion> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(solicitudUseCase.obtenerPorId(id));
    }

    @Operation(summary = "Listar todas las solicitudes")
    @GetMapping
    @PreAuthorize("hasAnyRole('MEDICO', 'ADMIN')")
    public ResponseEntity<List<SolicitudAutorizacion>> listarTodas() {
        return ResponseEntity.ok(solicitudUseCase.listarTodas());
    }

    @Operation(summary = "Listar solicitudes por paciente")
    @GetMapping("/paciente/{pacienteId}")
    @PreAuthorize("hasAnyRole('PACIENTE', 'MEDICO', 'ADMIN')")
    public ResponseEntity<List<SolicitudAutorizacion>> listarPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(solicitudUseCase.listarPorPaciente(pacienteId));
    }

    @Operation(summary = "Listar solicitudes por estado")
    @GetMapping("/estado/{estado}")
    @PreAuthorize("hasAnyRole('MEDICO', 'ADMIN')")
    public ResponseEntity<List<SolicitudAutorizacion>> listarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(solicitudUseCase.listarPorEstado(estado));
    }
}
