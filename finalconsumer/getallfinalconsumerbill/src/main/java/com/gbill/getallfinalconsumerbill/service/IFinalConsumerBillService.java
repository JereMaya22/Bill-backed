package com.gbill.getallfinalconsumerbill.service;

import java.util.List;

import shareddtos.billmodule.bill.ShowBillDto;

public interface IFinalConsumerBillService {
    List<ShowBillDto> getAllBill(String token);
    ShowBillDto getBygenerationCode(String generationCode, String token);
}
