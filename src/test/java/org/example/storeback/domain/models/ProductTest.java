package org.example.storeback.domain.models;

import org.example.storeback.domain.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductTest {
    @Test
    @DisplayName("dado un descuento del 30% a un producto comprobar nuevo precio es correcto")
    void testCalculatePriceWithDiscount() {
        // Given
        Product product = new Product(
        1L,
            new Category(23L,
            "Jueguetes",
            "Jueguetes para las mascotas"),
        "Pelota",
        "Pelota para mascotas",
        new BigDecimal("100.00"),
        new BigDecimal("30.00"),
        "http://img",
        5,
        4
        );


        // When
        BigDecimal priceWithDiscount = product.calculateFinalPrice();

        // Then
        assertEquals(new BigDecimal("70.00"), priceWithDiscount);
    }
    @ParameterizedTest
    @CsvSource({
            "0, 100.00, 100.00",
            "50, 200.00, 100.00",
            "100, 150.00, 0.00",
            "25, 80.00, 60.00"
    })
    @DisplayName("Calcular precio final con diferentes porcentajes de descuento")
    void testCalculatePriceWithVariousDiscounts(String discountPercentage, String basePrice, String expectedPrice){
        Product product = new Product(
                1L,
                new Category(2L,
                        "Jueguetes",
                        "Jueguetes para las mascotas"),
                "Pelota",
                "Pelota para mascotas",
                new BigDecimal(basePrice),
                new BigDecimal(discountPercentage),
                "http://img",
                5,
                4
        );
        BigDecimal priceWithDiscount = product.calculateFinalPrice();
        assertEquals(new BigDecimal(expectedPrice), priceWithDiscount);
    }

    @Test
    @DisplayName("Comprobar que devuelve una excepion cuando el descuento es mayor que 100")
    void testCalculatePriceWithInvalidDiscount() {

        assertThrows(BusinessException.class, () -> new Product(
                1L,
                new Category(23L,
                        "Jueguetes",
                        "Jueguetes para las mascotas"),
                "Pelota",
                "Pelota para mascotas",
                new BigDecimal("100.00"),
                new BigDecimal("120.00"),
                "http://img",
                5,
                4
        ));
    }

    @Test
    @DisplayName("Si discountPercentage es null debe devolver el basePrice")
    void testCalculatePriceWithNullDiscountReturnsBasePrice() {
        Product product = new Product(
                1L,
                new Category(2L, "Cat", "Desc"),
                "Item",
                "Desc",
                new BigDecimal("123.45"),
                null,
                "http://img",
                1,
                3
        );
        BigDecimal result = product.calculateFinalPrice();
        assertEquals(new BigDecimal("123.45"), result);
    }

    @Test
    @DisplayName("Si basePrice es null debe devolver 0")
    void testCalculatePriceWithNullBasePriceReturnsZero() {
        Product product = new Product(
                1L,
                new Category(2L, "Cat", "Desc"),
                "Item",
                "Desc",
                null,
                new BigDecimal("10.00"),
                "http://img",
                1,
                3
        );
        BigDecimal result = product.calculateFinalPrice();
        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    @DisplayName("Si basePrice es 0 debe devolver 0")
    void testCalculatePriceWithZeroBasePriceReturnsZero() {
        Product product = new Product(
                1L,
                new Category(2L, "Cat", "Desc"),
                "Item",
                "Desc",
                BigDecimal.ZERO,
                new BigDecimal("10.00"),
                "http://img",
                1,
                3
        );
        BigDecimal result = product.calculateFinalPrice();
        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    @DisplayName("Comprobar redondeo a 2 decimales y escala del resultado")
    void testCalculatePriceRoundingAndScale() {
        BigDecimal basePrice = new BigDecimal("100.005");
        BigDecimal discount = new BigDecimal("0.3333");
        Product product = new Product(
                1L,
                new Category(2L, "Cat", "Desc"),
                "Item",
                "Desc",
                basePrice,
                discount,
                "http://img",
                1,
                3
        );
        // Calcular expected usando misma lógica que en Product
        BigDecimal percent = discount.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
        BigDecimal expected = basePrice.subtract(basePrice.multiply(percent)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal result = product.calculateFinalPrice();
        assertEquals(expected, result);
        assertEquals(2, result.scale());
    }

}
