package com.gbill.createfinalconsumerbill.modeldto.promortion;

import lombok.Data;

@Data
public class ProductWithDiscountDTO {
    private Long id;
    private String name;
    private Integer quantity;
    private Double precioOriginal;
    private Double precioConDescuento;
    private Double descuentoAplicado;
}
