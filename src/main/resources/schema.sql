CREATE TABLE device_instance (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    model VARCHAR(255),
    status VARCHAR(50),
    device_type VARCHAR(50),
    used_for BIGINT
);
