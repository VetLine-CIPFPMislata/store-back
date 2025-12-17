-- Limpiar datos anteriores de productos y categorías
DELETE FROM cart_items;
DELETE FROM products;
DELETE FROM categories;

-- Resetear AUTO_INCREMENT
ALTER TABLE categories AUTO_INCREMENT = 1;
ALTER TABLE products AUTO_INCREMENT = 1;

-- Insertar categorías para tienda de mascotas
INSERT INTO categories (name, description) VALUES
    ('Juguetes para Perros', 'Juguetes interactivos y de entretenimiento para perros'),
    ('Juguetes para Gatos', 'Juguetes estimulantes para gatos de todas las edades'),
    ('Pienso para Perros', 'Alimento seco y húmedo de alta calidad para perros'),
    ('Pienso para Gatos', 'Comida balanceada para gatos adultos y cachorros'),
    ('Ropa para Perros', 'Prendas y accesorios de vestir para perros'),
    ('Ropa para Gatos', 'Ropa cómoda y adorable para gatos'),
    ('Accesorios', 'Correas, collares, comederos y bebederos'),
    ('Higiene y Cuidado', 'Productos de limpieza y cuidado para mascotas'),
    ('Camas y Transportines', 'Espacios cómodos para descanso y transporte'),
    ('Snacks y Premios', 'Golosinas y premios saludables para mascotas');

-- ========================================
-- JUGUETES PARA PERROS
-- ========================================
INSERT INTO products (id_category, name, product_description, base_price, discount_percentage, picture_product, quantity, rating) VALUES
    ((SELECT id_category FROM categories WHERE name = 'Juguetes para Perros'), 'Pelota Kong Clásica', 'Pelota resistente de goma natural, ideal para morder y jugar', 12.99, 0.00, 'https://example.com/pelota-kong.jpg', 150, 5),
    ((SELECT id_category FROM categories WHERE name = 'Juguetes para Perros'), 'Cuerda Dental Multicolor', 'Cuerda de algodón para tirar y limpiar dientes', 8.99, 15.00, 'https://example.com/cuerda-dental.jpg', 200, 4),
    ((SELECT id_category FROM categories WHERE name = 'Juguetes para Perros'), 'Frisbee Flotante', 'Disco volador suave y flotante para jugar en agua', 14.99, 10.00, 'https://example.com/frisbee.jpg', 120, 5),
    ((SELECT id_category FROM categories WHERE name = 'Juguetes para Perros'), 'Hueso de Goma con Sonido', 'Hueso duradero con squeaker interno', 9.99, 0.00, 'https://example.com/hueso-sonido.jpg', 180, 4),
    ((SELECT id_category FROM categories WHERE name = 'Juguetes para Perros'), 'Juguete Interactivo Dispensador', 'Pelota que dispensa premios al rodar', 19.99, 20.00, 'https://example.com/dispensador.jpg', 90, 5),
    ((SELECT id_category FROM categories WHERE name = 'Juguetes para Perros'), 'Peluche Pato Resistente', 'Peluche con costuras reforzadas y squeaker', 11.99, 5.00, 'https://example.com/peluche-pato.jpg', 130, 4);

