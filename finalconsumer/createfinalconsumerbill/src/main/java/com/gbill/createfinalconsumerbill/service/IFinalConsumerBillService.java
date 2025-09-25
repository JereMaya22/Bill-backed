package com.gbill.createfinalconsumerbill.service;

import com.gbill.createfinalconsumerbill.modeldto.CreateFinalConsumerBillDTO;

import shareddtos.billmodule.bill.ShowBillDto;


public interface IFinalConsumerBillService {
    
    ShowBillDto createFinalConsumerBill(CreateFinalConsumerBillDTO bill, String token);

}
