package com.gbill.getallfinalconsumerbill.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "bill")
@EqualsAndHashCode(exclude = "bill")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentType; // EFECTIVO o TARJETA
    private String cardType; // VISA, MASTERCARD, etc.
    private String maskedCardNumber; // **** **** **** 1234
    private String cardHolder; // Nombre del titular
    private String authorizationCode; // Código devuelto tras validación

    @OneToOne
    @JoinColumn(name = "bill_id")
    private FinalConsumerBill bill;
}
