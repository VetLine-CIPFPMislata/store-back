package org.example.storeback.controller.webmodel.response;

import java.math.BigDecimal;
import java.util.List;

/**
 * Response del carrito para el frontend
 */
public record CartResponse(
    Long id,
    Long userId,
    String userName,
    String userEmail,
    Integer totalProducts,
    BigDecimal totalPrice,
    List<CartItemResponse> items
) {
}

