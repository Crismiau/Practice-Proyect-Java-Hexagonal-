package com.example.MedicCenter.infrastructure.adapters.out.persistence.repositories;

import com.example.MedicCenter.infrastructure.adapters.out.persistence.entities.SolicitudAutorizacionEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SolicitudAutorizacionJpaRepository extends JpaRepository<SolicitudAutorizacionEntity, Long> {

    @EntityGraph(attributePaths = { "paciente", "evaluacionCobertura" })
    @Query("SELECT s FROM SolicitudAutorizacionEntity s WHERE s.id = :id")
    Optional<SolicitudAutorizacionEntity> findByIdWithDetails(@Param("id") Long id);

    @EntityGraph(attributePaths = { "paciente", "evaluacionCobertura" })
    @Query("SELECT s FROM SolicitudAutorizacionEntity s")
    List<SolicitudAutorizacionEntity> findAllWithDetails();

    @EntityGraph(attributePaths = { "paciente", "evaluacionCobertura" })
    @Query("SELECT s FROM SolicitudAutorizacionEntity s WHERE s.paciente.id = :pacienteId")
    List<SolicitudAutorizacionEntity> findByPacienteIdWithDetails(@Param("pacienteId") Long pacienteId);

    @EntityGraph(attributePaths = { "paciente", "evaluacionCobertura" })
    @Query("SELECT s FROM SolicitudAutorizacionEntity s WHERE s.estado = :estado")
    List<SolicitudAutorizacionEntity> findByEstadoWithDetails(@Param("estado") String estado);
}
