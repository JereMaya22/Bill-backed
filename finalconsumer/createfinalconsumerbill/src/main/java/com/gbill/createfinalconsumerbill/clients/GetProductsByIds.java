package com.gbill.createfinalconsumerbill.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import shareddtos.billmodule.product.ProductBillDTO;

@FeignClient(name = "inventory-service", url = "${app.client.inventory}")
public interface GetProductsByIds {

    @GetMapping("api/inventory/products/by-id/{id}")
    public ProductBillDTO getById(
        @RequestHeader(value = "Authorization", required = true) String token,
        @PathVariable("id") Long id
    );
}
