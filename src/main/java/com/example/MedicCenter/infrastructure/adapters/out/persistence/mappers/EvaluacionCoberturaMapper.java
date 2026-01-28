package com.example.MedicCenter.infrastructure.adapters.out.persistence.mappers;

import com.example.MedicCenter.domain.model.EvaluacionCobertura;
import com.example.MedicCenter.infrastructure.adapters.out.persistence.entities.EvaluacionCoberturaEntity;
import org.springframework.stereotype.Component;

@Component
public class EvaluacionCoberturaMapper {

    public EvaluacionCobertura toDomain(EvaluacionCoberturaEntity entity) {
        if (entity == null)
            return null;

        return new EvaluacionCobertura(
                entity.getId(),
                entity.getPorcentajeCobertura(),
                entity.getNivelCobertura(),
                entity.getRequiereCopago(),
                entity.getMotivo(),
                entity.getDetalle());
    }

    public EvaluacionCoberturaEntity toEntity(EvaluacionCobertura domain) {
        if (domain == null)
            return null;

        return new EvaluacionCoberturaEntity(
                domain.getId(),
                domain.getPorcentajeCobertura(),
                domain.getNivelCobertura(),
                domain.getRequiereCopago(),
                domain.getMotivo(),
                domain.getDetalle());
    }
}
