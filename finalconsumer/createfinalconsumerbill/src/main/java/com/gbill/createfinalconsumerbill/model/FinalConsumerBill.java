package com.gbill.createfinalconsumerbill.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
    private String companyName;
    private String companyDocument; 
    private String companyAddress;
    private String companyEmail;
    private String companyPhone;    

    //receiver
    private String customerName;
    private String customerDocument; 
    private String customerAddress;
    private String customerEmail;
    private String customerPhone;  

    //products
    @OneToMany(mappedBy = "finalConsumerBill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillItem> products;

    // Totals
    private Double nonTaxedSales;
    private Double exemptSales;
    private Double taxedSales;
    private Double iva; // 13% de IVA
    private Double perceivedIva;
    private Double withheldIva;
    private Double totalWithIva;

}
