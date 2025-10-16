package com.gbill.createfinalconsumerbill.modeldto.promortion;

import java.util.List;

import lombok.Data;

@Data
public class ApplyPromotionRequest {
    private String account;
    private List<ProductPromotionDTO> productos;
    private String codigoPromocion;
}
