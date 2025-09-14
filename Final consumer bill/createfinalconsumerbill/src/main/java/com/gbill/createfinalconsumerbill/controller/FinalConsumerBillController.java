package com.gbill.createfinalconsumerbill.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gbill.createfinalconsumerbill.modeldto.CreateFinalConsumerBillDTO;
import com.gbill.createfinalconsumerbill.service.FinalConsumerBillService;

@RestController
@RequestMapping("/api/final-consumer")
public class FinalConsumerBillController {

    private final FinalConsumerBillService billService;

    public FinalConsumerBillController(FinalConsumerBillService billService){
        this.billService= billService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createFinalConsumerBill(@RequestBody CreateFinalConsumerBillDTO createFinalConsumerBillDTO){
        billService.createFinalConsumerBill(createFinalConsumerBillDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("bill created");
    }

}
