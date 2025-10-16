package com.gbill.createfinalconsumerbill.model;

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

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
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
    private Double nonTaxedSales;
    private Double exemptSales;
    private Double taxedSales;
    private Double iva;
    private Double perceivedIva;
    private Double withheldIva;
    private Double totalWithIva;

    private String pdfPath;

}
