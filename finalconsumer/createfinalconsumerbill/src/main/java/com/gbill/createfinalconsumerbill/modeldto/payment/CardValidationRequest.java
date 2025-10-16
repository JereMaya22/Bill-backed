package com.gbill.createfinalconsumerbill.modeldto.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardValidationRequest {
    private String cardNumber;
    private String holderName;
    private String expirationDate; // MM/YY
    private String cvv;
}

