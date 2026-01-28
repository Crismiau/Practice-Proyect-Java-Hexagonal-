package com.example.MedicCenter.domain.ports.out;

import com.example.MedicCenter.domain.model.SolicitudAutorizacion;

import java.util.List;
import java.util.Optional;

public interface SolicitudAutorizacionRepositoryPort {

    SolicitudAutorizacion save(SolicitudAutorizacion solicitud);

    Optional<SolicitudAutorizacion> findById(Long id);

    List<SolicitudAutorizacion> findAll();

    List<SolicitudAutorizacion> findByPacienteId(Long pacienteId);

    List<SolicitudAutorizacion> findByEstado(String estado);

    void deleteById(Long id);
}
