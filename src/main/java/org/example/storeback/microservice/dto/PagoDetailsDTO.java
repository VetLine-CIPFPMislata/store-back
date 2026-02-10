package org.example.storeback.microservice.dto;

import java.math.BigDecimal;

public record PagoDetailsDTO(
        BigDecimal importe,
        String concepto) {
}
