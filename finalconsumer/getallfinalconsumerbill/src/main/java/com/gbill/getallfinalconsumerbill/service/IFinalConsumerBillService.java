package com.gbill.getallfinalconsumerbill.service;

import java.util.List;

import shareddtos.billmodule.bill.ShowBillDto;

public interface IFinalConsumerBillService {
    List<ShowBillDto> getAllBill();
    ShowBillDto getBygenerationCode(String generationCode);
}
