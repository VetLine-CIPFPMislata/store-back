package org.example.storeback.domain.service.dto;

import jakarta.validation.constraints.*;
import org.example.storeback.domain.models.Category;

import java.math.BigDecimal;

public record ProductDto(
        Long id,

        @NotNull(message = "La categoría no puede ser nula")
        Category category,

        @NotBlank(message = "El nombre no puede estar vacío")
        @Size(max = 255, message = "El nombre no puede exceder 255 caracteres")
        String name,

        @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
        String productDescription,

        @NotNull(message = "El precio base no puede ser nulo")
        @DecimalMin(value = "0.0", inclusive = false, message = "El precio base debe ser mayor que 0")
        @Digits(integer = 10, fraction = 2, message = "El precio base debe tener máximo 10 dígitos enteros y 2 decimales")
        BigDecimal basePrice,

        BigDecimal price,

        @DecimalMin(value = "0.0", message = "El porcentaje de descuento no puede ser negativo")
        @DecimalMax(value = "100.0", message = "El porcentaje de descuento no puede exceder 100")
        @Digits(integer = 3, fraction = 2, message = "El porcentaje debe tener máximo 3 dígitos enteros y 2 decimales")
        BigDecimal discountPercentage,


        @Size(max = 500, message = "La URL de la imagen no puede exceder 500 caracteres")
        String pictureProduct,

        @NotNull(message = "La cantidad no puede ser nula")
        @Min(value = 0, message = "La cantidad no puede ser negativa")
        Integer quantity,

        @NotNull(message = "El rating no puede ser nulo")
        @Min(value = 0, message = "El rating mínimo es 0")
        @Max(value = 5, message = "El rating máximo es 5")
        Integer rating
) {

}
