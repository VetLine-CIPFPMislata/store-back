package org.example.storeback.domain.repository.entity;

import java.math.BigDecimal;
import java.util.List;

public record CartEntity(
        Long id,
        Integer totalProducts,
        BigDecimal totalPrice,
        ClientEntity user,
        List<CartItemEntity> items
) {
}
