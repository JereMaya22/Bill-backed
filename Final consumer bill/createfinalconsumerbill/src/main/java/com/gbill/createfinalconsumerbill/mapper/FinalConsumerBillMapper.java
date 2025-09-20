package com.gbill.createfinalconsumerbill.mapper;

import java.time.LocalDateTime;

import com.gbill.createfinalconsumerbill.model.FinalConsumerBill;
import com.gbill.createfinalconsumerbill.modeldto.CreateFinalConsumerBillDTO;

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
}

