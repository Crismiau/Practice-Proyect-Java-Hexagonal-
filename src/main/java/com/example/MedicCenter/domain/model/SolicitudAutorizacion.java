package com.example.MedicCenter.domain.model;

import java.time.LocalDateTime;

public class SolicitudAutorizacion {
    private Long id;
    private Patient paciente;
    private String tipoServicio; // CONSULTA, PROCEDIMIENTO, CIRUGIA
    private String codigoServicio;
    private Double costoEstimado;
    private LocalDateTime fechaSolicitud;
    private String estado; // PENDIENTE, APROBADA, RECHAZADA
    private EvaluacionCobertura evaluacionCobertura;

    public SolicitudAutorizacion() {
    }

    public SolicitudAutorizacion(Long id, Patient paciente, String tipoServicio, String codigoServicio,
            Double costoEstimado, LocalDateTime fechaSolicitud, String estado,
            EvaluacionCobertura evaluacionCobertura) {
        this.id = id;
        this.paciente = paciente;
        this.tipoServicio = tipoServicio;
        this.codigoServicio = codigoServicio;
        this.costoEstimado = costoEstimado;
        this.fechaSolicitud = fechaSolicitud;
        this.estado = estado;
        this.evaluacionCobertura = evaluacionCobertura;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPaciente() {
        return paciente;
    }

    public void setPaciente(Patient paciente) {
        this.paciente = paciente;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getCodigoServicio() {
        return codigoServicio;
    }

    public void setCodigoServicio(String codigoServicio) {
        this.codigoServicio = codigoServicio;
    }

    public Double getCostoEstimado() {
        return costoEstimado;
    }

    public void setCostoEstimado(Double costoEstimado) {
        this.costoEstimado = costoEstimado;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public EvaluacionCobertura getEvaluacionCobertura() {
        return evaluacionCobertura;
    }

    public void setEvaluacionCobertura(EvaluacionCobertura evaluacionCobertura) {
        this.evaluacionCobertura = evaluacionCobertura;
    }
}
