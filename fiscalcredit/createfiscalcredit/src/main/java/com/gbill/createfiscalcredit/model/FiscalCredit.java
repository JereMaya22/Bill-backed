package com.gbill.createfiscalcredit.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class FiscalCredit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 36, nullable = false, unique = true)
    private String generationCode; // Código de generación - 36 caracteres
    
    @Column(length = 31, nullable = false, unique = true)
    private String controlNumber; // Número de control - DTE-03-12345678-000000000000001
    
    // EMISOR (Empresa)
    private String companyName;
    private String companyDocument; // NIT de la empresa
    private String companyAddress;
    private String companyPhone;
    private String companyEmail;
    
    // RECEPTOR (Cliente)
    private String customerName;
    private String customerDocument; // NIT o DUI del cliente
    private String customerAddress;
    private String customerPhone;
    private String customerEmail;
    
    // INFORMACIÓN DE FACTURA
    private LocalDateTime billGenerationDate; // Fecha de generación
    private String account; // Cuenta que realizó la factura
    private String paymentCondition; // Condición de pago
    
    // TOTALES DE VENTAS
    private Double nonTaxedSales; // Ventas no sujetas
    private Double exemptSales; // Ventas exentas
    private Double taxedSales; // Ventas gravadas
    
    // TOTALES CON IVA
    private Double subtotal;
    private Double iva; // 13% de IVA
    private Double perceivedIva; // IVA percibido
    private Double withheldIva; // IVA retenido
    private Double totalWithIva; // Total con la suma del IVA
    
    @OneToMany(mappedBy = "fiscalCredit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FiscalCreditItem> products;
}
