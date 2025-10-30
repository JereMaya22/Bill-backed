package com.gbill.createfinalconsumerbill.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gbill.createfinalconsumerbill.modeldto.NewProductDTO;

@FeignClient(name = "inventory-service", url = "${app.client.inventory}")
public interface ProductsClient {

    @GetMapping("/api/productos/{id}")
    public NewProductDTO getById(@PathVariable("id") Long id);

    @PutMapping("/api/productos/decrease-stock/{id}")
    public NewProductDTO decreaseStock(@PathVariable("id") Long id, @RequestParam("quantity") Integer quantity);
    
	@GetMapping("/api/productos")
	List<NewProductDTO> getAllProducts();
}

