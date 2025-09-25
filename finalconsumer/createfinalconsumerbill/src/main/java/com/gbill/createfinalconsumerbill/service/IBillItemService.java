package com.gbill.createfinalconsumerbill.service;

import java.util.List;
import java.util.Optional;

import shareddtos.billmodule.BillItem.BillItemDTO;
import shareddtos.billmodule.BillItem.CreateBillItemDTO;



public interface IBillItemService {
    List<BillItemDTO> getAllitem();
    Optional<BillItemDTO> getByid(Long id);
    CreateBillItemDTO saveItem(CreateBillItemDTO createBillItemDTO);
    List<CreateBillItemDTO> saveAllItems(List<CreateBillItemDTO> createBillItemDTOS);
}
