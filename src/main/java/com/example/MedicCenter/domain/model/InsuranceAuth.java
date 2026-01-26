package com.example.MedicCenter.domain.model;

public class InsuranceAuth {
    private String document;
    private Integer coveragePercentage;
    private String baseStatus;
    private String authCode;

    public InsuranceAuth() {
    }

    public InsuranceAuth(String document, Integer coveragePercentage, String baseStatus, String authCode) {
        this.document = document;
        this.coveragePercentage = coveragePercentage;
        this.baseStatus = baseStatus;
        this.authCode = authCode;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Integer getCoveragePercentage() {
        return coveragePercentage;
    }

    public void setCoveragePercentage(Integer coveragePercentage) {
        this.coveragePercentage = coveragePercentage;
    }

    public String getBaseStatus() {
        return baseStatus;
    }

    public void setBaseStatus(String baseStatus) {
        this.baseStatus = baseStatus;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
}
