package com.gbill.getallfinalconsumerbill.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gbill.getallfinalconsumerbill.service.FinalConsumerBillService;

import shareddtos.billmodule.ShowBillDto;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("api/final-consumer")
public class FinalConsumerBillController {

    private final FinalConsumerBillService billService;

    public FinalConsumerBillController(FinalConsumerBillService billService){
        this.billService = billService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ShowBillDto>> getAllBills(){
        
        List<ShowBillDto> bills = billService.getAllBill();

        return ResponseEntity.ok().body(bills);
    }

    @GetMapping("/generation-code/{code}")
    public ResponseEntity<Optional<ShowBillDto>> getMethodName(@PathVariable String code) {

        Optional<ShowBillDto> showBillDto = billService.getBygenerationCode(code);

        return ResponseEntity.ok().body(showBillDto);
    }
    
    
    

}
