package org.example.storeback.domain.service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CartItemDto(
    Long id,

    @NotNull(message = "La cantidad no puede ser nula")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    Integer quantity,

    Long cartId,

    @NotNull(message = "El producto no puede ser nulo")
    ProductDto product
) {
}
