package org.example.storeback.microservice.impl;

import org.example.storeback.microservice.BankPaymentService;
import org.example.storeback.microservice.dto.BankPaymentRequestDTO;
import org.example.storeback.microservice.dto.BankPaymentResponseDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class BankPaymentServiceImpl implements BankPaymentService {

    private final RestTemplate restTemplate;
    private static final String BANK_API_URL = "http://localhost:8083";

    public BankPaymentServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public BankPaymentResponseDTO processPayment(BankPaymentRequestDTO paymentRequest) {
        try {
            String url = BANK_API_URL + "/api/pago_tarjeta";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<BankPaymentRequestDTO> request = new HttpEntity<>(paymentRequest, headers);

            ResponseEntity<BankPaymentResponseDTO> response = restTemplate.postForEntity(
                    url,
                    request,
                    BankPaymentResponseDTO.class);

            if (response.getBody() != null) {
                return response.getBody();
            } else {
                return new BankPaymentResponseDTO(
                        null,
                        null,
                        null,
                        "Error: Respuesta vacía del banco",
                        false);
            }
        } catch (Exception e) {
            return new BankPaymentResponseDTO(
                    null,
                    null,
                    null,
                    "Error al procesar el pago: " + e.getMessage(),
                    false);
        }
    }
}
