package com.gbill.createfinalconsumerbill.modeldto.product;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProductBilllDTO {
    private Long id;
    private String name;
    private Integer Quantity;
    private Double price;
}
