package com.gbill.createfinalconsumerbill.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gbill.createfinalconsumerbill.modeldto.CreateFinalConsumerBillDTO;
import com.gbill.createfinalconsumerbill.service.BillReturnService;

import jakarta.validation.Valid;
import shareddtos.billmodule.bill.ShowBillDto;

/**
 * Controlador dedicado al flujo de devoluciones.
 * Reglas del dominio implementadas a través del service:
 * - No se devuelve dinero, se emite una nueva factura con nuevos productos.
 * - La factura original queda marcada como devuelta (isReversed=true) y con returnBillCode.
 * - La factura nueva queda enlazada a la original mediante originBillCode.
 * Responsabilidad del controller: orquestar entrada/salida HTTP; la lógica vive en el service.
 */
@RestController
@RequestMapping("/api/final-consumer/return")
public class BillReturnController {

    private final BillReturnService billReturnService;

    public BillReturnController(BillReturnService billReturnService) {
        this.billReturnService = billReturnService;
    }

    /**
     * Crea una factura de devolución basada en una factura original, con nuevos productos.
     * Parámetros:
     * - originalGenerationCode: código de la factura original que se reemplaza.
     * - createFinalConsumerBillDTO: datos de la nueva factura a emitir.
     * - Token: tomado desde header o cookie (si aplica en filtros de seguridad previos).
     * Respuestas:
     * - 201 Created con la factura creada.
     * - 400/404 ante reglas de negocio incumplidas o entidad inexistente.
     */
    @PostMapping("/{originalGenerationCode}")
    public ResponseEntity<ShowBillDto> createReturnBill(
            @PathVariable String originalGenerationCode,
            @Valid @RequestBody CreateFinalConsumerBillDTO createFinalConsumerBillDTO,
            @RequestHeader(value = "Authorization", required = false) String tokenHeader,
            @CookieValue(value = "Authorization", required = false) String tokenCookie) {

        String token = (tokenCookie != null) ? tokenCookie : tokenHeader;

        ShowBillDto returnBill = billReturnService.createReturnBill(
                createFinalConsumerBillDTO,
                originalGenerationCode,
                token);

        return ResponseEntity.status(HttpStatus.CREATED).body(returnBill);
    }
}
