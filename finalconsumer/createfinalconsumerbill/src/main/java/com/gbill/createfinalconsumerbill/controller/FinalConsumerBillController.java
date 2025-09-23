package com.gbill.createfinalconsumerbill.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.gbill.createfinalconsumerbill.modeldto.CreateFinalConsumerBillDTO;
import com.gbill.createfinalconsumerbill.service.FinalConsumerBillService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/final-consumer")
public class FinalConsumerBillController {

    private final FinalConsumerBillService billService;

    public FinalConsumerBillController(FinalConsumerBillService billService){
        this.billService= billService;

    }

    @PostMapping("/create")
    public ResponseEntity<CreateFinalConsumerBillDTO> createFinalConsumerBill(@Valid @RequestBody CreateFinalConsumerBillDTO createFinalConsumerBillDTO, @RequestHeader("Authorization") String token){
        billService.createFinalConsumerBill(createFinalConsumerBillDTO, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(createFinalConsumerBillDTO);
    }

    

}
