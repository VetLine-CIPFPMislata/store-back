-- Actualizar tabla de carritos para agregar totales
ALTER TABLE carts
    ADD COLUMN total_products INT DEFAULT 0,
    ADD COLUMN total_price DECIMAL(10, 2) DEFAULT 0.00;

-- Tabla de pedidos (orders)
CREATE TABLE orders (
    id_order BIGINT AUTO_INCREMENT PRIMARY KEY,
    total_products INT DEFAULT 0,
    total_price DECIMAL(10, 2) DEFAULT 0.00,
    user_id BIGINT NULL,
    state ENUM('CART', 'ORDER') DEFAULT 'CART',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    order_at TIMESTAMP NULL,
    address VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES clients(id_client) ON DELETE SET NULL
);

-- Tabla de items de pedidos
CREATE TABLE orders_item (
    id_item_order BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantity INT NOT NULL,
    id_order BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    FOREIGN KEY (id_order) REFERENCES orders(id_order) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id_product) ON DELETE CASCADE
);

-- Índices para mejorar el rendimiento
CREATE INDEX idx_order_user ON orders(user_id);
CREATE INDEX idx_order_state ON orders(state);
CREATE INDEX idx_order_item_order ON orders_item(id_order);
CREATE INDEX idx_order_item_product ON orders_item(product_id);

