package com.example.MedicCenter.infrastructure.adapters.out.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes_autorizacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudAutorizacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    @ToString.Exclude
    private PatientEntity paciente;

    @Column(nullable = false)
    private String tipoServicio; // CONSULTA, PROCEDIMIENTO, CIRUGIA

    @Column(nullable = false)
    private String codigoServicio;

    @Column(nullable = false)
    private Double costoEstimado;

    @Column(nullable = false)
    private LocalDateTime fechaSolicitud;

    @Column(nullable = false)
    private String estado; // PENDIENTE, APROBADA, RECHAZADA

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluacion_cobertura_id")
    private EvaluacionCoberturaEntity evaluacionCobertura;
}
