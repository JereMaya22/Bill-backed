package com.gbill.createfinalconsumerbill.modeldto.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardValidationResponse {
    private boolean valid;
    private String message;
    private String authorizationCode; //"AUTH-20251016-ABC123"
    private String cardType;
    private String maskedCardNumber;
}

