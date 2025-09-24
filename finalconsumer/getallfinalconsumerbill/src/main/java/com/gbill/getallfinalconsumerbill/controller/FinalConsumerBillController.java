package com.gbill.getallfinalconsumerbill.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gbill.getallfinalconsumerbill.service.FinalConsumerBillService;

import shareddtos.billmodule.ShowBillDto;


@RestController
@RequestMapping("api/final-consumer")
public class FinalConsumerBillController {

    private final FinalConsumerBillService billService;

    public FinalConsumerBillController(FinalConsumerBillService billService){
        this.billService = billService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ShowBillDto>> getAllBills(@RequestHeader("Authorization") String token){
        billService.validation(token);
        List<ShowBillDto> bills = billService.getAllBill();
        return ResponseEntity.ok().body(bills);
    }

    @GetMapping("/generation-code/{code}")
    public ResponseEntity<ShowBillDto> getMethodName(@RequestHeader("Authorization") String token, @PathVariable String code) {
        billService.validation(token);
        ShowBillDto showBillDto = billService.getBygenerationCode(code);
        return ResponseEntity.ok().body(showBillDto);
    }

}
