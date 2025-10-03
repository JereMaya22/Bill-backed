package com.gbill.createfiscalcredit.modeldto;

import lombok.Data;
import java.util.List;

@Data
public class CreateFiscalCreditDTO {
    
    // EMISOR (Empresa)
    private String companyName;
    private String companyDocument;  // NIT
    private String companyAddress;
    private String companyPhone;
    private String companyEmail;
    
    // RECEPTOR (Cliente)
    private String customerName;
    private String customerDocument;  // NIT o DUI
    private String customerAddress;
    private String customerPhone;
    private String customerEmail;
    
    // INFORMACIÓN DE FACTURA
    private String account;  // Cuenta que realizó la factura
    private String paymentCondition;  // "Contado", "Crédito", etc.
    
    // PRODUCTOS
    private List<CreateFiscalCreditItemDTO> products;
}
