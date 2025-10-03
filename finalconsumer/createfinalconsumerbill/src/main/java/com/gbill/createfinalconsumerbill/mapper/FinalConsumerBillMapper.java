package com.gbill.createfinalconsumerbill.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.gbill.createfinalconsumerbill.model.BillItem;
import com.gbill.createfinalconsumerbill.model.FinalConsumerBill;
import com.gbill.createfinalconsumerbill.modeldto.CreateBillItemRequestDTO;
import com.gbill.createfinalconsumerbill.modeldto.CreateFinalConsumerBillDTO;

import shareddtos.billmodule.BillItem.BillItemDTO;
import shareddtos.billmodule.BillItem.CreateBillItemDTO;
import shareddtos.billmodule.bill.ShowBillDto;


public class FinalConsumerBillMapper {
    
    public static FinalConsumerBill toEntity(CreateFinalConsumerBillDTO dto, String generationCode
    , String controlNumber, LocalDateTime date, Double iva, String account,
    String companyName, String companyDocument, String companyAddress, String companyEmail, String companyPhone,
    Double nonTaxedSales, Double exemptSales, Double taxedSales, Double perceivedIva,
    Double totalWithIva, List<CreateBillItemDTO> items, String pdfPath) {
        return new FinalConsumerBill(
            null,
            generationCode,
            controlNumber,
            date,
            account,
            dto.getPaymentCondition(),
            companyName,
            companyDocument,
            companyAddress,
            companyEmail,
            companyPhone,
            dto.getCustomerName(),
            dto.getCustomerDocument(),
            dto.getCustomerAddress(),
            dto.getCustomerEmail(),
            dto.getCustomerPhone(),
            items.stream().map(
                billItem -> new BillItem(
                    null,
                    billItem.getProductId(),
                    billItem.getName(),
                    billItem.getRequestedQuantity(),
                    billItem.getPrice(),
                    billItem.getSubTotal(),
                    null
            )).collect(Collectors.toList()),
            nonTaxedSales,  // Ahora se usa el parámetro
            exemptSales,    // Ahora se usa el parámetro
            taxedSales,
            iva,
            perceivedIva,
            dto.getWithheldIva(),
            totalWithIva,
            pdfPath
        );
    }

    public static CreateFinalConsumerBillDTO toDTO(FinalConsumerBill finalConsumerBill) {
       CreateFinalConsumerBillDTO consumerBillDTO = new CreateFinalConsumerBillDTO(
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
                    billItem -> {
                        CreateBillItemRequestDTO requestDTO = new CreateBillItemRequestDTO();
                        requestDTO.setProductId(billItem.getProductId());
                        requestDTO.setRequestedQuantity(billItem.getRequestedQuantity());
                        return requestDTO;
                    }
                ).collect(Collectors.toList()),
                finalConsumerBill.getWithheldIva()
            ); 
            
        return consumerBillDTO;
    }

    public static ShowBillDto toShowBillDto(FinalConsumerBill finalConsumerBill) {
        ShowBillDto showBillDto = new ShowBillDto();
        showBillDto.setGenerationCode(finalConsumerBill.getGenerationCode());
        showBillDto.setControlNumber(finalConsumerBill.getControlNumber());
        showBillDto.setBillGenerationDate(finalConsumerBill.getBillGenerationDate());
        showBillDto.setAccount(finalConsumerBill.getAccount());
        showBillDto.setPaymentCondition(finalConsumerBill.getPaymentCondition());
        showBillDto.setCompanyName(finalConsumerBill.getCompanyName());
        showBillDto.setCompanyDocument(finalConsumerBill.getCompanyDocument());
        showBillDto.setCompanyAddress(finalConsumerBill.getCompanyAddress());
        showBillDto.setCompanyEmail(finalConsumerBill.getCompanyEmail());
        showBillDto.setCompanyPhone(finalConsumerBill.getCompanyPhone());
        showBillDto.setCustomerName(finalConsumerBill.getCustomerName());
        showBillDto.setCustomerDocument(finalConsumerBill.getCustomerDocument());
        showBillDto.setCustomerAddress(finalConsumerBill.getCustomerAddress());
        showBillDto.setCustomerEmail(finalConsumerBill.getCustomerEmail());
        showBillDto.setCustomerPhone(finalConsumerBill.getCustomerPhone());
        showBillDto.setProducts(finalConsumerBill.getProducts().stream().map(
            productos -> {
                BillItemDTO billItemDTO = new BillItemDTO();
                billItemDTO.setId(productos.getId());
                        billItemDTO.setProductId(productos.getProductId());
                        billItemDTO.setName(productos.getName());
                        billItemDTO.setRequestedQuantity(productos.getRequestedQuantity());
                        billItemDTO.setPrice(productos.getPrice());
                        billItemDTO.setSubTotal(productos.getSubTotal());
                        return billItemDTO;
            }
        ).collect(Collectors.toList()));
        showBillDto.setNonTaxedSales(finalConsumerBill.getNonTaxedSales());
        showBillDto.setExemptSales(finalConsumerBill.getExemptSales());
        showBillDto.setTaxedSales(finalConsumerBill.getTaxedSales());
        showBillDto.setIva(finalConsumerBill.getIva());
        showBillDto.setPerceivedIva(finalConsumerBill.getPerceivedIva());
        showBillDto.setWithheldIva(finalConsumerBill.getWithheldIva());
        showBillDto.setTotalWithIva(finalConsumerBill.getTotalWithIva());
        return showBillDto;
    }
}

