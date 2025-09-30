package com.ginventory.inventory.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ginventory.inventory.dto.CreateProductDTO;
import com.ginventory.inventory.dto.EditProduct;
import com.ginventory.inventory.dto.ShowProductDTO;
import com.ginventory.inventory.mapper.ProductMapper;
import com.ginventory.inventory.repository.ProductRepository;
import com.ginventory.inventory.model.Product;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService implements IProductService{

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    

    @Override
    public List<ShowProductDTO> getAllProducts() {  
        return productRepository.findAll().stream().map(ProductMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ShowProductDTO> getProductsByIds(List<Long> ids) {
        List<Product> products = productRepository.findAllById(ids);
        return products.stream().map(ProductMapper::toDto).collect(Collectors.toList());
    }
    

    @Override
    public List<ShowProductDTO> getByCategory(String category) {
        List<Product> products = productRepository.findByCategory(category);
        return products.stream().map(ProductMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ShowProductDTO> getByName(String name) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        return products.stream().map(ProductMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ShowProductDTO getById(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        return ProductMapper.toDto(product);
    }

    @Override
    public CreateProductDTO createProduct(CreateProductDTO createProductDTO) {
        Product product = new Product();
        product.setName(createProductDTO.getName());
        product.setStock(createProductDTO.getStock());
        product.setPrice(createProductDTO.getPrice());
        product.setCategory(createProductDTO.getCategory());

        Product savedProduct = productRepository.save(product);
        return new CreateProductDTO(
            savedProduct.getName(),
            savedProduct.getStock(),
            savedProduct.getPrice(),
            savedProduct.getCategory()
        );
    }

    @Override
    public EditProduct editProduct(EditProduct editProduct) {
        Product product = productRepository.findById(editProduct.getId())
            .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + editProduct.getId()));

        product.setName(editProduct.getName());
        product.setStock(editProduct.getStock());
        product.setPrice(editProduct.getPrice());
        product.setCategory(editProduct.getCategory());

        Product updatedProduct = productRepository.save(product);

        return new EditProduct(
            updatedProduct.getId(),
            updatedProduct.getName(),
            updatedProduct.getStock(),
            updatedProduct.getPrice(),
            updatedProduct.getCategory()
        );
    }


    @Override
    public ShowProductDTO decreaseStock(Long id, int quantity) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("Insufficient stock for product with id: " + id);
        }

        product.setStock(product.getStock() - quantity);
        Product updatedProduct = productRepository.save(product);
        return ProductMapper.toDto(updatedProduct);
    }


    @Override
    public ShowProductDTO increaseStock(Long id, int quantity) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

        product.setStock(product.getStock() + quantity);
        Product updatedProduct = productRepository.save(product);
        return ProductMapper.toDto(updatedProduct);
    }

}
