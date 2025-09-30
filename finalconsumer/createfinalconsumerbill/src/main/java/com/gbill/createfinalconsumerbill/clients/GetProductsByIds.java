package com.gbill.createfinalconsumerbill.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import shareddtos.inventorymodule.ShowProductDTO;

@FeignClient(name = "inventory-service", url = "${app.client.inventory}")
public interface GetProductsByIds {

    @GetMapping("api/inventory/products/by-id/")
    public ShowProductDTO  getById(
        @PathVariable("id") Long id
    );

    @GetMapping("api/inventory/products/by-ids")
    public List<ShowProductDTO> getByIds(
        @RequestParam List<Long> ids
    );
}
