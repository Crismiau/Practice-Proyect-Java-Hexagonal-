package com.example.MedicCenter.application.services;

import com.example.MedicCenter.domain.exception.BusinessRuleException;
import com.example.MedicCenter.domain.exception.ResourceNotFoundException;
import com.example.MedicCenter.domain.model.EvaluacionCobertura;
import com.example.MedicCenter.domain.model.InsuranceValidationResponse;
import com.example.MedicCenter.domain.model.Patient;
import com.example.MedicCenter.domain.model.SolicitudAutorizacion;
import com.example.MedicCenter.domain.ports.in.SolicitudAutorizacionUseCase;
import com.example.MedicCenter.domain.ports.out.InsuranceValidationPort;
import com.example.MedicCenter.domain.ports.out.PatientRepositoryPort;
import com.example.MedicCenter.domain.ports.out.SolicitudAutorizacionRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SolicitudAutorizacionService implements SolicitudAutorizacionUseCase {

    private final SolicitudAutorizacionRepositoryPort solicitudRepository;
    private final PatientRepositoryPort patientRepository;
    private final InsuranceValidationPort insuranceValidationPort;

    // Políticas internas
    private static final int COBERTURA_MINIMA_CONSULTA = 60;
    private static final int COBERTURA_MINIMA_PROCEDIMIENTO = 70;
    private static final int COBERTURA_MINIMA_CIRUGIA = 80;
    private static final double COPAGO_MAXIMO_PERMITIDO = 0.40; // 40%

    @Override
    @Transactional
    public SolicitudAutorizacion registrarSolicitud(SolicitudAutorizacion solicitud) {
        log.info("Registrando nueva solicitud de autorización para paciente ID: {}",
                solicitud.getPaciente().getId());

        // Validar que el paciente existe
        Patient paciente = patientRepository.findById(solicitud.getPaciente().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));

        // Validar que el paciente está ACTIVO
        if (!"ACTIVO".equals(paciente.getEstado())) {
            throw new BusinessRuleException("El paciente debe estar en estado ACTIVO para solicitar una autorización");
        }

        // Validar tipo de servicio
        if (!isValidTipoServicio(solicitud.getTipoServicio())) {
            throw new BusinessRuleException("Tipo de servicio inválido. Debe ser: CONSULTA, PROCEDIMIENTO o CIRUGIA");
        }

        // Establecer valores iniciales
        solicitud.setPaciente(paciente);
        solicitud.setFechaSolicitud(LocalDateTime.now());
        solicitud.setEstado("PENDIENTE");

        SolicitudAutorizacion saved = solicitudRepository.save(solicitud);
        log.info("Solicitud registrada con ID: {} en estado PENDIENTE", saved.getId());

        return saved;
    }

    @Override
    @Transactional
    public SolicitudAutorizacion evaluarAutorizacion(Long solicitudId) {
        log.info("Iniciando evaluación de autorización para solicitud ID: {}", solicitudId);

        // 1. Obtener la solicitud
        SolicitudAutorizacion solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitud no encontrada"));

        // Validar que está en estado PENDIENTE
        if (!"PENDIENTE".equals(solicitud.getEstado())) {
            throw new BusinessRuleException("Solo se pueden evaluar solicitudes en estado PENDIENTE");
        }

        Patient paciente = solicitud.getPaciente();

        // 2. Invocar al servicio de validación de seguros
        log.info("Invocando servicio de validación de seguros para documento: {}", paciente.getDocumento());
        InsuranceValidationResponse validationResponse = insuranceValidationPort.validarCobertura(
                paciente.getDocumento(),
                paciente.getTipoAfiliacion(),
                solicitud.getCodigoServicio(),
                solicitud.getCostoEstimado());

        // 3. Aplicar políticas internas
        int coberturaMinima = getCoberturaMinimaPorTipoServicio(solicitud.getTipoServicio());
        boolean cumpleCobertura = validationResponse.getPorcentajeCobertura() >= coberturaMinima;

        // Calcular copago
        double porcentajeCopago = (100.0 - validationResponse.getPorcentajeCobertura()) / 100.0;
        boolean cumpleCopago = porcentajeCopago <= COPAGO_MAXIMO_PERMITIDO;

        // 4. Generar EvaluacionCobertura
        EvaluacionCobertura evaluacion = new EvaluacionCobertura();
        evaluacion.setPorcentajeCobertura(validationResponse.getPorcentajeCobertura());
        evaluacion.setNivelCobertura(validationResponse.getNivelCobertura());
        evaluacion.setRequiereCopago(validationResponse.getRequiereCopago());
        evaluacion.setDetalle(validationResponse.getDetalle());

        // 5. Decidir APROBADA o RECHAZADA
        String nuevoEstado;
        String motivo;

        if (cumpleCobertura && cumpleCopago) {
            nuevoEstado = "APROBADA";
            motivo = String.format("Autorización aprobada. Cobertura: %d%%, Nivel: %s",
                    validationResponse.getPorcentajeCobertura(),
                    validationResponse.getNivelCobertura());
        } else {
            nuevoEstado = "RECHAZADA";
            if (!cumpleCobertura) {
                motivo = String.format("Cobertura insuficiente. Requerido: %d%%, Obtenido: %d%%",
                        coberturaMinima, validationResponse.getPorcentajeCobertura());
            } else {
                motivo = String.format("Copago excede el máximo permitido. Copago: %.1f%%, Máximo: %.1f%%",
                        porcentajeCopago * 100, COPAGO_MAXIMO_PERMITIDO * 100);
            }
        }

        evaluacion.setMotivo(motivo);

        // 6. Actualizar la solicitud
        solicitud.setEstado(nuevoEstado);
        solicitud.setEvaluacionCobertura(evaluacion);

        SolicitudAutorizacion updated = solicitudRepository.save(solicitud);
        log.info("Solicitud ID: {} evaluada. Estado: {}, Motivo: {}", solicitudId, nuevoEstado, motivo);

        return updated;
    }

    @Override
    public SolicitudAutorizacion obtenerPorId(Long id) {
        return solicitudRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitud no encontrada con ID: " + id));
    }

    @Override
    public List<SolicitudAutorizacion> listarTodas() {
        return solicitudRepository.findAll();
    }

    @Override
    public List<SolicitudAutorizacion> listarPorPaciente(Long pacienteId) {
        return solicitudRepository.findByPacienteId(pacienteId);
    }

    @Override
    public List<SolicitudAutorizacion> listarPorEstado(String estado) {
        return solicitudRepository.findByEstado(estado);
    }

    // Métodos auxiliares
    private boolean isValidTipoServicio(String tipoServicio) {
        return "CONSULTA".equals(tipoServicio) ||
                "PROCEDIMIENTO".equals(tipoServicio) ||
                "CIRUGIA".equals(tipoServicio);
    }

    private int getCoberturaMinimaPorTipoServicio(String tipoServicio) {
        return switch (tipoServicio) {
            case "CONSULTA" -> COBERTURA_MINIMA_CONSULTA;
            case "PROCEDIMIENTO" -> COBERTURA_MINIMA_PROCEDIMIENTO;
            case "CIRUGIA" -> COBERTURA_MINIMA_CIRUGIA;
            default -> throw new BusinessRuleException("Tipo de servicio inválido");
        };
    }
}
