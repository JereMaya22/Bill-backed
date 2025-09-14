package com.gbill.createfinalconsumerbill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gbill.createfinalconsumerbill.model.FinalConsumerBill;
@Repository
public interface BillRepository extends JpaRepository<FinalConsumerBill, Long>{

}
