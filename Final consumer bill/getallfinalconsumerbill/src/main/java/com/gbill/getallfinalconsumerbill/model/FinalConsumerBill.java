package com.gbill.getallfinalconsumerbill.model;

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
    private List<ProductBill> products;

    // Totals
    private double nonTaxedSales;
    private double exemptSales;
    private double taxedSales;
    private double iva; // 13% de IVA
    private double perceivedIva;
    private double withheldIva;
    private double totalWithIva;
}
