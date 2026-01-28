-- V3: Insertar datos iniciales para pruebas

-- Usuarios de prueba (contraseñas encriptadas con BCrypt)
-- admin / admin123
-- medico / medico123
-- paciente / paciente123
INSERT INTO users (username, password, role) VALUES
('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ROLE_ADMIN'),
('medico', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ROLE_MEDICO'),
('paciente', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ROLE_PACIENTE');

-- Pacientes de prueba
INSERT INTO patients (documento, nombre_completo, tipo_afiliacion, fecha_afiliacion, estado) VALUES
('1017654311', 'Juan Pérez García', 'CONTRIBUTIVO', '2023-01-15', 'ACTIVO'),
('1234567890', 'María López Rodríguez', 'SUBSIDIADO', '2023-03-20', 'ACTIVO'),
('9876543210', 'Carlos Martínez Sánchez', 'PARTICULAR', '2023-06-10', 'ACTIVO'),
('5555555555', 'Ana Torres Díaz', 'CONTRIBUTIVO', '2022-12-01', 'INACTIVO');

-- Solicitudes de autorización de ejemplo
INSERT INTO solicitudes_autorizacion (paciente_id, tipo_servicio, codigo_servicio, costo_estimado, fecha_solicitud, estado) VALUES
(1, 'CONSULTA', 'CONS-001', 150000, CURRENT_TIMESTAMP - INTERVAL '2 days', 'PENDIENTE'),
(2, 'PROCEDIMIENTO', 'PROC-123', 1200000, CURRENT_TIMESTAMP - INTERVAL '1 day', 'PENDIENTE'),
(3, 'CIRUGIA', 'CIRUG-456', 5000000, CURRENT_TIMESTAMP, 'PENDIENTE');
