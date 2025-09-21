package com.gbill.getallfinalconsumerbill.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gbill.getallfinalconsumerbill.service.FinalConsumerBillService;

import shareddtos.billmodule.ShowBillDto;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api/final-consumer")
public class FinalConsumerBillController {

    private final FinalConsumerBillService billService;

    public FinalConsumerBillController(FinalConsumerBillService billService){
        this.billService = billService;
    }

    @GetMapping("/bills")
    public ResponseEntity<List<ShowBillDto>> getAllBills(){
        
        List<ShowBillDto> bills = billService.getAllBill();

        return ResponseEntity.ok().body(bills);
    }
    
    

}
