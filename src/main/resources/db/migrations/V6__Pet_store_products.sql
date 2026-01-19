-- Limpiar datos anteriores de productos y categorías
DELETE FROM cart_items;
DELETE FROM products;
DELETE FROM categories;


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
    ((SELECT id_category FROM categories WHERE name = 'Juguetes para Perros'), 'Pelota Kong Clásica', 'Pelota resistente de goma natural, ideal para morder y jugar', 12.99, 0.00, 'https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcQsyzeV83tfPKwCF9qfde54CXXUBSXClTb8IJSW8j9Z9k04zRX8yhUTRVrOlak9zSjkJK2K0Ynv0kb6-0SVoLexQjemcn1-dNLuXaUJ3o9Ekpogi1t-a8aSr-sEAw0A_lxLnw3Qyw&usqp=CAc', 150, 5),
    ((SELECT id_category FROM categories WHERE name = 'Juguetes para Perros'), 'Cuerda Dental Multicolor', 'Cuerda de algodón para tirar y limpiar dientes', 8.99, 15.00, 'https://www.icasa.com/wp-content/uploads/2022/10/cpd1450-cuerda-dental-twist-ica-22-cm_general_12570.jpg', 200, 4),
    ((SELECT id_category FROM categories WHERE name = 'Juguetes para Perros'), 'Frisbee Flotante', 'Disco volador suave y flotante para jugar en agua', 14.99, 10.00, 'https://goldpet.es/97270-medium_default/disco-frisbee-flotante-de-caucho-natural.jpg', 120, 5),
    ((SELECT id_category FROM categories WHERE name = 'Juguetes para Perros'), 'Hueso de Goma con Sonido', 'Hueso duradero con squeaker interno', 9.99, 0.00, 'https://m.media-amazon.com/images/I/51H08pN+M0L._AC_UF1000,1000_QL80_.jpg', 180, 4),
    ((SELECT id_category FROM categories WHERE name = 'Juguetes para Perros'), 'Juguete Interactivo Dispensador', 'Pelota que dispensa premios al rodar', 19.99, 20.00, 'https://www.petmarket.com.ar/wp-content/uploads/2025/07/juguete.perro_.YnaQL_._AC_SL1500_-247x296.png', 90, 5),
    ((SELECT id_category FROM categories WHERE name = 'Juguetes para Perros'), 'Peluche Pato Resistente', 'Peluche con costuras reforzadas y squeaker', 11.99, 5.00, 'https://ataacars.com/11999-medium_default/peluche-grande-patito-snow.jpg', 130, 4);

-- ========================================
-- JUGUETES PARA GATOS
-- ========================================
INSERT INTO products (id_category, name, product_description, base_price, discount_percentage, picture_product, quantity, rating) VALUES
    ((SELECT id_category FROM categories WHERE name = 'Juguetes para Gatos'), 'Ratón de Juguete con Catnip', 'Ratoncito relleno de hierba gatera premium', 5.99, 0.00, 'https://www.kiwoko.com/dw/image/v2/BDLQ_PRD/on/demandware.static/-/Sites-kiwoko-master-catalog/default/dw92c08aa2/images/raton_juguete_gatos_outward_hound_catstages_squeaky_squeaky_gris_catnip_NIN70377M_8.jpg?sw=780&sh=780&sm=fit&q=85', 250, 5),
    ((SELECT id_category FROM categories WHERE name = 'Juguetes para Gatos'), 'Varita con Plumas', 'Varita interactiva con plumas coloridas', 7.99, 10.00, 'https://www.tierradegatos.com/terrcontenido/uploads/2019/01/943-Varita-pluma-cascabel-300x300.jpg', 180, 4),
    ((SELECT id_category FROM categories WHERE name = 'Juguetes para Gatos'), 'Túnel de Juego Plegable', 'Túnel de tela con múltiples entradas', 24.99, 15.00, 'https://m.media-amazon.com/images/I/61vRaGo5A6L._AC_UF1000,1000_QL80_.jpg', 70, 5),
    ((SELECT id_category FROM categories WHERE name = 'Juguetes para Gatos'), 'Bola con Cascabel', 'Pelota sonora para perseguir', 3.99, 0.00, 'https://www.hoptoys.es/30979/1908.jpg', 300, 4),
    ((SELECT id_category FROM categories WHERE name = 'Juguetes para Gatos'), 'Rascador con Juguete Colgante', 'Poste rascador con bola suspendida', 34.99, 25.00, 'https://m.media-amazon.com/images/I/61MlfDwu53L._AC_UF1000,1000_QL80_.jpg', 60, 5),
    ((SELECT id_category FROM categories WHERE name = 'Juguetes para Gatos'), 'Circuito de Bolas', 'Pista circular con bolas giratorias', 16.99, 10.00, 'https://m.media-amazon.com/images/I/716k+anQNtL._AC_UF1000,1000_QL80_.jpg', 100, 4);

