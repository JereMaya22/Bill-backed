package com.gbill.createfinalconsumerbill.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.gbill.createfinalconsumerbill.modeldto.NewProductDTO;

@FeignClient(name = "productsClient", url = "${app.client.products}")
public interface ProductsClient {

	@GetMapping("/api/productos")
	List<NewProductDTO> getAllProducts();
}


