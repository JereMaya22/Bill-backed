package com.gbill.createfinalconsumerbill.mapper;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import com.gbill.createfinalconsumerbill.model.FinalConsumerBill;
import com.gbill.createfinalconsumerbill.modeldto.CreateFinalConsumerBillDTO;
import com.gbill.createfinalconsumerbill.modeldto.ProductBillDTO;

public class FinalConsumerBillMapper {
    public static FinalConsumerBill toEntity(CreateFinalConsumerBillDTO dto, String generationCode, String controlNumber, LocalDateTime date, double iva) {
        return new FinalConsumerBill(
            null,
            generationCode,
            controlNumber,
            date,
            dto.getAccount(),
            dto.getPaymentCondition(),
            dto.getCompanyName(),
            dto.getCompanyDocument(),
            dto.getCompanyAddress(),
            dto.getCompanyEmail(),
            dto.getCompanyPhone(),
            dto.getCustomerName(),
            dto.getCustomerDocument(),
            dto.getCustomerAddress(),
            dto.getCustomerEmail(),
            dto.getCustomerPhone(),
            null,
            dto.getNonTaxedSales(),
            dto.getExemptSales(),
            dto.getTaxedSales(),
            iva,
            dto.getPerceivedIva(),
            dto.getWithheldIva(),
            dto.getTotalWithIva()
        );
    }

    public static CreateFinalConsumerBillDTO toDTO(FinalConsumerBill finalConsumerBill) {
       CreateFinalConsumerBillDTO consumerBillDTO = new CreateFinalConsumerBillDTO(
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
                ).collect(Collectors.toList()),
                finalConsumerBill.getNonTaxedSales(),
                finalConsumerBill.getExemptSales(),
                finalConsumerBill.getTaxedSales(),
                finalConsumerBill.getIva(),
                finalConsumerBill.getPerceivedIva(),
                finalConsumerBill.getWithheldIva(),
                finalConsumerBill.getTotalWithIva()

            ); 
            
        return consumerBillDTO;
    }
}

