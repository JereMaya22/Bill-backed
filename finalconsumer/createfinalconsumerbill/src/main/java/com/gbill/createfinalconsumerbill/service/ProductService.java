package com.gbill.createfinalconsumerbill.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.gbill.createfinalconsumerbill.mapper.ProductMapper;
import com.gbill.createfinalconsumerbill.model.ProductBill;
import com.gbill.createfinalconsumerbill.repository.ProductRepository;

import shareddtos.billmodule.product.ProductBillDTO;

@Service
public class ProductService implements IProductService{

    private final ProductRepository productRepository;

    public ProductService (ProductRepository productRepository){
        this.productRepository = productRepository;

    }

    @Override
    public Optional<ProductBillDTO> getById(Long id) {
        return productRepository.findById(id).map(ProductMapper::toDto);
    }

    @Override
    public List<ProductBillDTO> getAllIds(List<Long> ids) {

        List<ProductBill> products = productRepository.findAllById(ids);

        return products.stream().map(p -> {
            ProductBillDTO dto = new ProductBillDTO();
            dto.setId(p.getId());
            dto.setName(p.getName());
            dto.setQuantity(p.getQuantity());
            dto.setPrice(p.getPrice());
            return dto;
        }).collect(Collectors.toList());
    }

}
