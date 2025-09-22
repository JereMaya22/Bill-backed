package com.gbill.getallfinalconsumerbill.service;

import java.util.List;

import shareddtos.billmodule.ShowBillDto;

public interface IFinalConsumerBillService {
    List<ShowBillDto> getAllBill();
    ShowBillDto getBygenerationCode();
    ShowBillDto getBycontrolNumber();
}
