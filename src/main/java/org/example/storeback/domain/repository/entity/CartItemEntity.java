package org.example.storeback.domain.repository.entity;

import java.math.BigDecimal;

public record CartItemEntity(
        Long id,
        Integer quantity,
        Long cartId,
        ProductEntity product,
        BigDecimal unitPrice
) {
}
