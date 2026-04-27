CREATE TABLE work_order (
    work_order_id BIGINT UNSIGNED NOT NULL,
    work_description VARCHAR(255) NOT NULL,
    work_comments VARCHAR(255) DEFAULT NULL,
    work_start_date DATE DEFAULT NULL,
    work_end_date DATE DEFAULT NULL,
    holder INT UNSIGNED NOT NULL,
    status_id INT UNSIGNED NOT NULL,
    plant_element_id INT UNSIGNED NOT NULL,
    work_procedure_id INT UNSIGNED DEFAULT NULL,
    work_permit_id INT UNSIGNED DEFAULT NULL,
    PRIMARY KEY (work_order_id)
);

CREATE TABLE status (
    status_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    order_status VARCHAR(255) NOT NULL,
    PRIMARY KEY (status_id)
);

CREATE TABLE employee (
    employee_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    employee_name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    company_email VARCHAR(255) NOT NULL,
    role_id INT UNSIGNED NOT NULL,
    user_credentials_id INT UNSIGNED DEFAULT NULL,
    PRIMARY KEY (employee_id)
);

CREATE TABLE work_order_employee (
    work_order_id BIGINT UNSIGNED NOT NULL,
    employee_id INT UNSIGNED NOT NULL,
    CONSTRAINT PK_work_order_employee
        PRIMARY KEY (work_order_id, employee_id),
    CONSTRAINT FK_work_order_id
        FOREIGN KEY (work_order_id)
        REFERENCES work_order(work_order_id)
        ON DELETE CASCADE,
   CONSTRAINT FK_employee_id
        FOREIGN KEY (employee_id)
        REFERENCES employee(employee_id)
        ON DELETE CASCADE
);

CREATE TABLE employee_role (
    role_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    role_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (role_id),
    UNIQUE (role_name)
);

CREATE TABLE user_credentials (
    user_credentials_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_name VARCHAR(255) NOT NULL,
    user_password VARCHAR(255) NOT NULL,
    PRIMARY KEY (user_credentials_id),
    UNIQUE (user_name)
);

CREATE TABLE plant_element (
    element_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    element_tag VARCHAR(255) NOT NULL,
    element_description VARCHAR(255) NOT NULL,
    element_location VARCHAR(255) NOT NULL,
    inspection_date DATE DEFAULT NULL,
    element_check_frequency INT DEFAULT NULL,
    PRIMARY KEY (element_id)
);

CREATE TABLE category (
    category_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    category_name VARCHAR(255) NOT NULL,
    category_description VARCHAR(255) NOT NULL,
    PRIMARY KEY (category_id),
    UNIQUE (category_name)
);

CREATE TABLE work_order_category (
    work_order_id BIGINT UNSIGNED NOT NULL,
    category_id INT UNSIGNED NOT NULL,
    CONSTRAINT PK_work_order_category
        PRIMARY KEY (work_order_id, category_id),
    CONSTRAINT FK_order_id
        FOREIGN KEY (work_order_id)
        REFERENCES work_order(work_order_id)
        ON DELETE CASCADE,
    CONSTRAINT FK_work_category_id
        FOREIGN KEY (category_id)
        REFERENCES category(category_id)
        ON DELETE CASCADE
);

CREATE TABLE spare_part (
    spare_part_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    spare_part_name VARCHAR(255) NOT NULL,
    spare_part_number VARCHAR(255) NOT NULL,
    spare_part_description VARCHAR(255) NOT NULL,
    spare_part_stock INT UNSIGNED NOT NULL,
    spare_part_category_id INT UNSIGNED DEFAULT NULL,
    PRIMARY KEY (spare_part_id)
);

CREATE TABLE spare_part_category (
    category_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    category_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (category_id),
    UNIQUE (category_name)
);

