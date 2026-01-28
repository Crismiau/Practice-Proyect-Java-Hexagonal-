package com.example.MedicCenter.infrastructure.adapters.out.persistence.mappers;

import com.example.MedicCenter.domain.model.SolicitudAutorizacion;
import com.example.MedicCenter.infrastructure.adapters.out.persistence.entities.SolicitudAutorizacionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SolicitudAutorizacionMapper {

    private final PatientMapper patientMapper;
    private final EvaluacionCoberturaMapper evaluacionMapper;

    public SolicitudAutorizacion toDomain(SolicitudAutorizacionEntity entity) {
        if (entity == null)
            return null;

        return new SolicitudAutorizacion(
                entity.getId(),
                patientMapper.toDomain(entity.getPaciente()),
                entity.getTipoServicio(),
                entity.getCodigoServicio(),
                entity.getCostoEstimado(),
                entity.getFechaSolicitud(),
                entity.getEstado(),
                evaluacionMapper.toDomain(entity.getEvaluacionCobertura()));
    }

    public SolicitudAutorizacionEntity toEntity(SolicitudAutorizacion domain) {
        if (domain == null)
            return null;

        return new SolicitudAutorizacionEntity(
                domain.getId(),
                patientMapper.toEntity(domain.getPaciente()),
                domain.getTipoServicio(),
                domain.getCodigoServicio(),
                domain.getCostoEstimado(),
                domain.getFechaSolicitud(),
                domain.getEstado(),
                evaluacionMapper.toEntity(domain.getEvaluacionCobertura()));
    }
}
