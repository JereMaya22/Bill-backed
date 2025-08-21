package com.gbill.createbill.controller;

import com.gbill.createbill.modeldto.CreateBillDto;
import com.gbill.createbill.servicce.BillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bill")
public class BillController {
    private final BillService billService;

    public BillController(BillService billService){
        this.billService = billService;
    }

    @PostMapping("/createbill")
    public ResponseEntity<String> createBill(@RequestBody CreateBillDto createBillDtoq){

        billService.createBill(createBillDtoq);
        return ResponseEntity.ok("Bill created");
    }
}
