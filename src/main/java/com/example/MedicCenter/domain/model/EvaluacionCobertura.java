package com.example.MedicCenter.domain.model;

public class EvaluacionCobertura {
    private Long id;
    private Integer porcentajeCobertura;
    private String nivelCobertura; // BAJA, MEDIA, ALTA
    private Boolean requiereCopago;
    private String motivo;
    private String detalle;

    public EvaluacionCobertura() {
    }

    public EvaluacionCobertura(Long id, Integer porcentajeCobertura, String nivelCobertura,
            Boolean requiereCopago, String motivo, String detalle) {
        this.id = id;
        this.porcentajeCobertura = porcentajeCobertura;
        this.nivelCobertura = nivelCobertura;
        this.requiereCopago = requiereCopago;
        this.motivo = motivo;
        this.detalle = detalle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPorcentajeCobertura() {
        return porcentajeCobertura;
    }

    public void setPorcentajeCobertura(Integer porcentajeCobertura) {
        this.porcentajeCobertura = porcentajeCobertura;
    }

    public String getNivelCobertura() {
        return nivelCobertura;
    }

    public void setNivelCobertura(String nivelCobertura) {
        this.nivelCobertura = nivelCobertura;
    }

    public Boolean getRequiereCopago() {
        return requiereCopago;
    }

    public void setRequiereCopago(Boolean requiereCopago) {
        this.requiereCopago = requiereCopago;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}
