package org.example.storeback.domain.service.impl;

import org.example.storeback.domain.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CartServiceDeleteDebugTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setup() {
        // Limpiar datos previos
        jdbcTemplate.execute("DELETE FROM cart_items");
        jdbcTemplate.execute("DELETE FROM carts");
    }

    @Test
    public void testDeleteCartItem_directDatabaseOperations() {
        System.out.println("=== TEST: Verificación directa de delete en BD ===");

        // Paso 1: Insertar un carrito directamente
        jdbcTemplate.execute("INSERT INTO carts (id_cart, total_products, total_price) VALUES (999, 1, 10.00)");

        // Paso 2: Insertar un cart_item directamente
        jdbcTemplate.execute(
            "INSERT INTO cart_items (id_cart_item, id_cart, id_product, quantity, unit_price) " +
            "VALUES (888, 999, 1, 2, 5.00)"
        );

        // Verificar que el item existe
        Integer countBefore = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM cart_items WHERE id_cart_item = 888",
            Integer.class
        );
        System.out.println("Items ANTES del delete: " + countBefore);
        assertEquals(1, countBefore);

        // Paso 3: Intentar eliminar usando el servicio
        System.out.println("Llamando a removeCartItem(1, 888)...");

        try {
            cartService.removeCartItem(1L, 888L);
            System.out.println("removeCartItem ejecutado sin excepción");
        } catch (Exception e) {
            System.out.println("ERROR al eliminar: " + e.getMessage());
            e.printStackTrace();
        }

        // Verificar si el item fue eliminado
        Integer countAfter = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM cart_items WHERE id_cart_item = 888",
            Integer.class
        );
        System.out.println("Items DESPUÉS del delete: " + countAfter);

        // Esta es la verificación crítica
        assertEquals(0, countAfter, "El cart item debería haber sido eliminado de la BD");
    }

    @Test
    public void testDeleteCartItem_checkTransactionCommit() {
        System.out.println("=== TEST: Verificar commit de transacción ===");

        // Insertar datos de prueba
        jdbcTemplate.execute("INSERT INTO carts (id_cart, total_products, total_price) VALUES (777, 1, 10.00)");
        jdbcTemplate.execute(
            "INSERT INTO cart_items (id_cart_item, id_cart, id_product, quantity, unit_price) " +
            "VALUES (666, 777, 1, 1, 10.00)"
        );

        Integer countBefore = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM cart_items WHERE id_cart_item = 666",
            Integer.class
        );
        System.out.println("Registros ANTES: " + countBefore);

        // Eliminar directamente con JDBC para comparar
        int rowsDeleted = jdbcTemplate.update("DELETE FROM cart_items WHERE id_cart_item = 666");
        System.out.println("Filas eliminadas por JDBC: " + rowsDeleted);

        Integer countAfterJdbc = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM cart_items WHERE id_cart_item = 666",
            Integer.class
        );
        System.out.println("Registros DESPUÉS del delete JDBC: " + countAfterJdbc);

        assertEquals(0, countAfterJdbc, "JDBC delete debería funcionar correctamente");
    }
}

