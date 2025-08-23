package com.gbill.createbill.controller;

import com.gbill.createbill.modeldto.CreateBillDto;
import com.gbill.createbill.servicce.BillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/bill")
@CrossOrigin(origins = "http://localhost:4200")
public class BillController {
    private final BillService billService;

    public BillController(BillService billService){
        this.billService = billService;
    }

    @PostMapping("/createbill")
    public ResponseEntity<Map<String, String>> createBill(@RequestBody CreateBillDto createBillDtoq){

        billService.createBill(createBillDtoq);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Bill created");
        return ResponseEntity.ok(response);
    }
}
