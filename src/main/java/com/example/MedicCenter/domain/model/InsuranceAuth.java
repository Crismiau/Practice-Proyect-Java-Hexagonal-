package com.example.MedicCenter.domain.model;

/**
 * Modelo de dominio que representa la autorización de seguro médico.
 * Contiene la información de cobertura y autorización para un procedimiento.
 */
public class InsuranceAuth {
    private String documento;
    private Integer porcentajeCobertura;
    private String estadoCobertura;
    private String codigoAutorizacion;

    public InsuranceAuth() {
    }

    public InsuranceAuth(String documento, Integer porcentajeCobertura, String estadoCobertura,
            String codigoAutorizacion) {
        this.documento = documento;
        this.porcentajeCobertura = porcentajeCobertura;
        this.estadoCobertura = estadoCobertura;
        this.codigoAutorizacion = codigoAutorizacion;
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

    public String getEstadoCobertura() {
        return estadoCobertura;
    }

    public void setEstadoCobertura(String estadoCobertura) {
        this.estadoCobertura = estadoCobertura;
    }

    public String getCodigoAutorizacion() {
        return codigoAutorizacion;
    }

    public void setCodigoAutorizacion(String codigoAutorizacion) {
        this.codigoAutorizacion = codigoAutorizacion;
    }
}
