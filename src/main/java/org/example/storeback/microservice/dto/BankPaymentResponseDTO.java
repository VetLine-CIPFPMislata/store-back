package org.example.storeback.microservice.dto;

import java.math.BigDecimal;

public record BankPaymentResponseDTO(
        String ibanDestino,
        BigDecimal importe,
        String concepto,
        String mensaje,
        boolean exito) {
}
