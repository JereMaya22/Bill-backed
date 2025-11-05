package com.gbill.createfinalconsumerbill.controller;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gbill.createfinalconsumerbill.modeldto.CreateFinalConsumerBillDTO;
import com.gbill.createfinalconsumerbill.service.FinalConsumerBillService;

import jakarta.validation.Valid;
import shareddtos.billmodule.bill.ShowBillDto;

/**
 * REST controller para la gestión de facturas a consumidor final.
 * Responsabilidades principales:
 * - Exponer endpoint para creación de factura con validaciones en service.
 * - Exponer endpoint para descarga de PDF previamente generado.
 * Consideraciones:
 * - El token puede venir en header o cookie; se prioriza cookie si está presente.
 * - La transformación de DTOs y la lógica de negocio no viven aquí; se delega al service.
 */
@RestController
@RequestMapping("/api/final-consumer")
public class FinalConsumerBillController {

    private final FinalConsumerBillService billService;

    public FinalConsumerBillController(FinalConsumerBillService billService) {
        this.billService = billService;
    }

    /**
     * Crea una factura a consumidor final.
     * Notas de implementación:
     * - Usa @Valid para validar constraints declarados en el DTO.
     * - El token se toma de cookie si existe, en su defecto de header.
     * - La respuesta incluye los datos calculados por el service (totales, promos, etc.).
     */
    @PostMapping("/create")
    public ResponseEntity<ShowBillDto> createFinalConsumerBill(
            @Valid @RequestBody CreateFinalConsumerBillDTO createFinalConsumerBillDTO,
            @RequestHeader(value = "Authorization", required = false) String tokenHeader,
            @CookieValue(value = "Authorization", required = false) String tokenCookie
    ) {

        String token = (tokenCookie != null) ? tokenCookie : tokenHeader;

        //Llamar al service como siempre
        ShowBillDto showBillDto = billService.createFinalConsumerBill(createFinalConsumerBillDTO, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(showBillDto);
    }

    /**
     * Descarga el PDF de una factura previamente generada.
     * Comportamiento:
     * - Si existe el archivo, lo devuelve con cabeceras para descarga.
     * - Si no existe, retorna 404 sin cuerpo.
     */
    @GetMapping("/{generationCode}/pdf")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable String generationCode) {
        return billService.getPdfByGenerationCode(generationCode)
            .map(bytes -> {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDisposition(ContentDisposition.attachment()
                    .filename("factura_" + generationCode + ".pdf")
                    .build());
                return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
            }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).<byte[]>build());
    }
}
