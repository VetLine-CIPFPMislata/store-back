package org.example.storeback.domain.service.dto;

import org.example.storeback.domain.models.Category;

import java.math.BigDecimal;

public record ProductDto(
        Long id,
        Category category,
        String name,
        String productDescription,
        BigDecimal basePrice,
        BigDecimal price,
        BigDecimal discountPercentage,
        String pictureProduct,
        int quantity,
        int rating
) {

}
