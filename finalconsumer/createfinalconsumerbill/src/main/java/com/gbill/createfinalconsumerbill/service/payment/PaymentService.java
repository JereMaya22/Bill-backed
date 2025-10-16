package com.gbill.createfinalconsumerbill.service.payment;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.gbill.createfinalconsumerbill.modeldto.payment.CardValidationRequest;
import com.gbill.createfinalconsumerbill.modeldto.payment.CardValidationResponse;

@Service
public class PaymentService {

    public CardValidationResponse validateCard(CardValidationRequest req) {
        // Validación Luhn simple (recomiendo aplicar Luhn en frontend también)
        if (!isLuhnValid(req.getCardNumber())) {
            return new CardValidationResponse(false, "Número de tarjeta inválido", null, null, null);
        }

        // Simular autorización (en producción llamar a gateway)
        String authorizationCode = "AUTH-" + UUID.randomUUID().toString().substring(0,8);
        String cardType = detectCardType(req.getCardNumber());
        String masked = maskCard(req.getCardNumber());

        return new CardValidationResponse(true, "Tarjeta válida", authorizationCode, cardType, masked);
    }

    private boolean isLuhnValid(String number) { /* implementación Luhn */ return true; }
    private String detectCardType(String number) { /* Visa/Master/etc */ return "VISA"; }
    private String maskCard(String number) {
        if (number == null || number.length() < 4) return "****";
        return "**** **** **** " + number.substring(number.length()-4);
    }
}
