package org.example.storeback.controller.webmodel.request;

public record PaymentCardRequest(
        String numeroTarjeta,
        String fechaCaducidad,
        String cvc,
        String nombreCompleto) {
}
