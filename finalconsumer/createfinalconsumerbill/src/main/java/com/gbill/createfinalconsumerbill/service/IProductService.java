package com.gbill.createfinalconsumerbill.service;

import java.util.List;
import java.util.Optional;

import shareddtos.billmodule.product.ProductBillDTO;

public interface IProductService {

    Optional<ProductBillDTO> getById(Long id);
    List<ProductBillDTO> getAllIds(List<Long> ids);

}