-- ========================================
-- JUGUETES PARA GATOS
-- ========================================
INSERT INTO products (id_category, name, product_description, base_price, discount_percentage, picture_product, quantity, rating) VALUES
    ((SELECT id_category FROM categories WHERE name = 'Juguetes para Gatos'), 'Ratón de Juguete con Catnip', 'Ratoncito relleno de hierba gatera premium', 5.99, 0.00, 'https://example.com/raton-catnip.jpg', 250, 5),
    ((SELECT id_category FROM categories WHERE name = 'Juguetes para Gatos'), 'Varita con Plumas', 'Varita interactiva con plumas coloridas', 7.99, 10.00, 'https://example.com/varita-plumas.jpg', 180, 4),
    ((SELECT id_category FROM categories WHERE name = 'Juguetes para Gatos'), 'Túnel de Juego Plegable', 'Túnel de tela con múltiples entradas', 24.99, 15.00, 'https://example.com/tunel-gato.jpg', 70, 5),
    ((SELECT id_category FROM categories WHERE name = 'Juguetes para Gatos'), 'Bola con Cascabel', 'Pelota sonora para perseguir', 3.99, 0.00, 'https://example.com/bola-cascabel.jpg', 300, 4),
    ((SELECT id_category FROM categories WHERE name = 'Juguetes para Gatos'), 'Rascador con Juguete Colgante', 'Poste rascador con bola suspendida', 34.99, 25.00, 'https://example.com/rascador-juguete.jpg', 60, 5),
    ((SELECT id_category FROM categories WHERE name = 'Juguetes para Gatos'), 'Circuito de Bolas', 'Pista circular con bolas giratorias', 16.99, 10.00, 'https://example.com/circuito-bolas.jpg', 100, 4);

-- ========================================
-- PIENSO PARA PERROS
-- ========================================
INSERT INTO products (id_category, name, product_description, base_price, discount_percentage, picture_product, quantity, rating) VALUES
    ((SELECT id_category FROM categories WHERE name = 'Pienso para Perros'), 'Pienso Premium Adult 15kg', 'Alimento completo para perros adultos con pollo y arroz', 54.99, 10.00, 'https://example.com/pienso-adult.jpg', 80, 5),
    ((SELECT id_category FROM categories WHERE name = 'Pienso para Perros'), 'Pienso Puppy Cachorro 10kg', 'Fórmula especial para cachorros en crecimiento', 48.99, 5.00, 'https://example.com/pienso-puppy.jpg', 90, 5),
    ((SELECT id_category FROM categories WHERE name = 'Pienso para Perros'), 'Pienso Senior +7 años 12kg', 'Alimento bajo en calorías para perros mayores', 49.99, 15.00, 'https://example.com/pienso-senior.jpg', 70, 4),
    ((SELECT id_category FROM categories WHERE name = 'Pienso para Perros'), 'Pienso Grain Free Salmón 12kg', 'Sin cereales, con salmón fresco y patata', 64.99, 20.00, 'https://example.com/grain-free-salmon.jpg', 60, 5),
    ((SELECT id_category FROM categories WHERE name = 'Pienso para Perros'), 'Comida Húmeda Pollo 400g (Pack 12)', 'Latas de comida húmeda sabor pollo', 29.99, 10.00, 'https://example.com/humeda-pollo.jpg', 120, 4),
    ((SELECT id_category FROM categories WHERE name = 'Pienso para Perros'), 'Pienso Hipoalergénico 10kg', 'Para perros con sensibilidad alimentaria', 59.99, 5.00, 'https://example.com/hipoalergenico.jpg', 50, 4);

-- ========================================
-- PIENSO PARA GATOS
-- ========================================
INSERT INTO products (id_category, name, product_description, base_price, discount_percentage, picture_product, quantity, rating) VALUES
    ((SELECT id_category FROM categories WHERE name = 'Pienso para Gatos'), 'Pienso Gato Adult 10kg', 'Alimento completo para gatos adultos con atún', 42.99, 10.00, 'https://example.com/pienso-gato-adult.jpg', 100, 5),
    ((SELECT id_category FROM categories WHERE name = 'Pienso para Gatos'), 'Pienso Kitten Gatitos 5kg', 'Fórmula rica en proteínas para gatitos', 34.99, 5.00, 'https://example.com/pienso-kitten.jpg', 85, 5),
    ((SELECT id_category FROM categories WHERE name = 'Pienso para Gatos'), 'Pienso Indoor Control Bolas 8kg', 'Previene bolas de pelo en gatos de interior', 44.99, 15.00, 'https://example.com/indoor-hairball.jpg', 75, 4),
    ((SELECT id_category FROM categories WHERE name = 'Pienso para Gatos'), 'Pienso Sterilized 10kg', 'Para gatos esterilizados, control de peso', 46.99, 10.00, 'https://example.com/sterilized.jpg', 90, 5),
    ((SELECT id_category FROM categories WHERE name = 'Pienso para Gatos'), 'Comida Húmeda Salmón 85g (Pack 24)', 'Sobres de comida húmeda premium', 34.99, 20.00, 'https://example.com/sobres-salmon.jpg', 150, 4),
    ((SELECT id_category FROM categories WHERE name = 'Pienso para Gatos'), 'Pienso Senior +10 años 7kg', 'Fórmula adaptada para gatos mayores', 39.99, 5.00, 'https://example.com/pienso-gato-senior.jpg', 65, 4);

