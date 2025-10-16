package com.gbill.createfinalconsumerbill.modeldto.promortion;

import java.util.List;

import lombok.Data;

@Data
public class PromotionAvailableResponse {
    private String account;
    private Double totalCompra;
    private List<PromotionDetailDTO> promocionesDisponibles;
}
