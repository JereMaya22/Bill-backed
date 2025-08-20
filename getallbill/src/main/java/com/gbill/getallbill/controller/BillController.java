package com.gbill.getallbill.controller;

import com.gbill.getallbill.modeldto.BillAllDTO;
import com.gbill.getallbill.service.BillService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/bill")
public class BillController {
    private final BillService billService;
    public BillController(BillService billService){
        this.billService = billService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<BillAllDTO>> getAllBills(){
        return ResponseEntity.ok(billService.getAllBills());
    }
}
