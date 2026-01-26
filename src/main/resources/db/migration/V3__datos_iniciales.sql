-- Password is 'admin123' BCrypt encoded: $2a$10$wR.8zIdkP/zPps7XhTfDbeH0i1lU1Z0R0G0m/GgGf7Y0tVjQ0o6eO (actually let's just use BCrypt in the app if needed, but for SQL we can seed)
-- For simplicity in H2/Test, let's just seed a known user.
INSERT INTO users (username, password, role) VALUES ('admin', '$2a$10$86YkCqX/8Y7Y7Y7Y7Y7Y7Y', 'ROLE_ADMIN');

INSERT INTO patients (dni, name, age, phone, email, status) VALUES ('12345', 'Juan Perez', 30, '555-0101', 'juan@example.com', 'ACTIVO');
