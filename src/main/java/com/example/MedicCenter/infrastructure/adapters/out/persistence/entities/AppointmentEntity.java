package com.example.MedicCenter.infrastructure.adapters.out.persistence.entities;

import com.example.MedicCenter.domain.model.Appointment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    private String procedureType;
    private Double estimatedCost;
    private LocalDateTime desiredDate;
    private String status;
    private String authCode;
    private Integer coveragePercentage;
    private String cancellationReason;

    public static AppointmentEntity fromDomain(Appointment appointment) {
        return AppointmentEntity.builder()
                .id(appointment.getId())
                .patient(PatientEntity.fromDomain(appointment.getPatient()))
                .procedureType(appointment.getProcedureType())
                .estimatedCost(appointment.getEstimatedCost())
                .desiredDate(appointment.getDesiredDate())
                .status(appointment.getStatus())
                .authCode(appointment.getAuthCode())
                .coveragePercentage(appointment.getCoveragePercentage())
                .cancellationReason(appointment.getCancellationReason())
                .build();
    }

    public Appointment toDomain() {
        return new Appointment(
                id,
                patient.toDomain(),
                procedureType,
                estimatedCost,
                desiredDate,
                status,
                authCode,
                coveragePercentage,
                cancellationReason);
    }
}
