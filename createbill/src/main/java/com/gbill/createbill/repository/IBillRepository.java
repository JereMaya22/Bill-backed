package com.gbill.createbill.repository;

import com.gbill.createbill.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBillRepository extends JpaRepository<Bill,Long> {
}
