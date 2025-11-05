package com.gbill.createfinalconsumerbill.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.gbill.createfinalconsumerbill.mapper.FinalConsumerBillMapper;
import com.gbill.createfinalconsumerbill.model.FinalConsumerBill;
import com.gbill.createfinalconsumerbill.modeldto.CreateFinalConsumerBillDTO;
import com.gbill.createfinalconsumerbill.repository.BillRepository;

import shareddtos.billmodule.bill.ShowBillDto;

/**
 * Servicio encargado del flujo de devoluciones.
 * Reglas de negocio:
 * - No se devuelve dinero: se emite una nueva factura con nuevos productos.
 * - La factura original queda marcada como devuelta (isReversed=true) y apunta a la nueva por returnBillCode.
 * - La factura nueva queda enlazada a la original mediante originBillCode.
 * - No se puede devolver una factura ya devuelta ni una que ya sea devolución.
 */
@Service
public class BillReturnService {

    private final FinalConsumerBillService finalConsumerBillService;
    private final BillRepository billRepository;

    public BillReturnService(
            FinalConsumerBillService finalConsumerBillService,
            BillRepository billRepository) {
        this.finalConsumerBillService = finalConsumerBillService;
        this.billRepository = billRepository;
    }

    /**
     * Crea una nueva factura de devolución y actualiza la factura original
     * 
     * @param createFinalConsumerBillDTO DTO con los datos de la nueva factura
     * @param originalGenerationCode Código de generación de la factura original a devolver
     * @param token Token de autenticación
     * @return ShowBillDto de la nueva factura creada
     */
    public ShowBillDto createReturnBill(
            CreateFinalConsumerBillDTO createFinalConsumerBillDTO,
            String originalGenerationCode,
            String token) {

        // Validar que la factura original existe
        FinalConsumerBill originalBill = billRepository.findBygenerationCode(originalGenerationCode)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Factura original con código " + originalGenerationCode + " no encontrada"));

        // Regla: no devolver si ya fue devuelta
        if (Boolean.TRUE.equals(originalBill.getIsReversed())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La factura original ya ha sido devuelta. Código de devolución: " + originalBill.getReturnBillCode());
        }

        // Regla: no devolver una factura que ya es devolución
        if (originalBill.getOriginBillCode() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No se puede devolver una factura de devolución. Esta factura es una devolución de: "
                            + originalBill.getOriginBillCode());
        }

        // Crear la nueva factura usando el flujo normal
        ShowBillDto newBill = finalConsumerBillService.createFinalConsumerBill(createFinalConsumerBillDTO, token);

        // Recuperar entidad creada para setear vínculos de devolución
        FinalConsumerBill createdBill = billRepository.findBygenerationCode(newBill.getGenerationCode())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Error al recuperar la factura recién creada"));

        // === CONFIGURAR CAMPOS DE DEVOLUCIÓN ===
        // Nueva factura (devolución): isReversed=false; originBillCode=original; returnBillCode=null
        createdBill.setIsReversed(true);
        createdBill.setOriginBillCode(originalGenerationCode);
        createdBill.setReturnBillCode(null);

        // Factura original: isReversed=true; returnBillCode=nueva; originBillCode=null (permanece)
        originalBill.setIsReversed(true);
        originalBill.setReturnBillCode(newBill.getGenerationCode());

        // Persistir ambas
        billRepository.save(createdBill);
        billRepository.save(originalBill);

        // Retornar la nueva factura ya con vínculos
        return FinalConsumerBillMapper.toShowBillDto(createdBill);
    }
}
