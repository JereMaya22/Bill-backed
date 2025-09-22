package com.gbill.getallfinalconsumerbill.service;

import java.util.List;
import java.util.Optional;

import shareddtos.billmodule.ShowBillDto;

public interface IFinalConsumerBillService {
    List<ShowBillDto> getAllBill();
    Optional<ShowBillDto> getBygenerationCode(String generationCode);
}
