package com.gbill.createfinalconsumerbill.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gbill.createfinalconsumerbill.model.FinalConsumerBill;
@Repository
public interface BillRepository extends JpaRepository<FinalConsumerBill, Long>{
    Optional<FinalConsumerBill> findBygenerationCode(String generationCode);
}
