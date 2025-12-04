package org.example.storeback.controller.webmodel.request;

import org.example.storeback.domain.models.Category;

import java.math.BigDecimal;

public record ProductUpdateRequest (
        Long id,
        String name,
        String productDescription,
        Category category,
        String pictureProduct,
        int quantity,
        BigDecimal basePrice,
        BigDecimal discountPercentage
){
}