-- ========================================
-- ROPA PARA PERROS
-- ========================================
INSERT INTO products (id_category, name, product_description, base_price, discount_percentage, picture_product, quantity, rating) VALUES
    ((SELECT id_category FROM categories WHERE name = 'Ropa para Perros'), 'Abrigo Impermeable con Capucha', 'Chaqueta resistente al agua talla M', 24.99, 15.00, 'https://example.com/abrigo-perro.jpg', 60, 4),
    ((SELECT id_category FROM categories WHERE name = 'Ropa para Perros'), 'Sudadera con Capucha Talla L', 'Sudadera cálida de algodón', 19.99, 10.00, 'https://example.com/sudadera-perro.jpg', 80, 4),
    ((SELECT id_category FROM categories WHERE name = 'Ropa para Perros'), 'Camiseta Deportiva Verano', 'Camiseta transpirable para días calurosos', 12.99, 0.00, 'https://example.com/camiseta-perro.jpg', 100, 3),
    ((SELECT id_category FROM categories WHERE name = 'Ropa para Perros'), 'Pijama de Invierno', 'Pijama completo de forro polar', 22.99, 20.00, 'https://example.com/pijama-perro.jpg', 50, 5),
    ((SELECT id_category FROM categories WHERE name = 'Ropa para Perros'), 'Botas Protectoras (Set 4)', 'Botas impermeables para nieve y lluvia', 29.99, 10.00, 'https://example.com/botas-perro.jpg', 70, 4),
    ((SELECT id_category FROM categories WHERE name = 'Ropa para Perros'), 'Chaleco Reflectante', 'Chaleco de seguridad para paseos nocturnos', 16.99, 5.00, 'https://example.com/chaleco-reflectante.jpg', 90, 4);

-- ========================================
-- ROPA PARA GATOS
-- ========================================
INSERT INTO products (id_category, name, product_description, base_price, discount_percentage, picture_product, quantity, rating) VALUES
    ((SELECT id_category FROM categories WHERE name = 'Ropa para Gatos'), 'Jersey Navideño', 'Jersey festivo para gatos pequeños', 14.99, 25.00, 'https://example.com/jersey-gato.jpg', 40, 3),
    ((SELECT id_category FROM categories WHERE name = 'Ropa para Gatos'), 'Camiseta Rayas Marineras', 'Camiseta cómoda estilo marinero', 9.99, 10.00, 'https://example.com/camiseta-gato.jpg', 60, 3),
    ((SELECT id_category FROM categories WHERE name = 'Ropa para Gatos'), 'Disfraz de León', 'Peluca de melena de león', 11.99, 15.00, 'https://example.com/disfraz-leon.jpg', 50, 4),
    ((SELECT id_category FROM categories WHERE name = 'Ropa para Gatos'), 'Bufanda de Invierno', 'Bufanda suave de lana', 7.99, 0.00, 'https://example.com/bufanda-gato.jpg', 70, 3),
    ((SELECT id_category FROM categories WHERE name = 'Ropa para Gatos'), 'Arnés con Correa Ajustable', 'Arnés tipo chaleco con correa', 18.99, 10.00, 'https://example.com/arnes-gato.jpg', 85, 5);

