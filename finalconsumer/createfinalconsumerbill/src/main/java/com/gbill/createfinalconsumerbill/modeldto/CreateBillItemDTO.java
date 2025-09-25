package com.gbill.createfinalconsumerbill.modeldto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBillItemDTO {
    private Long productId;
    private String name;
    private int requestedQuantity;
    private Double price;
    private Double subTotal;

}
