package org.example.storeback.microservice.dto;

public record BankPaymentRequestDTO(
        AutorizacionDTO autorizacion,
        OrigenDTO origen,
        DestinoDTO destino,
        PagoDetailsDTO pago) {
}
