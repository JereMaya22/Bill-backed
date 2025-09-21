package com.gbill.createfinalconsumerbill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gbill.createfinalconsumerbill.model.ProductBill;

@Repository
public interface ProductRepository extends JpaRepository<ProductBill, Long> {

}
