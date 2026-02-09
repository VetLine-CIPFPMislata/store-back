package org.example.storeback.controller.webmodel.request;

import java.math.BigDecimal;

public record PaymentRequest(
        PaymentCardRequest cardData,
        BigDecimal amount,
        String concept) {
}
