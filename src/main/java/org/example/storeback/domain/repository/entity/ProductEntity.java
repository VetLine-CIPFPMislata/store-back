package org.example.storeback.domain.repository.entity;

import org.example.storeback.domain.models.Category;

import java.math.BigDecimal;

public record ProductEntity(
        Long id,
        Category category,
        String name,
        String productDescription,
        BigDecimal basePrice,
        BigDecimal discountPercentage,
        String pictureProduct,
        int quantity,
        int rating
) {
}
