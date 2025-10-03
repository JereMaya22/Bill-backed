-- SQL para crear las tablas del microservicio fiscal credit
-- Ejecutar esto en la base de datos 'fiscal' como administrador

-- Crear tabla fiscal_credit
CREATE TABLE fiscal_credit (
    id BIGSERIAL NOT NULL,
    account VARCHAR(255),
    bill_generation_date TIMESTAMP(6),
    company_address VARCHAR(255),
    company_document VARCHAR(255),
    company_email VARCHAR(255),
    company_name VARCHAR(255),
    company_phone VARCHAR(255),
    control_number VARCHAR(31) NOT NULL,
    customer_address VARCHAR(255),
    customer_document VARCHAR(255),
    customer_email VARCHAR(255),
    customer_name VARCHAR(255),
    customer_phone VARCHAR(255),
    exempt_sales FLOAT(53),
    generation_code VARCHAR(36) NOT NULL,
    iva FLOAT(53),
    non_taxed_sales FLOAT(53),
    payment_condition VARCHAR(255),
    perceived_iva FLOAT(53),
    subtotal FLOAT(53),
    taxed_sales FLOAT(53),
    total_with_iva FLOAT(53),
    withheld_iva FLOAT(53),
    PRIMARY KEY (id)
);

-- Crear tabla fiscal_credit_item
CREATE TABLE fiscal_credit_item (
    id BIGSERIAL NOT NULL,
    exempt_sales FLOAT(53),
    name VARCHAR(255),
    non_taxable_sales FLOAT(53),
    price FLOAT(53),
    product_id BIGINT,
    requested_quantity INTEGER,
    sub_total FLOAT(53),
    taxable_sales FLOAT(53),
    fiscal_credit_id BIGINT,
    PRIMARY KEY (id)
);

-- Crear índices únicos
CREATE UNIQUE INDEX idx_fiscal_credit_generation_code ON fiscal_credit(generation_code);
CREATE UNIQUE INDEX idx_fiscal_credit_control_number ON fiscal_credit(control_number);

-- Crear foreign key
ALTER TABLE fiscal_credit_item 
ADD CONSTRAINT fk_fiscal_credit_item_fiscal_credit 
FOREIGN KEY (fiscal_credit_id) REFERENCES fiscal_credit(id);

-- Otorgar permisos al usuario fiscal_user
GRANT ALL PRIVILEGES ON fiscal_credit TO fiscal_user;
GRANT ALL PRIVILEGES ON fiscal_credit_item TO fiscal_user;
GRANT USAGE, SELECT ON SEQUENCE fiscal_credit_id_seq TO fiscal_user;
GRANT USAGE, SELECT ON SEQUENCE fiscal_credit_item_id_seq TO fiscal_user;
