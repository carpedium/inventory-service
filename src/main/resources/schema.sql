CREATE TABLE used_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_reference VARCHAR(100)
);

CREATE TABLE device_instance (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    model VARCHAR(255),
    status VARCHAR(50),
    device_type VARCHAR(50),
    used_for_id BIGINT,
    CONSTRAINT fk_used_order FOREIGN KEY (used_for_id) REFERENCES used_order(id)
);