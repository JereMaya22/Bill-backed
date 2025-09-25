package com.gbill.createfinalconsumerbill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gbill.createfinalconsumerbill.model.BillItem;

@Repository
public interface BillItemRepository extends JpaRepository<BillItem,Long>{


}
