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

    @PostMapping("/coverage-evaluation")
    public Map<String, Object> evaluateCoverage(@RequestBody Map<String, Object> request) {
        String documento = (String) request.get("documento");
        String tipoAfiliacion = (String) request.get("tipoAfiliacion");
        String codigoServicio = (String) request.get("codigoServicio");
        Number costo = (Number) request.get("costo");

        // Convertir documento en seed numérico determinista (hash mod 1000)
        long seed = Math.abs(documento.hashCode() % 1000);

        // Generar porcentaje de cobertura entre 50 y 100
        int porcentajeCobertura = 50 + (int) (seed % 51);

        // Clasificar nivel de cobertura
        String nivelCobertura;
        if (porcentajeCobertura >= 90) {
            nivelCobertura = "ALTA";
        } else if (porcentajeCobertura >= 70) {
            nivelCobertura = "MEDIA";
        } else {
            nivelCobertura = "BAJA";
        }

        // Determinar si requiere copago
        boolean requiereCopago = porcentajeCobertura < 100;

        // Generar detalle
        String detalle = "Cobertura parcial según plan del paciente.";

        Map<String, Object> response = new HashMap<>();
        response.put("documento", documento);
        response.put("porcentajeCobertura", porcentajeCobertura);
        response.put("nivelCobertura", nivelCobertura);
        response.put("requiereCopago", requiereCopago);
        response.put("detalle", detalle);

        return response;
    }
}
