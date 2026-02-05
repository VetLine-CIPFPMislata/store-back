package org.example.storeback.domain.repository.entity;

public record OrderItemEntity(
        Long id,
        Integer quantity,
        Long orderId,
        ProductEntity product
) {
}

