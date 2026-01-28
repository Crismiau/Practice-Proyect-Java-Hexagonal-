package com.example.MedicCenter.infrastructure.adapters.out.persistence.mappers;

import com.example.MedicCenter.domain.model.Patient;
import com.example.MedicCenter.infrastructure.adapters.out.persistence.entities.PatientEntity;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public Patient toDomain(PatientEntity entity) {
        if (entity == null)
            return null;

        return new Patient(
                entity.getId(),
                entity.getDocumento(),
                entity.getNombreCompleto(),
                entity.getTipoAfiliacion(),
                entity.getFechaAfiliacion(),
                entity.getEstado());
    }

    public PatientEntity toEntity(Patient domain) {
        if (domain == null)
            return null;

        PatientEntity entity = new PatientEntity();
        entity.setId(domain.getId());
        entity.setDocumento(domain.getDocumento());
        entity.setNombreCompleto(domain.getNombreCompleto());
        entity.setTipoAfiliacion(domain.getTipoAfiliacion());
        entity.setFechaAfiliacion(domain.getFechaAfiliacion());
        entity.setEstado(domain.getEstado());
        return entity;
    }
}
