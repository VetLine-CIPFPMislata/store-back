package org.example.storeback.domain.service.impl;

import org.example.storeback.controller.webmodel.request.AddProductToCartRequest;
import org.example.storeback.domain.service.CartService;
import org.example.storeback.domain.service.dto.CartDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CartServiceRemoveItemTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setup() {
        // Limpiar datos previos
        jdbcTemplate.execute("DELETE FROM cart_items");
        jdbcTemplate.execute("DELETE FROM carts");

        // Verificar que hay productos
        Integer productCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM products", Integer.class);
        System.out.println("=== SETUP ===");
        System.out.println("Productos en BD: " + productCount);
    }

    @Test
    public void testRemoveCartItem_shouldDeleteItemSuccessfully() {
        // Arrange: Agregar un producto al carrito
        Long userId = 1L; // Usuario de prueba existente
        Long productId = 1L; // Primer producto de la BD

        AddProductToCartRequest addRequest = new AddProductToCartRequest(productId, 2);

        CartDto cartAfterAdd = cartService.addProductToCart(userId, addRequest);

        assertNotNull(cartAfterAdd);
        assertFalse(cartAfterAdd.items().isEmpty());
        assertEquals(1, cartAfterAdd.items().size());

        Long cartItemId = cartAfterAdd.items().get(0).id();
        int initialItemCount = cartAfterAdd.items().size();

        System.out.println("=== ANTES DE ELIMINAR ===");
        System.out.println("CartItemId a eliminar: " + cartItemId);
        System.out.println("Items en carrito: " + initialItemCount);
        System.out.println("Total productos: " + cartAfterAdd.totalProducts());

        // Verificar en BD directamente
        Integer countBefore = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM cart_items WHERE id_cart_item = ?",
            Integer.class,
            cartItemId
        );
        System.out.println("Items en BD ANTES del delete: " + countBefore);

        // Act: Eliminar el item del carrito
        cartService.removeCartItem(userId, cartItemId);

        // Verificar en BD directamente después del delete
        Integer countAfter = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM cart_items WHERE id_cart_item = ?",
            Integer.class,
            cartItemId
        );
        System.out.println("=== DESPUÉS DE ELIMINAR ===");
        System.out.println("Items en BD DESPUÉS del delete: " + countAfter);

        // Assert: Verificar que el item fue eliminado
        CartDto cartAfterRemove = cartService.findByUserId(userId);

        System.out.println("Items en carrito DTO: " + cartAfterRemove.items().size());
        System.out.println("Total productos: " + cartAfterRemove.totalProducts());

        assertEquals(0, countAfter, "El item debería estar eliminado de la BD");
        assertEquals(0, cartAfterRemove.items().size(), "El carrito debería estar vacío después de eliminar el único item");
        assertEquals(0, cartAfterRemove.totalProducts(), "El total de productos debería ser 0");
    }

    @Test
    public void testRemoveCartItem_withMultipleItems_shouldDeleteOnlyOne() {
        // Arrange: Agregar múltiples productos
        Long userId = 1L;

        // Agregar primer producto
        AddProductToCartRequest addRequest1 = new AddProductToCartRequest(1L, 1);
        cartService.addProductToCart(userId, addRequest1);

        // Agregar segundo producto
        AddProductToCartRequest addRequest2 = new AddProductToCartRequest(2L, 2);
        CartDto cartWithTwoItems = cartService.addProductToCart(userId, addRequest2);

        assertEquals(2, cartWithTwoItems.items().size());

        Long cartItemIdToRemove = cartWithTwoItems.items().get(0).id();

        System.out.println("=== ANTES DE ELIMINAR (2 items) ===");
        System.out.println("Items: " + cartWithTwoItems.items().size());
        System.out.println("Item a eliminar: " + cartItemIdToRemove);

        Integer countBefore = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM cart_items",
            Integer.class
        );
        System.out.println("Total items en BD ANTES: " + countBefore);

        // Act: Eliminar solo un item
        cartService.removeCartItem(userId, cartItemIdToRemove);

        Integer countAfter = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM cart_items",
            Integer.class
        );
        System.out.println("=== DESPUÉS DE ELIMINAR ===");
        System.out.println("Total items en BD DESPUÉS: " + countAfter);

        // Assert: Verificar que queda solo un item
        CartDto cartAfterRemove = cartService.findByUserId(userId);

        System.out.println("Items restantes en DTO: " + cartAfterRemove.items().size());

        assertEquals(1, countAfter, "Debería quedar 1 item en la BD");
        assertEquals(1, cartAfterRemove.items().size(), "Debería quedar 1 item en el carrito");
        assertNotEquals(cartItemIdToRemove, cartAfterRemove.items().get(0).id(),
            "El item eliminado no debería estar en el carrito");
    }
}

