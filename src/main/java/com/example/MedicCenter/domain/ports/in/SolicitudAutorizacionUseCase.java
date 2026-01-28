package com.example.MedicCenter.domain.ports.in;

import com.example.MedicCenter.domain.model.SolicitudAutorizacion;

import java.util.List;

public interface SolicitudAutorizacionUseCase {

    /**
     * Registra una nueva solicitud de autorización médica en estado PENDIENTE
     */
    SolicitudAutorizacion registrarSolicitud(SolicitudAutorizacion solicitud);

    /**
     * Evalúa una solicitud de autorización:
     * 1. Valida que el paciente esté ACTIVO
     * 2. Invoca al servicio de validación de seguros
     * 3. Aplica políticas internas
     * 4. Genera EvaluacionCobertura
     * 5. Decide APROBADA o RECHAZADA
     * 6. Actualiza el estado de la solicitud
     */
    SolicitudAutorizacion evaluarAutorizacion(Long solicitudId);

    /**
     * Obtiene una solicitud por ID
     */
    SolicitudAutorizacion obtenerPorId(Long id);

    /**
     * Lista todas las solicitudes
     */
    List<SolicitudAutorizacion> listarTodas();

    /**
     * Lista solicitudes por paciente
     */
    List<SolicitudAutorizacion> listarPorPaciente(Long pacienteId);

    /**
     * Lista solicitudes por estado
     */
    List<SolicitudAutorizacion> listarPorEstado(String estado);
}
