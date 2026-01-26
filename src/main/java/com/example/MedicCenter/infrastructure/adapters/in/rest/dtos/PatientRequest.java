package com.example.MedicCenter.infrastructure.adapters.in.rest.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequest {

    @NotBlank(message = "DNI is mandatory")
    @Size(min = 5, max = 20, message = "DNI must be between 5 and 20 characters")
    private String dni;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @Min(value = 0, message = "Age cannot be negative")
    private Integer age;

    @NotBlank(message = "Phone is mandatory")
    private String phone;

    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Status is mandatory")
    @Pattern(regexp = "ACTIVO|INACTIVO", message = "Status must be ACTIVO or INACTIVO")
    private String status;
}