-- ========================================
-- PIENSO PARA PERROS
-- ========================================
INSERT INTO products (id_category, name, product_description, base_price, discount_percentage, picture_product, quantity, rating) VALUES
    ((SELECT id_category FROM categories WHERE name = 'Pienso para Perros'), 'Pienso Premium Adult 15kg', 'Alimento completo para perros adultos con pollo y arroz', 54.99, 10.00, 'https://media.falabella.com/sodimacCO/27433_1/w=1160', 80, 5),
    ((SELECT id_category FROM categories WHERE name = 'Pienso para Perros'), 'Pienso Puppy Cachorro 10kg', 'Fórmula especial para cachorros en crecimiento', 48.99, 5.00, 'https://www.worten.es/i/0d6bddd2ba4e39caadc57594bdcdffee7213fb45', 90, 5),
    ((SELECT id_category FROM categories WHERE name = 'Pienso para Perros'), 'Pienso Senior +7 años 12kg', 'Alimento bajo en calorías para perros mayores', 49.99, 15.00, 'https://www.gosigatalimentacio.org/1405-large_default/libra-senior-7-pollo-12-kg.jpg', 70, 4),
    ((SELECT id_category FROM categories WHERE name = 'Pienso para Perros'), 'Pienso Grain Free Salmón 12kg', 'Sin cereales, con salmón fresco y patata', 64.99, 20.00, 'https://www.superpet.club/21760-large_default/nature-s-variety-orginal-gf-mediummaxi-adult-salmon-.jpg', 60, 5),
    ((SELECT id_category FROM categories WHERE name = 'Pienso para Perros'), 'Comida Húmeda Pollo 400g (Pack 12)', 'Latas de comida húmeda sabor pollo', 29.99, 10.00, 'https://m.media-amazon.com/images/I/71k3+8n4QvL.jpg', 120, 4),
    ((SELECT id_category FROM categories WHERE name = 'Pienso para Perros'), 'Pienso Hipoalergénico 10kg', 'Para perros con sensibilidad alimentaria', 59.99, 5.00, 'https://m.media-amazon.com/images/I/71y3MVqQiDL.jpg', 50, 4);

