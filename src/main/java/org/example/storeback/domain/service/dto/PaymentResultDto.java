package org.example.storeback.domain.service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResultDto(
        boolean success,
        String message,
        String referenceNumber,
        BigDecimal paidAmount,
        LocalDateTime dateTime,
        String errorCode) {
}
