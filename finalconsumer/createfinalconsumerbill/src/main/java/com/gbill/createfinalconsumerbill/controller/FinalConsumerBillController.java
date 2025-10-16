package com.gbill.createfinalconsumerbill.controller;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


import com.gbill.createfinalconsumerbill.modeldto.CreateFinalConsumerBillDTO;
import com.gbill.createfinalconsumerbill.service.FinalConsumerBillService;

import jakarta.validation.Valid;
import shareddtos.billmodule.bill.ShowBillDto;

@RestController
@RequestMapping("/api/final-consumer")
public class FinalConsumerBillController {

    private final FinalConsumerBillService billService;

    public FinalConsumerBillController(FinalConsumerBillService billService){
        this.billService= billService;

    }

    @PostMapping("/create")
    public ResponseEntity<ShowBillDto> createFinalConsumerBill(@Valid @RequestBody CreateFinalConsumerBillDTO createFinalConsumerBillDTO, @RequestHeader("Authorization") String token){
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
