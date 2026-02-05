package org.example.storeback.domain.service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateOrderDto(
    @NotNull(message = "El ID del usuario es requerido")
    Long userId,

    @NotBlank(message = "La dirección es requerida")
    @Size(max = 255, message = "La dirección no puede exceder 255 caracteres")
    String address,

    @NotEmpty(message = "Debe incluir al menos un item")
    @Valid
    List<OrderItemDto> items
) {
    public record OrderItemDto(
        @NotNull(message = "El ID del producto es requerido")
        Long productId,

        @NotNull(message = "La cantidad es requerida")
        @Min(value = 1, message = "La cantidad debe ser al menos 1")
        Integer quantity
    ) {
    }
}


