package com.example.MedicCenter.domain.model;

import java.time.LocalDate;

public class Patient {
    private Long id;
    private String documento;
    private String nombreCompleto;
    private String tipoAfiliacion; // CONTRIBUTIVO, SUBSIDIADO, PARTICULAR
    private LocalDate fechaAfiliacion;
    private String estado; // ACTIVO, INACTIVO

    public Patient() {
    }

    public Patient(Long id, String documento, String nombreCompleto, String tipoAfiliacion,
            LocalDate fechaAfiliacion, String estado) {
        this.id = id;
        this.documento = documento;
        this.nombreCompleto = nombreCompleto;
        this.tipoAfiliacion = tipoAfiliacion;
        this.fechaAfiliacion = fechaAfiliacion;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTipoAfiliacion() {
        return tipoAfiliacion;
    }

    public void setTipoAfiliacion(String tipoAfiliacion) {
        this.tipoAfiliacion = tipoAfiliacion;
    }

    public LocalDate getFechaAfiliacion() {
        return fechaAfiliacion;
    }

    public void setFechaAfiliacion(LocalDate fechaAfiliacion) {
        this.fechaAfiliacion = fechaAfiliacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
