package com.gbill.createfinalconsumerbill.modeldto.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private String paymentType; // EFECTIVO o TARJETA
    private String cardType; // VISA, MASTERCARD
    private String maskedCardNumber; // **** **** **** 1234
    private String cardHolder; // Nombre del titular
    private String authorizationCode; // Código devuelto por validación
}
