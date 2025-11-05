package com.gbill.getallfinalconsumerbill.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gbill.getallfinalconsumerbill.service.FinalConsumerBillService;

import shareddtos.billmodule.bill.ShowBillDto;



@RestController
@RequestMapping("api/final-consumer")
public class FinalConsumerBillController {

    private final FinalConsumerBillService billService;

    public FinalConsumerBillController(FinalConsumerBillService billService){
        this.billService = billService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ShowBillDto>> getAllBills(@RequestHeader(value = "Authorization", required = false) String tokenHeader,
    @CookieValue(value = "Authorization", required = false) String tokenCookie){

        String token = (tokenCookie != null) ? tokenCookie : tokenHeader;

        List<ShowBillDto> bills = billService.getAllBill(token);
        return ResponseEntity.ok().body(bills);
    }

    @GetMapping("/generation-code/{code}")
    public ResponseEntity<ShowBillDto> getMethodName(@RequestHeader(value = "Authorization", required = false) String tokenHeader,
    @CookieValue(value = "Authorization", required = false)String tokenCookie, @PathVariable String code) {
        String token = (tokenCookie != null) ? tokenCookie : tokenHeader;

        ShowBillDto showBillDto = billService.getBygenerationCode(code,token);
        return ResponseEntity.ok().body(showBillDto);
    }

}
