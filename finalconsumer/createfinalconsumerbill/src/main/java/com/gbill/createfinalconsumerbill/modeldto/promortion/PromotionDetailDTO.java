package com.gbill.createfinalconsumerbill.modeldto.promortion;

import lombok.Data;

@Data
public class PromotionDetailDTO {
    private String codigo;
    private String nombre;
    private String descripcion;
    private String tipoPromocion;
    private Double descuentoEstimado;
}
