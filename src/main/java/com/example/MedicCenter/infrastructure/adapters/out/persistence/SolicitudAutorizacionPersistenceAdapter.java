package com.example.MedicCenter.infrastructure.adapters.out.persistence;

import com.example.MedicCenter.domain.model.SolicitudAutorizacion;
import com.example.MedicCenter.domain.ports.out.SolicitudAutorizacionRepositoryPort;
import com.example.MedicCenter.infrastructure.adapters.out.persistence.entities.SolicitudAutorizacionEntity;
import com.example.MedicCenter.infrastructure.adapters.out.persistence.mappers.SolicitudAutorizacionMapper;
import com.example.MedicCenter.infrastructure.adapters.out.persistence.repositories.SolicitudAutorizacionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SolicitudAutorizacionPersistenceAdapter implements SolicitudAutorizacionRepositoryPort {

    private final SolicitudAutorizacionJpaRepository jpaRepository;
    private final SolicitudAutorizacionMapper mapper;

    @Override
    public SolicitudAutorizacion save(SolicitudAutorizacion solicitud) {
        SolicitudAutorizacionEntity entity = mapper.toEntity(solicitud);
        SolicitudAutorizacionEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<SolicitudAutorizacion> findById(Long id) {
        return jpaRepository.findByIdWithDetails(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<SolicitudAutorizacion> findAll() {
        return jpaRepository.findAllWithDetails().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<SolicitudAutorizacion> findByPacienteId(Long pacienteId) {
        return jpaRepository.findByPacienteIdWithDetails(pacienteId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<SolicitudAutorizacion> findByEstado(String estado) {
        return jpaRepository.findByEstadoWithDetails(estado).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
