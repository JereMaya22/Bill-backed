package com.gbill.createfiscalcredit.controller;

import com.gbill.createfiscalcredit.modeldto.CreateFiscalCreditDTO;
import com.gbill.createfiscalcredit.service.FiscalCreditService;
import com.gbill.createfiscalcredit.model.FiscalCredit;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fiscal-credit")
@RequiredArgsConstructor
public class FiscalCreditController {

    private final FiscalCreditService fiscalCreditService;

    /**
     * Endpoint para crear un crédito fiscal
     * 
     * POST /api/fiscal-credit
     * Body: CreateFiscalCreditDTO (JSON)
     * 
     * @param createFiscalCreditDTO DTO con los datos del crédito fiscal
     * @return ResponseEntity con el crédito fiscal creado o error
     */
    @PostMapping
    public ResponseEntity<?> createFiscalCredit(
            @RequestBody CreateFiscalCreditDTO createFiscalCreditDTO) {
        
        try {
            // Token temporal para pruebas - sin validación
            String token = "Bearer test-token";
            FiscalCredit created = fiscalCreditService.createFiscalCredit(token, createFiscalCreditDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al crear crédito fiscal: " + e.getMessage());
        }
    }

    /**
     * Endpoint para obtener un crédito fiscal por ID
     * 
     * GET /api/fiscal-credit/{id}
     * 
     * @param id ID del crédito fiscal
     * @return ResponseEntity con el crédito fiscal o 404 si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getFiscalCreditById(@PathVariable Long id) {
        try {
            Optional<FiscalCredit> fiscalCredit = fiscalCreditService.getFiscalCreditById(id);
            
            if (fiscalCredit.isPresent()) {
                return ResponseEntity.ok(fiscalCredit.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Crédito fiscal no encontrado con ID: " + id);
            }
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener crédito fiscal: " + e.getMessage());
        }
    }

    /**
     * Endpoint para obtener un crédito fiscal por código de generación
     * 
     * GET /api/fiscal-credit/by-generation-code/{generationCode}
     * 
     * @param generationCode Código de generación del crédito fiscal
     * @return ResponseEntity con el crédito fiscal o 404 si no existe
     */
    @GetMapping("/by-generation-code/{generationCode}")
    public ResponseEntity<?> getFiscalCreditByGenerationCode(@PathVariable String generationCode) {
        try {
            Optional<FiscalCredit> fiscalCredit = fiscalCreditService.getFiscalCreditByGenerationCode(generationCode);
            
            if (fiscalCredit.isPresent()) {
                return ResponseEntity.ok(fiscalCredit.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Crédito fiscal no encontrado con código de generación: " + generationCode);
            }
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener crédito fiscal: " + e.getMessage());
        }
    }

    /**
     * Endpoint para obtener un crédito fiscal por número de control
     * 
     * GET /api/fiscal-credit/by-control-number/{controlNumber}
     * 
     * @param controlNumber Número de control del crédito fiscal
     * @return ResponseEntity con el crédito fiscal o 404 si no existe
     */
    @GetMapping("/by-control-number/{controlNumber}")
    public ResponseEntity<?> getFiscalCreditByControlNumber(@PathVariable String controlNumber) {
        try {
            Optional<FiscalCredit> fiscalCredit = fiscalCreditService.getFiscalCreditByControlNumber(controlNumber);
            
            if (fiscalCredit.isPresent()) {
                return ResponseEntity.ok(fiscalCredit.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Crédito fiscal no encontrado con número de control: " + controlNumber);
            }
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener crédito fiscal: " + e.getMessage());
        }
    }

    /**
     * Endpoint para obtener todos los créditos fiscales
     * 
     * GET /api/fiscal-credit
     * 
     * @return ResponseEntity con la lista de todos los créditos fiscales
     */
    @GetMapping
    public ResponseEntity<?> getAllFiscalCredits() {
        try {
            List<FiscalCredit> fiscalCredits = fiscalCreditService.getAllFiscalCredits();
            return ResponseEntity.ok(fiscalCredits);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener créditos fiscales: " + e.getMessage());
        }
    }
}
