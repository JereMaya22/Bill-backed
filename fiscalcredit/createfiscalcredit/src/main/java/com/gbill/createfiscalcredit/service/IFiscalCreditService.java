package com.gbill.createfiscalcredit.service;

import com.gbill.createfiscalcredit.model.FiscalCredit;
import com.gbill.createfiscalcredit.modeldto.CreateFiscalCreditDTO;
import java.util.List;
import java.util.Optional;

public interface IFiscalCreditService {
    
    /**
     * Crea un nuevo crédito fiscal
     * @param token Token de autenticación
     * @param createFiscalCreditDTO DTO con los datos del crédito fiscal
     * @return FiscalCredit creado
     */
    FiscalCredit createFiscalCredit(String token, CreateFiscalCreditDTO createFiscalCreditDTO);
    
    /**
     * Obtiene un crédito fiscal por ID
     * @param id ID del crédito fiscal
     * @return FiscalCredit encontrado
     */
    Optional<FiscalCredit> getFiscalCreditById(Long id);
    
    /**
     * Obtiene un crédito fiscal por código de generación
     * @param generationCode Código de generación
     * @return FiscalCredit encontrado
     */
    Optional<FiscalCredit> getFiscalCreditByGenerationCode(String generationCode);
    
    /**
     * Obtiene un crédito fiscal por número de control
     * @param controlNumber Número de control
     * @return FiscalCredit encontrado
     */
    Optional<FiscalCredit> getFiscalCreditByControlNumber(String controlNumber);
    
    /**
     * Obtiene todos los créditos fiscales
     * @return Lista de todos los créditos fiscales
     */
    List<FiscalCredit> getAllFiscalCredits();
}
