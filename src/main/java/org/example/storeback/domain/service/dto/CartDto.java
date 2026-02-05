package org.example.storeback.domain.service.dto;

import java.math.BigDecimal;
import java.util.List;

public record CartDto(
    Long id,
    Integer totalProducts,
    BigDecimal totalPrice,
    ClientDto user,
    List<CartItemDto> items
) {
}
