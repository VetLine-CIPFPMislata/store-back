-- Tabla de categorías
CREATE TABLE categories (
                            id_category BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(100) NOT NULL UNIQUE,
                            description TEXT
);

-- Tabla de productos
CREATE TABLE products (
                          id_product BIGINT AUTO_INCREMENT PRIMARY KEY,
                          id_category BIGINT NOT NULL,
                          name VARCHAR(255) NOT NULL,
                          product_description TEXT,
                          base_price DECIMAL(10, 2) NOT NULL,
                          discount_percentage DECIMAL(5, 2) DEFAULT 0.00,
                          picture_product VARCHAR(500),
                          quantity INT NOT NULL DEFAULT 0,
                          rating INT NOT NULL DEFAULT 0 CHECK (rating BETWEEN 0 AND 5),
                          CONSTRAINT fk_product_category FOREIGN KEY (id_category) REFERENCES categories(id_category) ON DELETE CASCADE
);

-- Tabla de carritos
CREATE TABLE carts (
                       id_cart BIGINT AUTO_INCREMENT PRIMARY KEY,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabla de clientes
CREATE TABLE clients (
                         id_client BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         email VARCHAR(255) NOT NULL UNIQUE,
                         password VARCHAR(255) NOT NULL,
                         phone VARCHAR(20),
                         id_cart BIGINT,
                         role VARCHAR(50) NOT NULL DEFAULT 'USER',
                         CONSTRAINT fk_client_cart FOREIGN KEY (id_cart) REFERENCES carts(id_cart) ON DELETE SET NULL
);

-- Tabla de items del carrito
CREATE TABLE cart_items (
                            id_cart_item BIGINT AUTO_INCREMENT PRIMARY KEY,
                            id_cart BIGINT NOT NULL,
                            id_product BIGINT NOT NULL,
                            quantity INT NOT NULL DEFAULT 1,
                            unit_price DECIMAL(10, 2) NOT NULL,
                            CONSTRAINT fk_cart_item_cart FOREIGN KEY (id_cart) REFERENCES carts(id_cart) ON DELETE CASCADE,
                            CONSTRAINT fk_cart_item_product FOREIGN KEY (id_product) REFERENCES products(id_product) ON DELETE CASCADE
);

-- Índices para mejorar el rendimiento
CREATE INDEX idx_product_category ON products(id_category);
CREATE INDEX idx_product_name ON products(name);
CREATE INDEX idx_product_rating ON products(rating);
CREATE INDEX idx_client_email ON clients(email);
CREATE INDEX idx_cart_item_cart ON cart_items(id_cart);
CREATE INDEX idx_cart_item_product ON cart_items(id_product);
