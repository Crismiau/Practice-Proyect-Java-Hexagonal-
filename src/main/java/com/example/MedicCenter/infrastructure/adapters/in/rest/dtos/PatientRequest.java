package com.example.MedicCenter.infrastructure.adapters.in.rest.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientRequest {

    @NotBlank(message = "El documento es obligatorio")
    private String documento;

    @NotBlank(message = "El nombre completo es obligatorio")
    private String nombreCompleto;

    @NotBlank(message = "El tipo de afiliación es obligatorio")
    @Pattern(regexp = "CONTRIBUTIVO|SUBSIDIADO|PARTICULAR", message = "El tipo de afiliación debe ser: CONTRIBUTIVO, SUBSIDIADO o PARTICULAR")
    private String tipoAfiliacion;

    @NotNull(message = "La fecha de afiliación es obligatoria")
    @PastOrPresent(message = "La fecha de afiliación no puede ser futura")
    private LocalDate fechaAfiliacion;

    @Pattern(regexp = "ACTIVO|INACTIVO", message = "El estado debe ser: ACTIVO o INACTIVO")
    private String estado;
}
