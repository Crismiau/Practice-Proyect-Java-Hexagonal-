package com.example.MedicCenter.infrastructure.adapters.out.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "evaluaciones_cobertura")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluacionCoberturaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer porcentajeCobertura;

    @Column(nullable = false)
    private String nivelCobertura; // BAJA, MEDIA, ALTA

    @Column(nullable = false)
    private Boolean requiereCopago;

    @Column(length = 500)
    private String motivo;

    @Column(length = 500)
    private String detalle;
}