-- ========================================
-- PIENSO PARA GATOS
-- ========================================
INSERT INTO products (id_category, name, product_description, base_price, discount_percentage, picture_product, quantity, rating) VALUES
    ((SELECT id_category FROM categories WHERE name = 'Pienso para Gatos'), 'Pienso Gato Adult 10kg', 'Alimento completo para gatos adultos con atún', 42.99, 10.00, 'https://m.media-amazon.com/images/I/71xT7Q4qfbL._AC_UF894,1000_QL80_.jpg', 100, 5),
    ((SELECT id_category FROM categories WHERE name = 'Pienso para Gatos'), 'Pienso Kitten Gatitos 5kg', 'Fórmula rica en proteínas para gatitos', 34.99, 5.00, 'https://goldpet.es/115505-large_default/royal-canin-kitten-sterilised-pienso-para-gatitos-esterilizados.jpg', 85, 5),
    ((SELECT id_category FROM categories WHERE name = 'Pienso para Gatos'), 'Pienso Indoor Control Bolas 8kg', 'Previene bolas de pelo en gatos de interior', 44.99, 15.00, 'https://static.miscota.com/media/1/photos/products/003369/RC-FCN-HairballCare-MV-2-es-ES-649d9157645a9_g.jpg', 75, 4),
    ((SELECT id_category FROM categories WHERE name = 'Pienso para Gatos'), 'Pienso Sterilized 10kg', 'Para gatos esterilizados, control de peso', 46.99, 10.00, 'https://www.kiwoko.com/dw/image/v2/BDLQ_PRD/on/demandware.static/-/Sites-kiwoko-master-catalog/default/dw6c79719f/images/nath_chicken_salmon_sterilized_adult_NTH41776_M.jpg?sw=780&sh=780&sm=fit&q=85', 90, 5),
    ((SELECT id_category FROM categories WHERE name = 'Pienso para Gatos'), 'Comida Húmeda Salmón 85g (Pack 24)', 'Sobres de comida húmeda premium', 34.99, 20.00, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRNqJKc_gDPvdmaO4yRG5NinSnvFtXK2ykBug&s', 150, 4),
    ((SELECT id_category FROM categories WHERE name = 'Pienso para Gatos'), 'Pienso Senior +10 años 7kg', 'Fórmula adaptada para gatos mayores', 39.99, 5.00, 'https://d23dsm0lnesl7r.cloudfront.net/media/ef/52/fd/1763369005/bl-senior-7.5kg-front.jpg?ts=1763626963', 65, 4);

-- ========================================
-- ROPA PARA PERROS
-- ========================================
INSERT INTO products (id_category, name, product_description, base_price, discount_percentage, picture_product, quantity, rating) VALUES
    ((SELECT id_category FROM categories WHERE name = 'Ropa para Perros'), 'Abrigo Impermeable con Capucha', 'Chaqueta resistente al agua talla M', 24.99, 15.00, 'https://mascoboutique.com/16280-large_default/abrigo-para-perro-plumas-azul-azul.jpg', 60, 4),
    ((SELECT id_category FROM categories WHERE name = 'Ropa para Perros'), 'Sudadera con Capucha Talla L', 'Sudadera cálida de algodón', 19.99, 10.00, 'https://m.media-amazon.com/images/I/61IvaoaBlHL._AC_UF1000,1000_QL80_.jpg', 80, 4),
    ((SELECT id_category FROM categories WHERE name = 'Ropa para Perros'), 'Camiseta Deportiva Verano', 'Camiseta transpirable para días calurosos', 12.99, 0.00, 'https://m.media-amazon.com/images/I/514BNWLcjAL.jpg', 100, 3),
    ((SELECT id_category FROM categories WHERE name = 'Ropa para Perros'), 'Pijama de Invierno', 'Pijama completo de forro polar', 22.99, 20.00, 'https://static.zoomalia.com/cdn-cgi/image/width=1200,height=1200,quality=100,format=auto/prod_img/67739/xl_2319b04d152845ec0a378394003c96da5941575298022.jpg', 50, 5),
    ((SELECT id_category FROM categories WHERE name = 'Ropa para Perros'), 'Botas Protectoras (Set 4)', 'Botas impermeables para nieve y lluvia', 29.99, 10.00, 'https://static.miscota.com/media/1/photos/products/085804/85804-neopre1_0_g.jpg', 70, 4),
    ((SELECT id_category FROM categories WHERE name = 'Ropa para Perros'), 'Chaleco Reflectante', 'Chaleco de seguridad para paseos nocturnos', 16.99, 5.00, 'https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcRj_3y2-XNmAe4MrJ5snxvzZtJ-bLOD4jNYhdqXUI9HIoWduCwZkeSYi6lzXjiBLjvaDZ_UqvKzdSeAUW_CUJ-V0XTED05ehvzLjwupctrGHOKM7Dz2xq-rP5WbXNshsx3d2PU2xA&usqp=CAc', 90, 4);

-- ========================================
-- ROPA PARA GATOS
-- ========================================
INSERT INTO products (id_category, name, product_description, base_price, discount_percentage, picture_product, quantity, rating) VALUES
    ((SELECT id_category FROM categories WHERE name = 'Ropa para Gatos'), 'Jersey Navideño', 'Jersey festivo para gatos pequeños', 14.99, 25.00, 'https://m.media-amazon.com/images/I/710ti-+jk4L._AC_UF894,1000_QL80_.jpg', 40, 3),
    ((SELECT id_category FROM categories WHERE name = 'Ropa para Gatos'), 'Camiseta Rayas Marineras', 'Camiseta cómoda estilo marinero', 9.99, 10.00, 'https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcQcsMKz2G6AX3tmIcr0iGfog1rVpECG9pINfYXugXxHjFyH_znqHFu-tAefHq0WOcB7PxLMZkzb_0hPuDqeEHqCoL3ucV0wVE3CnfbunpSgCYfz3wbgbO-7VLu9xzHYE8hi6tBPcWc&usqp=CAc', 60, 3),
    ((SELECT id_category FROM categories WHERE name = 'Ropa para Gatos'), 'Disfraz de León', 'Peluca de melena de león', 11.99, 15.00, 'https://m.media-amazon.com/images/I/719Z8fEJyhL._AC_UF894,1000_QL80_.jpg', 50, 4),
    ((SELECT id_category FROM categories WHERE name = 'Ropa para Gatos'), 'Bufanda de Invierno', 'Bufanda suave de lana', 7.99, 0.00, 'https://media.adeo.com/mkp/bed3ad78189114f334910113ed54e06a/media.jpeg?width=3000&height=3000&format=jpg&quality=80&fit=bounds', 70, 3),
    ((SELECT id_category FROM categories WHERE name = 'Ropa para Gatos'), 'Arnés con Correa Ajustable', 'Arnés tipo chaleco con correa', 18.99, 10.00, 'https://m.media-amazon.com/images/I/71-hL9gOkOL.jpg', 85, 5);

-- ========================================
-- ACCESORIOS
-- ========================================
INSERT INTO products (id_category, name, product_description, base_price, discount_percentage, picture_product, quantity, rating) VALUES
    ((SELECT id_category FROM categories WHERE name = 'Accesorios'), 'Correa Extensible 5m', 'Correa retráctil hasta 5 metros', 19.99, 15.00, 'https://www.superpet.club/25100-large_default/flexi-xtreme-correa-extensible-con-amortiguador-y-cinta-de-5-m.jpg', 120, 5),
    ((SELECT id_category FROM categories WHERE name = 'Accesorios'), 'Collar Antipulgas Natural', 'Collar repelente de pulgas y garrapatas', 14.99, 10.00, 'https://m.media-amazon.com/images/I/61eV+FvUpmL.jpg', 150, 4),
    ((SELECT id_category FROM categories WHERE name = 'Accesorios'), 'Comedero Doble Acero Inoxidable', 'Set de 2 cuencos elevados', 24.99, 20.00, 'https://www.icasa.com/wp-content/uploads/2024/01/csw1031-comedero-doble-de-acero-inoxidable-negro-home_general_877.jpg', 90, 5),
    ((SELECT id_category FROM categories WHERE name = 'Accesorios'), 'Bebedero Automático 2L', 'Fuente de agua con filtro', 34.99, 15.00, 'https://m.media-amazon.com/images/I/717bhbzmsAL.jpg', 70, 5),
    ((SELECT id_category FROM categories WHERE name = 'Accesorios'), 'Comedero Lento Antivoracidad', 'Reduce la velocidad de ingesta', 16.99, 10.00, 'https://m.media-amazon.com/images/I/71s8CZG2gGL._AC_UF894,1000_QL80_.jpg', 100, 4),
    ((SELECT id_category FROM categories WHERE name = 'Accesorios'), 'Placa de Identificación Grabada', 'Chapa personalizable con nombre y teléfono', 6.99, 0.00, 'https://i.etsystatic.com/32405017/r/il/594c88/4249341459/il_570xN.4249341459_yymb.jpg', 200, 5),
    ((SELECT id_category FROM categories WHERE name = 'Accesorios'), 'Correa Reflectante Nylon 1.5m', 'Correa resistente con elementos reflectantes', 12.99, 5.00, 'https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcShE1SSWH1MX2Osoc7pok9ilkwmz5JxOAq5uys0m2e66h9sIm6xe8z7rcAWOanrO75Jn7HZibUpDmecb-QjUvPef0jeME2cDFoffnIVISP36yZOl1RCTYPT8RZlupZpSAAA9NXFOeb2bQ0&usqp=CAc', 140, 4);

-- ========================================
-- HIGIENE Y CUIDADO
-- ========================================
INSERT INTO products (id_category, name, product_description, base_price, discount_percentage, picture_product, quantity, rating) VALUES
    ((SELECT id_category FROM categories WHERE name = 'Higiene y Cuidado'), 'Champú Hipoalergénico 500ml', 'Champú suave para piel sensible', 14.99, 10.00, 'https://m.media-amazon.com/images/I/81qmEra6eKL._AC_UF1000,1000_QL80_.jpg', 110, 5),
    ((SELECT id_category FROM categories WHERE name = 'Higiene y Cuidado'), 'Cepillo Quitapelos Profesional', 'Cepillo para eliminar pelo muerto', 18.99, 15.00, 'https://www.trixie.es/WebRoot/StoreWeb/Shops/Trixie/5CFB/84D3/FEEB/FCEC/EB2D/0A6E/0E02/5E7C/PHO_PRO_CLIP_31524-1.jpg', 95, 5),
    ((SELECT id_category FROM categories WHERE name = 'Higiene y Cuidado'), 'Toallitas Limpiadoras (Pack 100)', 'Toallitas húmedas para higiene rápida', 9.99, 5.00, 'https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcRE-lVpz2TGTwwzm5A0PIAxwtToCrEQFhErozc5LUz74GAsw8AaZawx1wKouvS4EFWBYmdOz3uRHqUJRUHuWHZe4BSuXb9o_49uXbAcW7UcR-ykTCNnyr-udUI-T9KvMdjI7cYUng&usqp=CAc', 180, 4),
    ((SELECT id_category FROM categories WHERE name = 'Higiene y Cuidado'), 'Cortauñas Guillotina', 'Cortauñas seguro y preciso', 11.99, 10.00, 'https://m.media-amazon.com/images/I/813-AV45oIL._AC_UF1000,1000_QL80_.jpg', 130, 4),
    ((SELECT id_category FROM categories WHERE name = 'Higiene y Cuidado'), 'Champú Seco en Espuma 200ml', 'Limpieza sin agua', 12.99, 0.00, 'https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcS7gK1hg0TtD8Nq1Qm7k7BzkIDPU4t_TLRhlqWsBy7G4LSzupqa0LnGVwCDc_-UE0V27YT0wwzCe2Z25ICBzOf1Kr2vpvCnWEHkLGzqkIOCViXDtkCC1-w-M9df4JEs-FrXAr46Qg&usqp=CAc', 100, 4),
    ((SELECT id_category FROM categories WHERE name = 'Higiene y Cuidado'), 'Kit Dental Completo', 'Cepillo, pasta y dedal limpiador', 16.99, 20.00, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSK0-zYhzdH4U1We2Y_XG7QkF_rF0uKtWm7zA&s', 85, 5),
    ((SELECT id_category FROM categories WHERE name = 'Higiene y Cuidado'), 'Perfume para Mascotas 100ml', 'Colonia con aroma fresco y duradero', 13.99, 10.00, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQlwQ8DsEwgA2o4Y54LhKSAzqMnqc72KwSOiw&s', 75, 3);

-- ========================================
-- CAMAS Y TRANSPORTINES
-- ========================================
INSERT INTO products (id_category, name, product_description, base_price, discount_percentage, picture_product, quantity, rating) VALUES
    ((SELECT id_category FROM categories WHERE name = 'Camas y Transportines'), 'Cama Ortopédica Viscoelástica L', 'Cama con memoria de forma para perros grandes', 59.99, 20.00, 'https://media.adeo.com/mkp/c7df159cd6c975a12620a86e28f41cb4/media.jpeg?width=3000&height=3000&format=jpg&quality=80&fit=bounds', 50, 5),
    ((SELECT id_category FROM categories WHERE name = 'Camas y Transportines'), 'Cama Cálida de Felpa M', 'Cama suave y cálida tamaño mediano', 34.99, 15.00, 'https://image.made-in-china.com/202f0j00wAmWjsgycloY/Double-Compartment-Ventilated-Cat-Pet-Carrier-Backpack-for-Cats-Dogs.webp', 80, 4),
    ((SELECT id_category FROM categories WHERE name = 'Camas y Transportines'), 'Igloo para Gatos', 'Cueva acogedora de fieltro', 29.99, 10.00, 'https://cdn.manomano.com/images/images_products/2665139/P/7969518_1.jpg', 65, 5),
    ((SELECT id_category FROM categories WHERE name = 'Camas y Transportines'), 'Transportín Rígido IATA Aprobado', 'Transportín para avión talla M', 49.99, 25.00, 'https://images.nexusapp.co/assets/23/53/a6/117013254.jpg', 40, 5),
    ((SELECT id_category FROM categories WHERE name = 'Camas y Transportines'), 'Mochila Transportadora Ventilada', 'Mochila para llevar mascotas pequeñas', 39.99, 15.00, 'https://www.buenapetshop.com/cdn/shop/files/5092_cc266767-9d83-4f35-aaa4-d957d797c3e6_300x300.png?v=1739729365', 60, 4),
    ((SELECT id_category FROM categories WHERE name = 'Camas y Transportines'), 'Caseta de Exterior Impermeable', 'Caseta resistente para jardín talla L', 89.99, 20.00, 'https://m.media-amazon.com/images/I/81qmEra6eKL._AC_UF1000,1000_QL80_.jpg', 30, 5),
    ((SELECT id_category FROM categories WHERE name = 'Camas y Transportines'), 'Hamaca Colgante para Gatos', 'Hamaca para ventana con ventosas', 24.99, 10.00, 'https://www.trixie.es/WebRoot/StoreWeb/Shops/Trixie/5CFB/84D3/FEEB/FCEC/EB2D/0A6E/0E02/5E7C/PHO_PRO_CLIP_31524-1.jpg', 70, 4);

-- ========================================
-- SNACKS Y PREMIOS
-- ========================================
INSERT INTO products (id_category, name, product_description, base_price, discount_percentage, picture_product, quantity, rating) VALUES
    ((SELECT id_category FROM categories WHERE name = 'Snacks y Premios'), 'Huesos Dentales 28 unidades', 'Huesos masticables para higiene dental', 19.99, 15.00, 'https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcRE-lVpz2TGTwwzm5A0PIAxwtToCrEQFhErozc5LUz74GAsw8AaZawx1wKouvS4EFWBYmdOz3uRHqUJRUHuWHZe4BSuXb9o_49uXbAcW7UcR-ykTCNnyr-udUI-T9KvMdjI7cYUng&usqp=CAc', 150, 5),
    ((SELECT id_category FROM categories WHERE name = 'Snacks y Premios'), 'Snacks Naturales de Pollo 500g', 'Tiras de pechuga de pollo deshidratada', 14.99, 10.00, 'https://m.media-amazon.com/images/I/813-AV45oIL._AC_UF1000,1000_QL80_.jpg', 120, 5),
    ((SELECT id_category FROM categories WHERE name = 'Snacks y Premios'), 'Premios para Entrenamiento 200g', 'Pequeños premios bajos en calorías', 9.99, 5.00, 'https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcS7gK1hg0TtD8Nq1Qm7k7BzkIDPU4t_TLRhlqWsBy7G4LSzupqa0LnGVwCDc_-UE0V27YT0wwzCe2Z25ICBzOf1Kr2vpvCnWEHkLGzqkIOCViXDtkCC1-w-M9df4JEs-FrXAr46Qg&usqp=CAc', 180, 4),
    ((SELECT id_category FROM categories WHERE name = 'Snacks y Premios'), 'Snacks Dentales para Gatos 60g', 'Premios crujientes para limpieza dental', 7.99, 0.00, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSK0-zYhzdH4U1We2Y_XG7QkF_rF0uKtWm7zA&s', 200, 4),
    ((SELECT id_category FROM categories WHERE name = 'Snacks y Premios'), 'Orejas de Cerdo Naturales (Pack 10)', 'Orejas 100% naturales para masticar', 16.99, 20.00, 'https://static.miscota.com/media/1/photos/products/511376/030390004-64ccd75f9c67a_g.png', 90, 5),
    ((SELECT id_category FROM categories WHERE name = 'Snacks y Premios'), 'Galletas Caseras Variadas 300g', 'Galletas artesanales sin conservantes', 11.99, 10.00, 'https://media.adeo.com/mkp/c7df159cd6c975a12620a86e28f41cb4/media.jpeg?width=3000&height=3000&format=jpg&quality=80&fit=bounds', 140, 4),
    ((SELECT id_category FROM categories WHERE name = 'Snacks y Premios'), 'Palitos Dentales Veganos 14 unidades', 'Palitos vegetales para limpieza dental', 12.99, 15.00, 'https://image.made-in-china.com/202f0j00wAmWjsgycloY/Double-Compartment-Ventilated-Cat-Pet-Carrier-Backpack-for-Cats-Dogs.webp', 160, 4);