-- ========================================
-- ACCESORIOS
-- ========================================
INSERT INTO products (id_category, name, product_description, base_price, discount_percentage, picture_product, quantity, rating) VALUES
    ((SELECT id_category FROM categories WHERE name = 'Accesorios'), 'Correa Extensible 5m', 'Correa retráctil hasta 5 metros', 19.99, 15.00, 'https://example.com/correa-extensible.jpg', 120, 5),
    ((SELECT id_category FROM categories WHERE name = 'Accesorios'), 'Collar Antipulgas Natural', 'Collar repelente de pulgas y garrapatas', 14.99, 10.00, 'https://example.com/collar-antipulgas.jpg', 150, 4),
    ((SELECT id_category FROM categories WHERE name = 'Accesorios'), 'Comedero Doble Acero Inoxidable', 'Set de 2 cuencos elevados', 24.99, 20.00, 'https://example.com/comedero-doble.jpg', 90, 5),
    ((SELECT id_category FROM categories WHERE name = 'Accesorios'), 'Bebedero Automático 2L', 'Fuente de agua con filtro', 34.99, 15.00, 'https://example.com/bebedero-automatico.jpg', 70, 5),
    ((SELECT id_category FROM categories WHERE name = 'Accesorios'), 'Comedero Lento Antivoracidad', 'Reduce la velocidad de ingesta', 16.99, 10.00, 'https://example.com/comedero-lento.jpg', 100, 4),
    ((SELECT id_category FROM categories WHERE name = 'Accesorios'), 'Placa de Identificación Grabada', 'Chapa personalizable con nombre y teléfono', 6.99, 0.00, 'https://example.com/placa-id.jpg', 200, 5),
    ((SELECT id_category FROM categories WHERE name = 'Accesorios'), 'Correa Reflectante Nylon 1.5m', 'Correa resistente con elementos reflectantes', 12.99, 5.00, 'https://example.com/correa-nylon.jpg', 140, 4);

-- ========================================
-- HIGIENE Y CUIDADO
-- ========================================
INSERT INTO products (id_category, name, product_description, base_price, discount_percentage, picture_product, quantity, rating) VALUES
    ((SELECT id_category FROM categories WHERE name = 'Higiene y Cuidado'), 'Champú Hipoalergénico 500ml', 'Champú suave para piel sensible', 14.99, 10.00, 'https://example.com/champu-hipoalergenico.jpg', 110, 5),
    ((SELECT id_category FROM categories WHERE name = 'Higiene y Cuidado'), 'Cepillo Quitapelos Profesional', 'Cepillo para eliminar pelo muerto', 18.99, 15.00, 'https://example.com/cepillo-quitapelos.jpg', 95, 5),
    ((SELECT id_category FROM categories WHERE name = 'Higiene y Cuidado'), 'Toallitas Limpiadoras (Pack 100)', 'Toallitas húmedas para higiene rápida', 9.99, 5.00, 'https://example.com/toallitas.jpg', 180, 4),
    ((SELECT id_category FROM categories WHERE name = 'Higiene y Cuidado'), 'Cortauñas Guillotina', 'Cortauñas seguro y preciso', 11.99, 10.00, 'https://example.com/cortaunias.jpg', 130, 4),
    ((SELECT id_category FROM categories WHERE name = 'Higiene y Cuidado'), 'Champú Seco en Espuma 200ml', 'Limpieza sin agua', 12.99, 0.00, 'https://example.com/champu-seco.jpg', 100, 4),
    ((SELECT id_category FROM categories WHERE name = 'Higiene y Cuidado'), 'Kit Dental Completo', 'Cepillo, pasta y dedal limpiador', 16.99, 20.00, 'https://example.com/kit-dental.jpg', 85, 5),
    ((SELECT id_category FROM categories WHERE name = 'Higiene y Cuidado'), 'Perfume para Mascotas 100ml', 'Colonia con aroma fresco y duradero', 13.99, 10.00, 'https://example.com/perfume-mascota.jpg', 75, 3);

