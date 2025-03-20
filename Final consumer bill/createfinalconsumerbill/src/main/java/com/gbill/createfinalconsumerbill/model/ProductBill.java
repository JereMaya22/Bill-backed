package com.gbill.createfinalconsumerbill.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ProductBill {

    private Long id;
    private String name;
    private Integer quantity;
    private Double price;

}
