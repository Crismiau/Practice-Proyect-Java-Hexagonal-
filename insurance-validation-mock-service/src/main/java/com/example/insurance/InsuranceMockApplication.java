package com.example.insurance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class InsuranceMockApplication {

    public static void main(String[] args) {
        SpringApplication.run(InsuranceMockApplication.class, args);
    }

    @PostMapping("/insurance/validate")
    public Map<String, Object> validate(@RequestBody Map<String, Object> request) {
        String documento = (String) request.get("documento");

        // Convertir documento en seed numérico determinista
        long seed = documento.hashCode();
        int coverage = (int) (Math.abs(seed) % 101);

        String estado = (coverage > 30) ? "AUTORIZADO" : "NO_AUTORIZADO";
        String authCode = "AUTH-2024-" + Math.abs(seed % 100000);

        Map<String, Object> response = new HashMap<>();
        response.put("documento", documento);
        response.put("porcentajeCobertura", coverage);
        response.put("estadoCobertura", estado);
        response.put("codigoAutorizacion", authCode);

        return response;
    }
}
