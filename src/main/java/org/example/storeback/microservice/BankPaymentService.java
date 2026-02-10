package org.example.storeback.microservice;

import org.example.storeback.microservice.dto.BankPaymentRequestDTO;
import org.example.storeback.microservice.dto.BankPaymentResponseDTO;

public interface BankPaymentService {
    BankPaymentResponseDTO processPayment(BankPaymentRequestDTO paymentRequest);
}
