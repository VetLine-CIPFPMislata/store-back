-- Insertar categorías
INSERT INTO categories (name, description) VALUES
                                               ('Electrónica', 'Dispositivos electrónicos y accesorios'),
                                               ('Ropa', 'Prendas de vestir para hombres y mujeres'),
                                               ('Hogar', 'Artículos para el hogar y decoración'),
                                               ('Deportes', 'Equipamiento deportivo y fitness'),
                                               ('Libros', 'Libros físicos y digitales'),
                                               ('Juguetes', 'Juguetes y juegos para niños'),
                                               ('Alimentos', 'Productos alimenticios y bebidas'),
                                               ('Belleza', 'Productos de belleza y cuidado personal');

-- Insertar productos de Electrónica
INSERT INTO products (id_category, name, product_description, base_price, discount_percentage, picture_product, quantity, rating) VALUES
                                                                                                                                      (1, 'Smartphone XYZ Pro', 'Smartphone de última generación con pantalla OLED', 599.99, 10.00, 'https://example.com/smartphone.jpg', 50, 5),
                                                                                                                                      (1, 'Laptop Gaming Ultra', 'Laptop potente para gaming y diseño', 1299.99, 15.00, 'https://example.com/laptop.jpg', 30, 4),
                                                                                                                                      (1, 'Auriculares Inalámbricos', 'Auriculares con cancelación de ruido', 89.99, 20.00, 'https://example.com/auriculares.jpg', 100, 4),
                                                                                                                                      (1, 'Tablet Plus 10"', 'Tablet con pantalla de 10 pulgadas', 349.99, 5.00, 'https://example.com/tablet.jpg', 75, 4);

-- Insertar productos de Ropa
INSERT INTO products (id_category, name, product_description, base_price, discount_percentage, picture_product, quantity, rating) VALUES
                                                                                                                                      (2, 'Camiseta Básica', 'Camiseta de algodón 100%', 19.99, 0.00, 'https://example.com/camiseta.jpg', 200, 3),
                                                                                                                                      (2, 'Jeans Clásicos', 'Pantalones vaqueros de corte regular', 49.99, 25.00, 'https://example.com/jeans.jpg', 150, 4),
                                                                                                                                      (2, 'Chaqueta de Invierno', 'Chaqueta impermeable con capucha', 89.99, 30.00, 'https://example.com/chaqueta.jpg', 80, 5),
                                                                                                                                      (2, 'Zapatillas Deportivas', 'Zapatillas cómodas para correr', 79.99, 15.00, 'https://example.com/zapatillas.jpg', 120, 4);

-- Insertar productos de Hogar
INSERT INTO products (id_category, name, product_description, base_price, discount_percentage, picture_product, quantity, rating) VALUES
                                                                                                                                      (3, 'Lámpara LED Moderna', 'Lámpara de mesa con luz regulable', 34.99, 10.00, 'https://example.com/lampara.jpg', 60, 4),
                                                                                                                                      (3, 'Alfombra Decorativa', 'Alfombra suave para sala de estar', 69.99, 20.00, 'https://example.com/alfombra.jpg', 40, 3),
                                                                                                                                      (3, 'Juego de Sábanas', 'Sábanas de algodón king size', 44.99, 15.00, 'https://example.com/sabanas.jpg', 90, 4);

-- Insertar productos de Deportes
INSERT INTO products (id_category, name, product_description, base_price, discount_percentage, picture_product, quantity, rating) VALUES
                                                                                                                                      (4, 'Balón de Fútbol Profesional', 'Balón oficial talla 5', 29.99, 5.00, 'https://example.com/balon.jpg', 150, 5),
                                                                                                                                      (4, 'Pesas Ajustables 20kg', 'Set de pesas con ajuste rápido', 119.99, 10.00, 'https://example.com/pesas.jpg', 45, 4),
                                                                                                                                      (4, 'Esterilla de Yoga', 'Esterilla antideslizante de 6mm', 24.99, 0.00, 'https://example.com/esterilla.jpg', 100, 4);

