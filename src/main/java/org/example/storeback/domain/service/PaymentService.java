package org.example.storeback.domain.service;

import org.example.storeback.controller.webmodel.request.PaymentCardRequest;
import org.example.storeback.domain.service.dto.PaymentResultDto;

import java.math.BigDecimal;

public interface PaymentService {
    PaymentResultDto processCardPayment(PaymentCardRequest cardData, BigDecimal amount, String concept,
            String destinationIban);
}
