package com.example.MedicCenter.domain.model;

import java.time.LocalDateTime;

public class Appointment {
    private Long id;
    private Patient patient;
    private String procedureType;
    private Double estimatedCost;
    private LocalDateTime desiredDate;
    private String status; // PENDIENTE_PAGO, AGENDADA, CANCELADA
    private String authCode;
    private Integer coveragePercentage;
    private String cancellationReason;

    public Appointment() {
    }

    public Appointment(Long id, Patient patient, String procedureType, Double estimatedCost, LocalDateTime desiredDate,
            String status, String authCode, Integer coveragePercentage, String cancellationReason) {
        this.id = id;
        this.patient = patient;
        this.procedureType = procedureType;
        this.estimatedCost = estimatedCost;
        this.desiredDate = desiredDate;
        this.status = status;
        this.authCode = authCode;
        this.coveragePercentage = coveragePercentage;
        this.cancellationReason = cancellationReason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getProcedureType() {
        return procedureType;
    }

    public void setProcedureType(String procedureType) {
        this.procedureType = procedureType;
    }

    public Double getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(Double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public LocalDateTime getDesiredDate() {
        return desiredDate;
    }

    public void setDesiredDate(LocalDateTime desiredDate) {
        this.desiredDate = desiredDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public Integer getCoveragePercentage() {
        return coveragePercentage;
    }

    public void setCoveragePercentage(Integer coveragePercentage) {
        this.coveragePercentage = coveragePercentage;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }
}
