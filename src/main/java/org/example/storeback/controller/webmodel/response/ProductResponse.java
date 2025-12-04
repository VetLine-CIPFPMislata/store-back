package org.example.storeback.controller.webmodel.response;

import org.example.storeback.domain.models.Category;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        String productDescription,
        Category category,
        String pictureProduct,
        int quantity,
        BigDecimal basePrice,
        BigDecimal discountPercentage,
        BigDecimal price
) {
}
