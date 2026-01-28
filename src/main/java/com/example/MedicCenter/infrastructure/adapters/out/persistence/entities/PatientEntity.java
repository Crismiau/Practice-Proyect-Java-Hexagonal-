package com.example.MedicCenter.infrastructure.adapters.out.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String documento;

    @Column(nullable = false)
    private String nombreCompleto;

    @Column(nullable = false)
    private String tipoAfiliacion; // CONTRIBUTIVO, SUBSIDIADO, PARTICULAR

    @Column(nullable = false)
    private LocalDate fechaAfiliacion;

    @Column(nullable = false)
    private String estado; // ACTIVO, INACTIVO

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private java.util.List<SolicitudAutorizacionEntity> solicitudes = new java.util.ArrayList<>();
}
