CREATE TABLE appointments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    procedure_type VARCHAR(100) NOT NULL,
    estimated_cost DOUBLE PRECISION,
    desired_date TIMESTAMP,
    status VARCHAR(20) NOT NULL,
    auth_code VARCHAR(100),
    coverage_percentage INT,
    cancellation_reason VARCHAR(255),
    CONSTRAINT fk_patient FOREIGN KEY (patient_id) REFERENCES patients(id)
);

CREATE INDEX idx_appointment_patient ON appointments(patient_id);
