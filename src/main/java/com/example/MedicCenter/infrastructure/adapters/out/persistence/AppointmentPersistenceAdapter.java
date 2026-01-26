package com.example.MedicCenter.infrastructure.adapters.out.persistence;

import com.example.MedicCenter.domain.model.Appointment;
import com.example.MedicCenter.domain.ports.out.AppointmentRepositoryPort;
import com.example.MedicCenter.infrastructure.adapters.out.persistence.entities.AppointmentEntity;
import com.example.MedicCenter.infrastructure.adapters.out.persistence.repositories.JpaAppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AppointmentPersistenceAdapter implements AppointmentRepositoryPort {

    private final JpaAppointmentRepository repository;

    @Override
    public Appointment save(Appointment appointment) {
        AppointmentEntity entity = AppointmentEntity.fromDomain(appointment);
        return repository.save(entity).toDomain();
    }

    @Override
    public Optional<Appointment> findById(Long id) {
        return repository.findById(id).map(AppointmentEntity::toDomain);
    }
}
