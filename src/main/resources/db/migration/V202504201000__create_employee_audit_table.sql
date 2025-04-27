CREATE TABLE employees_audit(
    audit_id BIGINT NOT NULL AUTO_INCREMENT,
    employee_id BIGINT NOT NULL,
    name VARCHAR(150),
    old_name VARCHAR(150),
    salary DECIMAL(10,2),
    old_salary DECIMAL(10,2),
    birthday TIMESTAMP,
    old_birthday TIMESTAMP,
    operation CHAR(1),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(audit_id)
)engine=InnoDB DEFAULT charset=utf8;