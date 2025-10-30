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

@RestController
@RequestMapping("/api/final-consumer")
public class FinalConsumerBillController {

    private final FinalConsumerBillService billService;

    public FinalConsumerBillController(FinalConsumerBillService billService) {
        this.billService = billService;
    }

    @PostMapping("/create")
    public ResponseEntity<ShowBillDto> createFinalConsumerBill(
            @Valid @RequestBody CreateFinalConsumerBillDTO createFinalConsumerBillDTO,
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @CookieValue(value = "jwt", required = false) String cookieToken // 👈 nombre de tu cookie
    ) {
        //Determinar de dónde viene el token
        String token = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        } else if (cookieToken != null && !cookieToken.isEmpty()) {
            token = cookieToken;
        }

        // Validar que exista
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }

        //Llamar al service como siempre
        ShowBillDto showBillDto = billService.createFinalConsumerBill(createFinalConsumerBillDTO, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(showBillDto);
    }

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
