CREATE TABLE accesses(
    employee_id BIGINT NOT NULL,
    module_id BIGINT NOT NULL,
    PRIMARY KEY (employee_id, module_id),
    CONSTRAINT fk_accesses_employees FOREIGN KEY (employee_id) REFERENCES employees(employee_id),
    CONSTRAINT fk_accesses_modules FOREIGN KEY (module_id) REFERENCES modules(module_id)
)engine=InnoDB DEFAULT charset=utf8;
