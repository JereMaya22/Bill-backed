package com.gbill.createfinalconsumerbill.modeldto.promortion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPromotionDTO {
    private Long idProducto;
    private Integer cantidad;
    private Double precioUnitario;
    private String name;
}
