package org.example.storeback.domain.service.dto;

import org.example.storeback.domain.models.OrderState;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
    Long id,
    Integer totalProducts,
    BigDecimal totalPrice,
    OrderState state,
    ClientDto user,
    LocalDateTime createdAt,
    LocalDateTime orderAt,
    String address,
    List<OrderItemDto> items
) {
}
