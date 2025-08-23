package com.gbill.getIdBill.controller;

import com.gbill.getIdBill.modeldto.ShowBill;
import com.gbill.getIdBill.service.ServiceBill;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bill")
public class BillController {
    private final ServiceBill serviceBill;

    public BillController(ServiceBill serviceBill) {
        this.serviceBill = serviceBill;
    }

    @GetMapping("/BillbyId")
    public ResponseEntity<ShowBill> getById(@PathVariable String codeGenerator) {
        return ResponseEntity.ok(serviceBill.getById(codeGenerator).orElse(null));
    }
}