-- ========================================
-- CAMAS Y TRANSPORTINES
-- ========================================
INSERT INTO products (id_category, name, product_description, base_price, discount_percentage, picture_product, quantity, rating) VALUES
    ((SELECT id_category FROM categories WHERE name = 'Camas y Transportines'), 'Cama Ortopédica Viscoelástica L', 'Cama con memoria de forma para perros grandes', 59.99, 20.00, 'https://example.com/cama-ortopedica.jpg', 50, 5),
    ((SELECT id_category FROM categories WHERE name = 'Camas y Transportines'), 'Cama Cálida de Felpa M', 'Cama suave y cálida tamaño mediano', 34.99, 15.00, 'https://example.com/cama-felpa.jpg', 80, 4),
    ((SELECT id_category FROM categories WHERE name = 'Camas y Transportines'), 'Igloo para Gatos', 'Cueva acogedora de fieltro', 29.99, 10.00, 'https://example.com/igloo-gato.jpg', 65, 5),
    ((SELECT id_category FROM categories WHERE name = 'Camas y Transportines'), 'Transportín Rígido IATA Aprobado', 'Transportín para avión talla M', 49.99, 25.00, 'https://example.com/transportin-rigido.jpg', 40, 5),
    ((SELECT id_category FROM categories WHERE name = 'Camas y Transportines'), 'Mochila Transportadora Ventilada', 'Mochila para llevar mascotas pequeñas', 39.99, 15.00, 'https://example.com/mochila-transportadora.jpg', 60, 4),
    ((SELECT id_category FROM categories WHERE name = 'Camas y Transportines'), 'Caseta de Exterior Impermeable', 'Caseta resistente para jardín talla L', 89.99, 20.00, 'https://example.com/caseta-exterior.jpg', 30, 5),
    ((SELECT id_category FROM categories WHERE name = 'Camas y Transportines'), 'Hamaca Colgante para Gatos', 'Hamaca para ventana con ventosas', 24.99, 10.00, 'https://example.com/hamaca-ventana.jpg', 70, 4);

-- ========================================
-- SNACKS Y PREMIOS
-- ========================================
INSERT INTO products (id_category, name, product_description, base_price, discount_percentage, picture_product, quantity, rating) VALUES
    ((SELECT id_category FROM categories WHERE name = 'Snacks y Premios'), 'Huesos Dentales 28 unidades', 'Huesos masticables para higiene dental', 19.99, 15.00, 'https://example.com/huesos-dentales.jpg', 150, 5),
    ((SELECT id_category FROM categories WHERE name = 'Snacks y Premios'), 'Snacks Naturales de Pollo 500g', 'Tiras de pechuga de pollo deshidratada', 14.99, 10.00, 'https://example.com/snacks-pollo.jpg', 120, 5),
    ((SELECT id_category FROM categories WHERE name = 'Snacks y Premios'), 'Premios para Entrenamiento 200g', 'Pequeños premios bajos en calorías', 9.99, 5.00, 'https://example.com/premios-entrenamiento.jpg', 180, 4),
    ((SELECT id_category FROM categories WHERE name = 'Snacks y Premios'), 'Snacks Dentales para Gatos 60g', 'Premios crujientes para limpieza dental', 7.99, 0.00, 'https://example.com/snacks-gatos.jpg', 200, 4),
    ((SELECT id_category FROM categories WHERE name = 'Snacks y Premios'), 'Orejas de Cerdo Naturales (Pack 10)', 'Orejas 100% naturales para masticar', 16.99, 20.00, 'https://example.com/orejas-cerdo.jpg', 90, 5),
    ((SELECT id_category FROM categories WHERE name = 'Snacks y Premios'), 'Galletas Caseras Variadas 300g', 'Galletas artesanales sin conservantes', 11.99, 10.00, 'https://example.com/galletas-caseras.jpg', 140, 4),
    ((SELECT id_category FROM categories WHERE name = 'Snacks y Premios'), 'Palitos Dentales Veganos 14 unidades', 'Palitos vegetales para limpieza dental', 12.99, 15.00, 'https://example.com/palitos-veganos.jpg', 160, 4);

