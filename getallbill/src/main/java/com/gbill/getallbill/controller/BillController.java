package com.gbill.getallbill.controller;

import com.gbill.getallbill.service.BillService;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillController {
    private final BillService billservice;
    public BillController(BillService billService){
        this.billservice = billService;
    }

    public RequestEntity getAllBills(){
        return null;
    }
}
