package org.example.storeback.domain.service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddProductToCartDto(
        @NotNull(message = "El ID del producto es requerido")
        Long productId,

        @NotNull(message = "La cantidad es requerida")
        @Min(value = 1, message = "La cantidad debe ser al menos 1")
        Integer quantity
) {
}