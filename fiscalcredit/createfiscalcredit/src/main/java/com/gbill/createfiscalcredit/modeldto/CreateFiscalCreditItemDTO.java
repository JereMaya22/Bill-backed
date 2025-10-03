package com.gbill.createfiscalcredit.modeldto;

import lombok.Data;

@Data
public class CreateFiscalCreditItemDTO {
    private Long productId;  // Solo el ID del producto
    private Integer requestedQuantity;  // Solo la cantidad solicitada
    
    // ✨ LOS 3 CAMPOS NUEVOS PARA CRÉDITO FISCAL
    // El frontend debe especificar el tipo de venta para cada producto
    private Double nonTaxableSales;  // Ventas no sujetas
    private Double exemptSales;      // Ventas exentas  
    private Double taxableSales;     // Ventas gravadas
    
    // Nota: name y price se obtendrán del servicio de inventario
}
