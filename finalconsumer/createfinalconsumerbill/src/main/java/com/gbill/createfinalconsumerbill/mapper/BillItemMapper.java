package com.gbill.createfinalconsumerbill.mapper;

import com.gbill.createfinalconsumerbill.model.BillItem;

import shareddtos.billmodule.BillItem.BillItemDTO;
import shareddtos.billmodule.BillItem.CreateBillItemDTO;

public class BillItemMapper {

    public static BillItemDTO toDto(BillItem item){

        return new BillItemDTO(
            item.getId(),
            item.getProductId(),
            item.getName(),
            item.getRequestedQuantity(),
            item.getPrice(),
            item.getSubTotal()
        );
    }

    public static BillItem toEntity(BillItemDTO itemDTO){
        return new BillItem(
            itemDTO.getId(),
            itemDTO.getProductId(),
            itemDTO.getName(),
            itemDTO.getRequestedQuantity(),
            itemDTO.getPrice(),
            itemDTO.getSubTotal(),
            null
        );
    }

    public static BillItem toEntity(CreateBillItemDTO itemDTO){
        return new BillItem(
            null,
            itemDTO.getProductId(),
            itemDTO.getName(),
            itemDTO.getRequestedQuantity(),
            itemDTO.getPrice(),
            itemDTO.getSubTotal(),
            null
        );
    }

    public static CreateBillItemDTO toCreateDto(BillItem billItem){
        return new CreateBillItemDTO(
            billItem.getProductId(),
            billItem.getName(),
            billItem.getRequestedQuantity(),
            billItem.getPrice(),
            billItem.getSubTotal()
        );
    }
}
