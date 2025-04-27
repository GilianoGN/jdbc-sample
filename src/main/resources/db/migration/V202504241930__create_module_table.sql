CREATE TABLE modules(
    module_id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(150) NOT NULL,
    PRIMARY KEY (module_id)
) engine=InnoDB DEFAULT charset=utf8;
