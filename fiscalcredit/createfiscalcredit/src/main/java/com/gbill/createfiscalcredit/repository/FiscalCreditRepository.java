package com.gbill.createfiscalcredit.repository;

import com.gbill.createfiscalcredit.model.FiscalCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FiscalCreditRepository extends JpaRepository<FiscalCredit, Long> {
    
    // Buscar por código de generación
    Optional<FiscalCredit> findByGenerationCode(String generationCode);
    
    // Buscar por número de control
    Optional<FiscalCredit> findByControlNumber(String controlNumber);
    
    // Verificar si existe por código de generación
    boolean existsByGenerationCode(String generationCode);
    
    // Verificar si existe por número de control
    boolean existsByControlNumber(String controlNumber);
}
