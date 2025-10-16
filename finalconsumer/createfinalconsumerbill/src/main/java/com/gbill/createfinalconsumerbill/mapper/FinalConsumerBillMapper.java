package com.gbill.createfinalconsumerbill.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.gbill.createfinalconsumerbill.model.BillItem;
import com.gbill.createfinalconsumerbill.model.FinalConsumerBill;
import com.gbill.createfinalconsumerbill.model.Receiver;
import com.gbill.createfinalconsumerbill.model.Transmitter;
import com.gbill.createfinalconsumerbill.modeldto.CreateBillItemRequestDTO;
import com.gbill.createfinalconsumerbill.modeldto.CreateFinalConsumerBillDTO;
import com.gbill.createfinalconsumerbill.modeldto.CreateReceiver;
import com.gbill.createfinalconsumerbill.modeldto.CreateTransmitter;

import shareddtos.billmodule.BillItem.BillItemDTO;
import shareddtos.billmodule.BillItem.CreateBillItemDTO;
import shareddtos.billmodule.bill.ShowBillDto;
import shareddtos.billmodule.bill.ShowReceiver;
import shareddtos.billmodule.bill.ShowTransmitter;


public class FinalConsumerBillMapper {
    
    public static FinalConsumerBill toEntity(CreateFinalConsumerBillDTO dto, String generationCode,
    String controlNumber, LocalDateTime date, Double iva, String account,
    Double nonTaxedSales, Double exemptSales, Double taxedSales, Double perceivedIva,
    Double totalWithIva, List<CreateBillItemDTO> items, String pdfPath) {
        Transmitter transmitter = TransmitterMapper.toEntity(dto.getTransmitter());
        Receiver receiver = ReceiverMapper.toEntity(dto.getReceiver());
        return new FinalConsumerBill(
            null,
            generationCode,
            controlNumber,
            date,
            account,
            dto.getPaymentCondition(),
            transmitter,
            receiver,
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
        CreateTransmitter transmitterDTO = TransmitterMapper.toDTO(finalConsumerBill.getTransmitter());
        CreateReceiver receiverDTO = ReceiverMapper.toDTO(finalConsumerBill.getReceiver());
        return new CreateFinalConsumerBillDTO(
                finalConsumerBill.getPaymentCondition(),
                transmitterDTO,
                receiverDTO,
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
            
    }

    public static ShowBillDto toShowBillDto(FinalConsumerBill finalConsumerBill) {
        ShowReceiver receiverDTO = ReceiverMapper.toShowReceiverDto(finalConsumerBill.getReceiver());
        ShowTransmitter transmitterDTO = TransmitterMapper.toShowTransmitterDto(finalConsumerBill.getTransmitter());
        return new ShowBillDto(
            finalConsumerBill.getGenerationCode(),
            finalConsumerBill.getControlNumber(),
            finalConsumerBill.getBillGenerationDate(),
            finalConsumerBill.getAccount(),
            finalConsumerBill.getPaymentCondition(),
            transmitterDTO,
            receiverDTO,
            finalConsumerBill.getProducts().stream().map(
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
            ).collect(Collectors.toList()),
            finalConsumerBill.getNonTaxedSales(),
            finalConsumerBill.getExemptSales(),
            finalConsumerBill.getTaxedSales(),
            finalConsumerBill.getIva(),
            finalConsumerBill.getPerceivedIva(),
            finalConsumerBill.getWithheldIva(),
            finalConsumerBill.getTotalWithIva()
        );
    }
}

