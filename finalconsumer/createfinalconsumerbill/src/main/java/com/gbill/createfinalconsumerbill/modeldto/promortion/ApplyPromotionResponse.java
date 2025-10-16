package com.gbill.createfinalconsumerbill.modeldto.promortion;

import java.util.List;

import lombok.Data;

@Data
public class ApplyPromotionResponse {
    private String account;
    private Boolean promocionAplicada;
    private String codigoPromocionUsada;
    private String nombrePromocion;
    private Double montoDescuento;
    private Double porcentajeDescuento;
    private String mensaje;
    private List<ProductWithDiscountDTO> productosConDescuento;
}
