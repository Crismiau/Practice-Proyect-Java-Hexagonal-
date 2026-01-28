package com.example.MedicCenter.infrastructure.adapters.in.rest;

import com.example.MedicCenter.domain.model.Patient;
import com.example.MedicCenter.domain.model.SolicitudAutorizacion;
import com.example.MedicCenter.domain.ports.in.SolicitudAutorizacionUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
class SolicitudAutorizacionIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SolicitudAutorizacionUseCase solicitudUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "paciente", roles = { "PACIENTE" })
    void registrarSolicitud_WhenAuthenticated_ShouldReturnOk() throws Exception {
        // Arrange
        SolicitudAutorizacion mockResponse = new SolicitudAutorizacion();
        mockResponse.setId(1L);
        mockResponse.setEstado("PENDIENTE");

        when(solicitudUseCase.registrarSolicitud(any())).thenReturn(mockResponse);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("pacienteId", 1);
        requestBody.put("tipoServicio", "CONSULTA");
        requestBody.put("codigoServicio", "CONS-01");
        requestBody.put("costoEstimado", 50000);

        // Act & Assert
        mockMvc.perform(post("/api/solicitudes-autorizacion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));
    }

    @Test
    void registrarSolicitud_WhenUnauthenticated_ShouldReturnUnauthorized() throws Exception {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("pacienteId", 1);

        mockMvc.perform(post("/api/solicitudes-autorizacion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isForbidden()); // JWT security usually returns 403 if token is missing
    }
}
