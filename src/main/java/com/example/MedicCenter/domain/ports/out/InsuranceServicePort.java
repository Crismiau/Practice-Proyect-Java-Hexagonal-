package com.example.MedicCenter.domain.ports.out;

import com.example.MedicCenter.domain.model.InsuranceAuth;

public interface InsuranceServicePort {
    InsuranceAuth validateCoverage(String dni, String procedureType, Double cost);
}
