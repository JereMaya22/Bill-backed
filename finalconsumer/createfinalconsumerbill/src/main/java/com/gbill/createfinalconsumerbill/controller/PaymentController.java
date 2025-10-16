package com.gbill.createfinalconsumerbill.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gbill.createfinalconsumerbill.modeldto.payment.CardValidationRequest;
import com.gbill.createfinalconsumerbill.modeldto.payment.CardValidationResponse;
import com.gbill.createfinalconsumerbill.service.payment.PaymentService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/validate-card")
    public ResponseEntity<CardValidationResponse> validateCard(@RequestBody CardValidationRequest req) {
        CardValidationResponse resp = paymentService.validateCard(req);
        return ResponseEntity.ok(resp);
    }
}

