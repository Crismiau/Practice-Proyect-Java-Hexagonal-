package com.example.MedicCenter.domain.ports.in;

import com.example.MedicCenter.domain.model.Appointment;

public interface AppointmentUseCase {
    Appointment createAppointmentIntent(Appointment appointment);

    Appointment processScheduling(Long appointmentId);
}
