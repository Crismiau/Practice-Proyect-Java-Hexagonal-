package com.example.MedicCenter.infrastructure.adapters.out.rest;

import com.example.MedicCenter.domain.model.InsuranceValidationResponse;
import com.example.MedicCenter.domain.ports.out.InsuranceValidationPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class InsuranceValidationAdapter implements InsuranceValidationPort {

    private final RestTemplate restTemplate;

    @Value("${insurance.validation.url:http://localhost:8081/coverage-evaluation}")
    private String insuranceServiceUrl;

    @Override
    public InsuranceValidationResponse validarCobertura(String documento, String tipoAfiliacion,
            String codigoServicio, Double costo) {
        log.info("Invocando servicio de validación de seguros: {}", insuranceServiceUrl);

        // Preparar request
        Map<String, Object> request = new HashMap<>();
        request.put("documento", documento);
        request.put("tipoAfiliacion", tipoAfiliacion);
        request.put("codigoServicio", codigoServicio);
        request.put("costo", costo);

        try {
            // Invocar servicio externo
            @SuppressWarnings("unchecked")
            Map<String, Object> response = restTemplate.postForObject(
                    insuranceServiceUrl,
                    request,
                    Map.class);

            if (response == null) {
                throw new RuntimeException("Respuesta vacía del servicio de validación de seguros");
            }

            // Mapear respuesta
            InsuranceValidationResponse validationResponse = new InsuranceValidationResponse();
            validationResponse.setDocumento((String) response.get("documento"));
            validationResponse.setPorcentajeCobertura((Integer) response.get("porcentajeCobertura"));
            validationResponse.setNivelCobertura((String) response.get("nivelCobertura"));
            validationResponse.setRequiereCopago((Boolean) response.get("requiereCopago"));
            validationResponse.setDetalle((String) response.get("detalle"));

            log.info("Respuesta del servicio de seguros - Cobertura: {}%, Nivel: {}",
                    validationResponse.getPorcentajeCobertura(),
                    validationResponse.getNivelCobertura());

            return validationResponse;

        } catch (Exception e) {
            log.error("Error al invocar servicio de validación de seguros", e);
            throw new RuntimeException("Error al validar cobertura con el servicio externo: " + e.getMessage(), e);
        }
    }
}
