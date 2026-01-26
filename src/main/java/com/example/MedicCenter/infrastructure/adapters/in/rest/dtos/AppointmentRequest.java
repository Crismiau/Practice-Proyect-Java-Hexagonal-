package com.example.MedicCenter.infrastructure.adapters.in.rest.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequest {

    @NotNull(message = "Patient ID is mandatory")
    private Long patientId;

    @NotBlank(message = "Procedure type is mandatory")
    private String procedureType;

    @NotNull(message = "Estimated cost is mandatory")
    @Positive(message = "Estimated cost must be positive")
    private Double estimatedCost;

    @NotNull(message = "Desired date is mandatory")
    @Future(message = "Desired date must be in the future")
    private LocalDateTime desiredDate;
}
