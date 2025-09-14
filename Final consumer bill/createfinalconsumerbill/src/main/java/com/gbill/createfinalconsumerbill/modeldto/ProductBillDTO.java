package com.gbill.createfinalconsumerbill.modeldto;

import com.gbill.createfinalconsumerbill.model.FinalConsumerBill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProductBillDTO {
    private Long id;
    private String name;
    private Integer quantity;
    private Double price;
}
