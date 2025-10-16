package com.gbill.createfinalconsumerbill.modeldto.promortion;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionAvailableRequest {
    private String account;
    private List<ProductPromotionDTO> productos;
}
