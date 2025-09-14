package com.gbill.createfinalconsumerbill.modeldto;

import java.time.LocalDateTime;
import java.util.List;

import com.gbill.createfinalconsumerbill.model.ProductBill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFinalConsumerBillDTO {

    private String generationCode; // 36 caracteres
    private String controlNumber; // DTE-03-12345678-000000000000001
    private LocalDateTime billGenerationDate;
    private String account;
    private String paymentCondition;

    //transmitter
    private String companyName;
    private String companyDocument; // NIT
    private String companyAddress;
    private String companyEmail;
    private String companyPhone;    

    //receiver
    private String customerName;
    private String customerDocument; // NIT or DUI
    private String customerAddress;
    private String customerEmail;
    private String customerPhone;  

    //products
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
