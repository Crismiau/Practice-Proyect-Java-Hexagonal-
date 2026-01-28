package com.example.MedicCenter.infrastructure.adapters.in.rest.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudAutorizacionRequest {

    @NotNull(message = "El ID del paciente es obligatorio")
    private Long pacienteId;

    @NotBlank(message = "El tipo de servicio es obligatorio")
    @Pattern(regexp = "CONSULTA|PROCEDIMIENTO|CIRUGIA", message = "El tipo de servicio debe ser: CONSULTA, PROCEDIMIENTO o CIRUGIA")
    private String tipoServicio;

    @NotBlank(message = "El código del servicio es obligatorio")
    private String codigoServicio;

    @NotNull(message = "El costo estimado es obligatorio")
    @Positive(message = "El costo debe ser mayor a cero")
    private Double costoEstimado;
}
