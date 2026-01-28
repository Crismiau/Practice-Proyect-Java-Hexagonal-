package com.example.MedicCenter.domain.model;

public class InsuranceValidationResponse {
    private String documento;
    private Integer porcentajeCobertura;
    private String nivelCobertura; // BAJA, MEDIA, ALTA
    private Boolean requiereCopago;
    private String detalle;

    public InsuranceValidationResponse() {
    }

    public InsuranceValidationResponse(String documento, Integer porcentajeCobertura, String nivelCobertura,
            Boolean requiereCopago, String detalle) {
        this.documento = documento;
        this.porcentajeCobertura = porcentajeCobertura;
        this.nivelCobertura = nivelCobertura;
        this.requiereCopago = requiereCopago;
        this.detalle = detalle;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
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

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}
