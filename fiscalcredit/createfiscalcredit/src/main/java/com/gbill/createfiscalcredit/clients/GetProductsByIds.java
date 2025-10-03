package com.gbill.createfiscalcredit.clients;

import com.gbill.createfiscalcredit.modeldto.ShowProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "inventory-service", url = "${app.client.inventory}")
public interface GetProductsByIds {

    @GetMapping("api/inventory/products/by-id/{id}")
    ShowProductDTO getById(@PathVariable("id") Long id);

    @GetMapping("api/inventory/products/by-ids")
    List<ShowProductDTO> getByIds(@RequestParam List<Long> ids);
}
