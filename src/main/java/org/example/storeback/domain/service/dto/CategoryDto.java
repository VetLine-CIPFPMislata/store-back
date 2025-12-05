package org.example.storeback.domain.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryDto(
        Long id,
        @NotBlank(message = "El nombre de la categoría no puede estar vacío")
        @Size(max = 100, message = "El nombre de la categoría no puede exceder 100 caracteres")
        String name,

        @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
        String description
) {
}
