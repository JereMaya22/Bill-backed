package com.gbill.createfiscalcredit.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class FiscalCreditItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long productId;
    private String name; // Descripción del producto
    private Integer requestedQuantity; // Cantidad
    private Double price; // Precio unitario
    private Double subTotal;
    
    // ✨ CAMPOS ESPECÍFICOS PARA CRÉDITO FISCAL (por producto)
    private Double nonTaxableSales;  // Ventas no sujetas
    private Double exemptSales;      // Ventas exentas  
    private Double taxableSales;     // Ventas gravadas

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fiscal_credit_id")
    @JsonIgnore
    private FiscalCredit fiscalCredit;
}
