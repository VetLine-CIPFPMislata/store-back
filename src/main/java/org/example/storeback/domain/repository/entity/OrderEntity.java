package org.example.storeback.domain.repository.entity;

import org.example.storeback.domain.models.OrderState;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderEntity(
        Long id,
        Integer totalProducts,
        BigDecimal totalPrice,
        OrderState state,
        ClientEntity user,
        LocalDateTime createdAt,
        LocalDateTime orderAt,
        String address,
        List<OrderItemEntity> items
) {
}

