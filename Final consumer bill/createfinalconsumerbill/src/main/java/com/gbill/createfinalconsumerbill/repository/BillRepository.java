package com.gbill.createfinalconsumerbill.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gbill.createfinalconsumerbill.model.FinalConsumerBill;

public interface BillRepository extends JpaRepository<FinalConsumerBill, Long>{

}
