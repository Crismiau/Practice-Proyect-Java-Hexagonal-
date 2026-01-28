-- V2: Crear relaciones entre tablas

CREATE TABLE solicitudes_autorizacion (
    id BIGSERIAL PRIMARY KEY,
    paciente_id BIGINT NOT NULL,
    tipo_servicio VARCHAR(20) NOT NULL CHECK (tipo_servicio IN ('CONSULTA', 'PROCEDIMIENTO', 'CIRUGIA')),
    codigo_servicio VARCHAR(50) NOT NULL,
    costo_estimado DOUBLE NOT NULL,
    fecha_solicitud TIMESTAMP NOT NULL,
    estado VARCHAR(20) NOT NULL CHECK (estado IN ('PENDIENTE', 'APROBADA', 'RECHAZADA')),
    evaluacion_cobertura_id BIGINT,
    CONSTRAINT fk_solicitud_paciente FOREIGN KEY (paciente_id) REFERENCES patients(id),
    CONSTRAINT fk_solicitud_evaluacion FOREIGN KEY (evaluacion_cobertura_id) REFERENCES evaluaciones_cobertura(id)
);

CREATE INDEX idx_solicitudes_paciente ON solicitudes_autorizacion(paciente_id);
CREATE INDEX idx_solicitudes_estado ON solicitudes_autorizacion(estado);
CREATE INDEX idx_solicitudes_fecha ON solicitudes_autorizacion(fecha_solicitud);
