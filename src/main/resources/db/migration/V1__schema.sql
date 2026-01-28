-- V1: Crear esquema inicial de tablas

CREATE TABLE patients (
    id BIGSERIAL PRIMARY KEY,
    documento VARCHAR(50) UNIQUE NOT NULL,
    nombre_completo VARCHAR(255) NOT NULL,
    tipo_afiliacion VARCHAR(20) NOT NULL CHECK (tipo_afiliacion IN ('CONTRIBUTIVO', 'SUBSIDIADO', 'PARTICULAR')),
    fecha_afiliacion DATE NOT NULL,
    estado VARCHAR(20) NOT NULL CHECK (estado IN ('ACTIVO', 'INACTIVO'))
);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE evaluaciones_cobertura (
    id BIGSERIAL PRIMARY KEY,
    porcentaje_cobertura INTEGER NOT NULL,
    nivel_cobertura VARCHAR(20) NOT NULL CHECK (nivel_cobertura IN ('BAJA', 'MEDIA', 'ALTA')),
    requiere_copago BOOLEAN NOT NULL,
    motivo VARCHAR(500),
    detalle VARCHAR(500)
);

CREATE INDEX idx_evaluaciones_nivel ON evaluaciones_cobertura(nivel_cobertura);
