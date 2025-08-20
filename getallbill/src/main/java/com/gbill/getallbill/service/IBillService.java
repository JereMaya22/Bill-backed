package com.gbill.getallbill.service;

import com.gbill.getallbill.modeldto.BillAllDTO;

import java.util.List;

public interface IBillService {
    List<BillAllDTO> getAllBills();
}
