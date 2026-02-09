package org.example.storeback.controller;

import org.example.storeback.controller.webmodel.request.PaymentRequest;
import org.example.storeback.domain.service.PaymentService;
import org.example.storeback.domain.service.dto.PaymentResultDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/card")
    public ResponseEntity<PaymentResultDto> processCardPayment(
            @RequestBody PaymentRequest paymentRequest) {

        PaymentResultDto result = paymentService.processCardPayment(
                paymentRequest.cardData(),
                paymentRequest.amount(),
                paymentRequest.concept(),
                null);

        if (result.success()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
}
