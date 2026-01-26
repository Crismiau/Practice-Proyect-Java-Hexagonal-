package com.example.MedicCenter.infrastructure.adapters.out.persistence.repositories;

import com.example.MedicCenter.infrastructure.adapters.out.persistence.entities.AppointmentEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import java.util.List;
import java.util.Optional;

public interface JpaAppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

    @Override
    @NonNull
    @EntityGraph(attributePaths = { "patient" })
    List<AppointmentEntity> findAll();

    @Override
    @NonNull
    @EntityGraph(attributePaths = { "patient" })
    Optional<AppointmentEntity> findById(@NonNull Long id);
}
