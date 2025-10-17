package com.gbill.createfinalconsumerbill.service.payment;

import java.util.UUID;
import org.springframework.stereotype.Service;
import com.gbill.createfinalconsumerbill.modeldto.payment.CardValidationRequest;
import com.gbill.createfinalconsumerbill.modeldto.payment.CardValidationResponse;

@Service
public class PaymentService {

    public CardValidationResponse validateCard(CardValidationRequest req) {
        if (req == null || req.getCardNumber() == null || req.getCardNumber().isBlank()) {
            return new CardValidationResponse(false, "El número de tarjeta no puede estar vacío", null, null, null);
        }

        // 🔹 Limpiar espacios o guiones
        String cardNumber = req.getCardNumber().replaceAll("[^0-9]", "");

        // 🔹 Validación Luhn
        if (!isLuhnValid(cardNumber)) {
            return new CardValidationResponse(false, "Número de tarjeta inválido", null, null, null);
        }

        // 🔹 Detectar tipo de tarjeta
        String cardType = detectCardType(cardNumber);
        if ("DESCONOCIDA".equals(cardType)) {
            return new CardValidationResponse(false, "Tipo de tarjeta no reconocido", null, null, null);
        }

        // 🔹 Simular autorización
        String authorizationCode = "AUTH-" + UUID.randomUUID().toString().substring(0, 8);
        String maskedCard = maskCard(cardNumber);

        return new CardValidationResponse(true, "Tarjeta válida", authorizationCode, cardType, maskedCard);
    }

    // === Algoritmo de Luhn ===
    private boolean isLuhnValid(String number) {
        int sum = 0;
        boolean alternate = false;

        for (int i = number.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(number.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) n = (n % 10) + 1;
            }
            sum += n;
            alternate = !alternate;
        }

        return (sum % 10 == 0);
    }

    // === Detección básica de tipo de tarjeta ===
    private String detectCardType(String number) {
        if (number.startsWith("4")) {
            return "VISA";
        } else if (number.matches("^5[1-5].*")) {
            return "MASTERCARD";
        } else if (number.matches("^3[47].*")) {
            return "AMEX";
        } else if (number.matches("^6(?:011|5).*")) {
            return "DISCOVER";
        } else {
            return "DESCONOCIDA";
        }
    }

    // === Enmascarar tarjeta ===
    private String maskCard(String number) {
        if (number == null || number.length() < 4) return "****";
        return "**** **** **** " + number.substring(number.length() - 4);
    }
}
