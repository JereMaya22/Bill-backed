package com.gbill.createfinalconsumerbill.service;

import com.gbill.createfinalconsumerbill.modeldto.CreateFinalConsumerBillDTO;
import com.gbill.createfinalconsumerbill.repository.BillRepository;

public class FinalConsumerBillService implements IFinalConsumerBillService{

    private final BillRepository billRepository;

    public FinalConsumerBillService(BillRepository billRepository){
        this.billRepository = billRepository;
    }

    @Override
    public void createFinalConsumerBill(CreateFinalConsumerBillDTO Bill) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createFinalConsumerBill'");
    }

}
