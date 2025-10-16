package com.gbill.getallfinalconsumerbill.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class FinalConsumerBill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String generationCode; 
    private String controlNumber; 
    private LocalDateTime billGenerationDate;
    private String account;
    private String paymentCondition;

    //transmitter
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "transmitter_id")
    private Transmitter transmitter;    

    //receiver
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "receiver_id")
    private Receiver receiver;  

    //products
    @OneToMany(mappedBy = "finalConsumerBill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillItem> products;

    // Totals
    private double nonTaxedSales;
    private double exemptSales;
    private double taxedSales;
    private double iva; // 13% de IVA
    private double perceivedIva;
    private double withheldIva;
    private double totalWithIva;
}
