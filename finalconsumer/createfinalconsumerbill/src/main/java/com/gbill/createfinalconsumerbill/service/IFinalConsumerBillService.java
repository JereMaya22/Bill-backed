package com.gbill.createfinalconsumerbill.service;

import com.gbill.createfinalconsumerbill.modeldto.CreateFinalConsumerBillDTO;

public interface IFinalConsumerBillService {
    
    CreateFinalConsumerBillDTO createFinalConsumerBill(CreateFinalConsumerBillDTO bill, String token);

}
