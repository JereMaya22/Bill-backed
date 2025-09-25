package com.gbill.createfinalconsumerbill.mapper;

import com.gbill.createfinalconsumerbill.model.ProductBill;

import shareddtos.billmodule.product.ProductBillDTO;

public class ProductMapper {
    public static ProductBillDTO toDto (ProductBill productBill){
        return new ProductBillDTO(
            productBill.getId(),
            productBill.getName(),
            productBill.getQuantity(),
            productBill.getPrice()
        );
    }

}
