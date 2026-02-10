package org.example.storeback.domain.service.impl;

import org.example.storeback.controller.webmodel.request.PaymentCardRequest;
import org.example.storeback.domain.service.PaymentService;
import org.example.storeback.domain.service.dto.PaymentResultDto;
import org.example.storeback.microservice.BankPaymentService;
import org.example.storeback.microservice.dto.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final BankPaymentService bankPaymentService;

    private static final String BANK_API_LOGIN = "jperez";
    private static final String BANK_API_TOKEN = "BANK_SECRET_TOKEN_2024";

    private static final String STORE_IBAN = "ES9121000418450200051332";

    public PaymentServiceImpl(BankPaymentService bankPaymentService) {
        this.bankPaymentService = bankPaymentService;
    }

    @Override
    public PaymentResultDto processCardPayment(PaymentCardRequest cardData, BigDecimal amount, String concept,
            String destinationIban) {

        AutorizacionDTO autorizacion = new AutorizacionDTO(BANK_API_LOGIN, BANK_API_TOKEN);

        OrigenDTO origen = new OrigenDTO(
                cardData.numeroTarjeta(),
                cardData.fechaCaducidad(),
                cardData.cvc(),
                cardData.nombreCompleto());

        DestinoDTO destino = new DestinoDTO(STORE_IBAN);

        PagoDetailsDTO pago = new PagoDetailsDTO(amount, concept);

        BankPaymentRequestDTO paymentRequest = new BankPaymentRequestDTO(
                autorizacion,
                origen,
                destino,
                pago);

        BankPaymentResponseDTO response = bankPaymentService.processPayment(paymentRequest);

        return new PaymentResultDto(
                response.exito(),
                response.mensaje(),
                null, // bank-back no devuelve numeroReferencia
                response.importe(),
                null, // bank-back no devuelve fechaHora
                response.exito() ? null : "PAYMENT_ERROR");
    }
}
