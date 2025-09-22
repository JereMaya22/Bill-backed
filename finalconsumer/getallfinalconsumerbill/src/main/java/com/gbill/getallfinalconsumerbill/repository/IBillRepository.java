package com.gbill.getallfinalconsumerbill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gbill.getallfinalconsumerbill.model.FinalConsumerBill;
import java.util.Optional;


@Repository
public interface IBillRepository extends JpaRepository<FinalConsumerBill,Long>{
    Optional<FinalConsumerBill> findByGenerationCode(String generationCode);
}
