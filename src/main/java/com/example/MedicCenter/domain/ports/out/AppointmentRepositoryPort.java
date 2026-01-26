package com.example.MedicCenter.domain.ports.out;

import com.example.MedicCenter.domain.model.Appointment;
import java.util.Optional;

public interface AppointmentRepositoryPort {
    Appointment save(Appointment appointment);

    Optional<Appointment> findById(Long id);
}
