package org.example.storeback.microservice.dto;

public record OrigenDTO(
        String numeroTarjeta,
        String fechaCaducidad,
        String cvc,
        String nombreCompleto) {
}
