package org.example.storeback.domain.service.dto;

import jakarta.validation.constraints.NotNull;

public record RemoveItemFromCartDto(
    @NotNull(message = "El ID del item es requerido")
    Long itemId
) {
}

