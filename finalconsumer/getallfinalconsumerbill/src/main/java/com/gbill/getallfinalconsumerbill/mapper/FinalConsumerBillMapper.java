package com.gbill.getallfinalconsumerbill.mapper;


import java.util.stream.Collectors;

import com.gbill.getallfinalconsumerbill.model.FinalConsumerBill;

import shareddtos.billmodule.ProductBillDTO;
import shareddtos.billmodule.ShowBillDto;

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
                        ProductBillDTO productDto = new ProductBillDTO();
                        productDto.setId(productos.getId());
                        productDto.setName(productos.getName());
                        productDto.setQuantity(productos.getQuantity());
                        productDto.setPrice(productos.getPrice());
                        return productDto;
                    }
                ).collect(Collectors.toList()), // Ahora se pasa la lista de DTOs de productos
                finalConsumerBill.getNonTaxedSales(),
                finalConsumerBill.getExemptSales(),
                finalConsumerBill.getTaxedSales(),
                finalConsumerBill.getIva(),
                finalConsumerBill.getPerceivedIva(),
                finalConsumerBill.getWithheldIva(),
                finalConsumerBill.getTotalWithIva()

            ); 
            
            // Se elimina la lógica incorrecta que modificaba la entidad de entrada y no usaba los productos mapeados.

            return showBillDto;
        }
}
