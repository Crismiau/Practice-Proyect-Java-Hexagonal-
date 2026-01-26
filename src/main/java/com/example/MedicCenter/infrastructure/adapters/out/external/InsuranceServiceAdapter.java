package com.example.MedicCenter.infrastructure.adapters.out.external;

import com.example.MedicCenter.domain.model.InsuranceAuth;
import com.example.MedicCenter.domain.ports.out.InsuranceServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class InsuranceServiceAdapter implements InsuranceServicePort {

    private final RestTemplate restTemplate;

    @Value("${insurance.mock.url:http://localhost:8081/insurance/validate}")
    private String mockUrl;

    @Override
    public InsuranceAuth validateCoverage(String dni, String procedureType, Double cost) {
        Map<String, Object> request = new HashMap<>();
        request.put("documento", dni);
        request.put("tipoProcedimiento", procedureType);
        request.put("costo", cost);

        try {
            InsuranceResponse response = restTemplate.postForObject(mockUrl, request, InsuranceResponse.class);
            if (response == null)
                throw new RuntimeException("Empty response from insurance service");

            return new InsuranceAuth(
                    response.getDocumento(),
                    response.getPorcentajeCobertura(),
                    response.getEstadoCobertura(),
                    response.getCodigoAutorizacion());
        } catch (Exception e) {
            // Simplified error handling
            return new InsuranceAuth(dni, 0, "NO_AUTORIZADO", "ERROR-" + System.currentTimeMillis());
        }
    }

    private static class InsuranceResponse {
        private String documento;
        private Integer porcentajeCobertura;
        private String estadoCobertura;
        private String codigoAutorizacion;

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
}