-- Insertar productos de Libros
INSERT INTO products (id_category, name, product_description, base_price, discount_percentage, picture_product, quantity, rating) VALUES
                                                                                                                                      (5, 'Clean Code', 'Libro sobre buenas prácticas de programación', 39.99, 15.00, 'https://example.com/cleancode.jpg', 70, 5),
                                                                                                                                      (5, 'El Señor de los Anillos', 'Trilogía completa en un solo volumen', 49.99, 20.00, 'https://example.com/lotr.jpg', 55, 5),
                                                                                                                                      (5, 'Cien Años de Soledad', 'Clásico de Gabriel García Márquez', 19.99, 10.00, 'https://example.com/cienanos.jpg', 80, 5);

-- Insertar productos de Juguetes
INSERT INTO products (id_category, name, product_description, base_price, discount_percentage, picture_product, quantity, rating) VALUES
                                                                                                                                      (6, 'Lego Star Wars', 'Set de construcción de 500 piezas', 59.99, 25.00, 'https://example.com/lego.jpg', 65, 5),
                                                                                                                                      (6, 'Muñeca Interactiva', 'Muñeca que habla y camina', 34.99, 15.00, 'https://example.com/muneca.jpg', 90, 4),
                                                                                                                                      (6, 'Coche RC a Escala', 'Coche teledirigido 4x4', 44.99, 20.00, 'https://example.com/coche-rc.jpg', 70, 4);

-- Insertar productos de Alimentos
INSERT INTO products (id_category, name, product_description, base_price, discount_percentage, picture_product, quantity, rating) VALUES
                                                                                                                                      (7, 'Café Premium 1kg', 'Café en grano de origen único', 24.99, 5.00, 'https://example.com/cafe.jpg', 200, 4),
                                                                                                                                      (7, 'Aceite de Oliva Extra Virgen', 'Botella de 1 litro', 14.99, 10.00, 'https://example.com/aceite.jpg', 150, 5),
                                                                                                                                      (7, 'Chocolate Negro 85%', 'Tableta de 200g', 4.99, 0.00, 'https://example.com/chocolate.jpg', 300, 4);

-- Insertar productos de Belleza
INSERT INTO products (id_category, name, product_description, base_price, discount_percentage, picture_product, quantity, rating) VALUES
                                                                                                                                      (8, 'Crema Hidratante Facial', 'Crema para todo tipo de piel', 29.99, 15.00, 'https://example.com/crema.jpg', 120, 4),
                                                                                                                                      (8, 'Perfume Floral 100ml', 'Fragancia fresca y duradera', 59.99, 25.00, 'https://example.com/perfume.jpg', 80, 5),
                                                                                                                                      (8, 'Set de Maquillaje Profesional', 'Kit completo con estuche', 89.99, 30.00, 'https://example.com/maquillaje.jpg', 50, 5);

-- Insertar carrito de ejemplo
INSERT INTO carts (id_cart) VALUES (1), (2), (3);

-- Insertar clientes de ejemplo (contraseñas: deben estar hasheadas en producción)
INSERT INTO clients (name, email, password, phone, id_cart, role) VALUES
                                                                      ('Juan Pérez', 'juan.perez@email.com', '$2a$10$dummyHashedPassword1', '+34600111222', 1, 'USER'),
                                                                      ('María García', 'maria.garcia@email.com', '$2a$10$dummyHashedPassword2', '+34600333444', 2, 'USER'),
                                                                      ('Admin Usuario', 'admin@store.com', '$2a$10$dummyHashedPassword3', '+34600555666', 3, 'ADMIN');

-- Insertar items en carritos
INSERT INTO cart_items (id_cart, id_product, quantity, unit_price) VALUES
                                                                       (1, 1, 1, 539.99),  -- Smartphone con descuento aplicado
                                                                       (1, 3, 2, 71.99),   -- 2 Auriculares con descuento
                                                                       (2, 7, 1, 62.99),   -- Chaqueta con descuento
                                                                       (2, 15, 3, 33.99),  -- 3 Libros Clean Code con descuento
                                                                       (3, 10, 1, 31.49);  -- Lámpara con descuento
