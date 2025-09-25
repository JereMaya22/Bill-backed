package com.gbill.getallfinalconsumerbill.mapper;


import java.util.stream.Collectors;

import com.gbill.getallfinalconsumerbill.model.FinalConsumerBill;

import shareddtos.billmodule.BillItem.BillItemDTO;
import shareddtos.billmodule.bill.ShowBillDto;

public class FinalConsumerBillMapper {

        public static ShowBillDto toDto(FinalConsumerBill finalConsumerBill){

            ShowBillDto showBillDto = new ShowBillDto(
                finalConsumerBill.getGenerationCode(),
                finalConsumerBill.getControlNumber(),
                finalConsumerBill.getBillGenerationDate(),
                finalConsumerBill.getAccount(),
                finalConsumerBill.getPaymentCondition(),
                finalConsumerBill.getCompanyName(),
                finalConsumerBill.getCompanyDocument(),
                finalConsumerBill.getCompanyAddress(),
                finalConsumerBill.getCompanyEmail(),
                finalConsumerBill.getCompanyPhone(),
                finalConsumerBill.getCustomerName(),
                finalConsumerBill.getCustomerDocument(),
                finalConsumerBill.getCustomerAddress(),
                finalConsumerBill.getCustomerEmail(),
                finalConsumerBill.getCustomerPhone(),
                finalConsumerBill.getProducts().stream().map(
                    productos -> {
                        BillItemDTO productDto = new BillItemDTO();
                        productDto.setId(productos.getId());
                        productDto.setProductId(productos.getProductId());
                        productDto.setName(productos.getName());
                        productDto.setRequestedQuantity(productos.getRequestedQuantity());
                        productDto.setPrice(productos.getPrice());
                        productDto.setSubTotal(productos.getSubTotal());
                        return productDto;
                    }
                ).collect(Collectors.toList()),
                finalConsumerBill.getNonTaxedSales(),
                finalConsumerBill.getExemptSales(),
                finalConsumerBill.getTaxedSales(),
                finalConsumerBill.getIva(),
                finalConsumerBill.getPerceivedIva(),
                finalConsumerBill.getWithheldIva(),
                finalConsumerBill.getTotalWithIva()

            ); 
            
            return showBillDto;
        }
}
