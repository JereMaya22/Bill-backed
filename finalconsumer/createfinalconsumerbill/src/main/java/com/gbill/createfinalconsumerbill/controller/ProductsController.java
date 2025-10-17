package com.gbill.createfinalconsumerbill.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gbill.createfinalconsumerbill.clients.ProductsClient;
import com.gbill.createfinalconsumerbill.modeldto.NewProductDTO;

@RestController
@RequestMapping("/api/productos")
public class ProductsController {

	private final ProductsClient productsClient;

	public ProductsController(ProductsClient productsClient) {
		this.productsClient = productsClient;
	}

	@GetMapping
	public ResponseEntity<List<NewProductDTO>> getAllProducts() {
		List<NewProductDTO> products = productsClient.getAllProducts();
		if (products == null || products.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(products);
	}
}


