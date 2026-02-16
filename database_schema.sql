CREATE DATABASE stock_control_db;

\c stock_control_db;

CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    value NUMERIC(10, 2) NOT NULL CHECK (value > 0),
    CONSTRAINT products_name_unique UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS raw_materials (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    stock_quantity NUMERIC(10, 2) NOT NULL CHECK (stock_quantity >= 0),
    CONSTRAINT raw_materials_name_unique UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS product_compositions (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    raw_material_id BIGINT NOT NULL,
    quantity_required NUMERIC(10, 2) NOT NULL CHECK (quantity_required > 0),
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    CONSTRAINT fk_raw_material FOREIGN KEY (raw_material_id) REFERENCES raw_materials(id) ON DELETE CASCADE,
    CONSTRAINT unique_product_raw_material UNIQUE (product_id, raw_material_id)
);

CREATE INDEX IF NOT EXISTS idx_product_compositions_product_id ON product_compositions(product_id);
CREATE INDEX IF NOT EXISTS idx_product_compositions_raw_material_id ON product_compositions(raw_material_id);
CREATE INDEX IF NOT EXISTS idx_products_value ON products(value DESC);

INSERT INTO raw_materials (name, stock_quantity) VALUES
('Steel', 1000.00),
('Plastic', 500.00),
('Rubber', 300.00),
('Glass', 200.00),
('Aluminum', 150.00)
ON CONFLICT (name) DO NOTHING;

INSERT INTO products (name, value) VALUES
('Product A', 150.00),
('Product B', 200.00),
('Product C', 100.00),
('Product D', 250.00)
ON CONFLICT (name) DO NOTHING;

INSERT INTO product_compositions (product_id, raw_material_id, quantity_required)
SELECT p.id, rm.id, 10.00
FROM products p, raw_materials rm
WHERE p.name = 'Product A' AND rm.name = 'Steel'
ON CONFLICT (product_id, raw_material_id) DO NOTHING;

INSERT INTO product_compositions (product_id, raw_material_id, quantity_required)
SELECT p.id, rm.id, 5.00
FROM products p, raw_materials rm
WHERE p.name = 'Product A' AND rm.name = 'Plastic'
ON CONFLICT (product_id, raw_material_id) DO NOTHING;

INSERT INTO product_compositions (product_id, raw_material_id, quantity_required)
SELECT p.id, rm.id, 15.00
FROM products p, raw_materials rm
WHERE p.name = 'Product B' AND rm.name = 'Steel'
ON CONFLICT (product_id, raw_material_id) DO NOTHING;

INSERT INTO product_compositions (product_id, raw_material_id, quantity_required)
SELECT p.id, rm.id, 3.00
FROM products p, raw_materials rm
WHERE p.name = 'Product B' AND rm.name = 'Rubber'
ON CONFLICT (product_id, raw_material_id) DO NOTHING;
