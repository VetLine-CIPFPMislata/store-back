package org.example.storeback.domain.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CheckoutDto(
    @NotBlank(message = "La dirección es requerida")
    @Size(max = 255, message = "La dirección no puede exceder 255 caracteres")
    String address
) {
}

