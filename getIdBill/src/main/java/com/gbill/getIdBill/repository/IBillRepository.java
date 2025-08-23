package com.gbill.getIdBill.repository;

import com.gbill.getIdBill.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IBillRepository extends JpaRepository<Bill, Long> {
    Optional<Bill> findByCodeGenerator(String codeGenerator);
}
