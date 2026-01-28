package com.example.MedicCenter.domain.ports.out;

import com.example.MedicCenter.domain.model.InsuranceValidationResponse;

public interface InsuranceValidationPort {

    /**
     * Valida la cobertura del seguro invocando al insurance-validation-mock-service
     * 
     * @param documento      Documento del paciente
     * @param tipoAfiliacion Tipo de afiliación (CONTRIBUTIVO, SUBSIDIADO,
     *                       PARTICULAR)
     * @param codigoServicio Código del servicio médico
     * @param costo          Costo estimado del servicio
     * @return Respuesta con porcentaje de cobertura, nivel, copago y detalle
     */
    InsuranceValidationResponse validarCobertura(String documento, String tipoAfiliacion,
            String codigoServicio, Double costo);
}