CREATE TABLE work_order_spare (
    work_order_id BIGINT UNSIGNED NOT NULL,
    spare_part_id INT UNSIGNED NOT NULL,
    selected_number INT UNSIGNED NOT NULL,
    CONSTRAINT PK_work_order_spare
        PRIMARY KEY (work_order_id, spare_part_id),
    CONSTRAINT FK_sp_work_order_id
        FOREIGN KEY (work_order_id)
        REFERENCES work_order(work_order_id)
        ON DELETE CASCADE,
    CONSTRAINT FK_spare_part_id
        FOREIGN KEY (spare_part_id)
        REFERENCES spare_part(spare_part_id)
        ON DELETE CASCADE
);

CREATE TABLE work_log (
    log_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    log_date DATETIME NOT NULL,
    log_comment VARCHAR(255) NOT NULL,
    employee_id INT UNSIGNED NOT NULL,
    work_order_id BIGINT UNSIGNED NOT NULL,
    work_permit_id INT UNSIGNED DEFAULT NULL,
    PRIMARY KEY (log_id)
);

CREATE TABLE work_permit (
    work_permit_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    lockout_device_id VARCHAR(255) DEFAULT NULL,
    work_permit_description VARCHAR(255) NOT NULL,
    loto_procedure_id INT UNSIGNED DEFAULT NULL,
    PRIMARY KEY (work_permit_id)
);

CREATE TABLE work_procedure (
    work_procedure_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    work_procedure_code VARCHAR(255) NOT NULL,
    work_procedure_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (work_procedure_id)
);

CREATE TABLE loto_procedure (
    loto_procedure_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    loto_procedure_code VARCHAR(255) NOT NULL,
    loto_procedure_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (loto_procedure_id)
);


ALTER TABLE work_order
    ADD CONSTRAINT FK_holder
        FOREIGN KEY (holder)
        REFERENCES employee(employee_id)
        ON DELETE RESTRICT,
    ADD CONSTRAINT FK_work_order_status
        FOREIGN KEY (status_id)
        REFERENCES status(status_id)
        ON DELETE RESTRICT,
    ADD CONSTRAINT FK_plant_element
        FOREIGN KEY (plant_element_id)
        REFERENCES plant_element(element_id)
        ON DELETE RESTRICT,
    ADD CONSTRAINT FK_work_procedure
        FOREIGN KEY (work_procedure_id)
        REFERENCES work_procedure(work_procedure_id)
        ON DELETE SET NULL,
    ADD CONSTRAINT FK_work_permit
        FOREIGN KEY (work_permit_id)
        REFERENCES work_permit(work_permit_id)
        ON DELETE SET NULL;

ALTER TABLE employee
    ADD CONSTRAINT FK_role
        FOREIGN KEY (role_id)
        REFERENCES employee_role(role_id)
        ON DELETE RESTRICT,
    ADD CONSTRAINT FK_credentials
        FOREIGN KEY (user_credentials_id)
        REFERENCES user_credentials(user_credentials_id)
        ON DELETE SET NULL;

ALTER TABLE work_log
    ADD CONSTRAINT FK_log_employee
        FOREIGN KEY (employee_id)
        REFERENCES employee(employee_id)
        ON DELETE RESTRICT,
    ADD CONSTRAINT FK_log_work_order
        FOREIGN KEY (work_order_id)
        REFERENCES work_order(work_order_id)
        ON DELETE CASCADE,
    ADD CONSTRAINT FK_log_work_permit
        FOREIGN KEY (work_permit_id)
        REFERENCES work_permit(work_permit_id)
        ON DELETE SET NULL;

ALTER TABLE work_permit
    ADD CONSTRAINT FK_work_permit_procedure
        FOREIGN KEY (loto_procedure_id)
        REFERENCES loto_procedure(loto_procedure_id)
        ON DELETE SET NULL;

ALTER TABLE spare_part
    ADD CONSTRAINT FK_spare_part_category
        FOREIGN KEY (spare_part_category_id)
        REFERENCES spare_part_category(category_id);

ALTER TABLE work_procedure
    ADD CONSTRAINT UC_code
        UNIQUE (work_procedure_code);

ALTER TABLE loto_procedure
    ADD CONSTRAINT UC_loto_code
        UNIQUE (loto_procedure_code);