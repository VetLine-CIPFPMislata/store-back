package org.example.storeback.controller.webmodel.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Response de una orden para el frontend
 */
public record OrderResponse(
    Long id,
    Long userId,
    String userName,
    String userEmail,
    Integer totalProducts,
    BigDecimal totalPrice,
    String state,
    String address,
    LocalDateTime createdAt,
    LocalDateTime orderAt,
    List<OrderItemResponse> items
) {
}

