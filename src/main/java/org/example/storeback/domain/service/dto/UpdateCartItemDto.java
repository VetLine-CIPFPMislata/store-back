package org.example.storeback.domain.service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateCartItemDto(
    @NotNull(message = "El ID del item es requerido")
    Long itemId,

    @NotNull(message = "La cantidad es requerida")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    Integer quantity
) {
}

