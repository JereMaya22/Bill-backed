package com.gbill.createfinalconsumerbill.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.gbill.createfinalconsumerbill.model.*;
import com.gbill.createfinalconsumerbill.modeldto.*;
import com.gbill.createfinalconsumerbill.modeldto.payment.PaymentDTO;

import shareddtos.billmodule.BillItem.BillItemDTO;
import shareddtos.billmodule.BillItem.CreateBillItemDTO;
import shareddtos.billmodule.bill.ShowBillDto;
import shareddtos.billmodule.bill.ShowReceiver;
import shareddtos.billmodule.bill.ShowTransmitter;

public class FinalConsumerBillMapper {

    public static FinalConsumerBill toEntity(CreateFinalConsumerBillDTO dto, String generationCode,
            String controlNumber, LocalDateTime date, Double iva, String account,
            Double nonTaxedSales, Double exemptSales, Double taxedSales, Double perceivedIva,
            Double totalWithIva, List<CreateBillItemDTO> items, String pdfPath, Boolean promotionApplied,
            String promotionCode, String promotionName, Double promotionDiscount,
            List<Long> productsWithPromotion) {

        Transmitter transmitter = TransmitterMapper.toEntity(dto.getTransmitter());
        Receiver receiver = ReceiverMapper.toEntity(dto.getReceiver());

        FinalConsumerBill bill = new FinalConsumerBill(
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
                                null))
                        .collect(Collectors.toList()),
                nonTaxedSales,
                exemptSales,
                taxedSales,
                iva,
                perceivedIva,
                dto.getWithheldIva(),
                totalWithIva,
                pdfPath,
                promotionApplied,
                promotionCode,
                promotionName,
                promotionDiscount,
                productsWithPromotion);

        // ✅ Mapear pago si viene en el DTO
        if (dto.getPayment() != null) {
            PaymentDTO p = dto.getPayment();
            Payment payment = new Payment();
            payment.setPaymentType(p.getPaymentType());
            payment.setCardType(p.getCardType());
            payment.setMaskedCardNumber(p.getMaskedCardNumber());
            payment.setCardHolder(p.getCardHolder());
            payment.setAuthorizationCode(p.getAuthorizationCode());
            payment.setBill(bill);
            bill.setPayment(payment);
        }

        return bill;
    }

    public static CreateFinalConsumerBillDTO toDTO(FinalConsumerBill finalConsumerBill) {
        CreateTransmitter transmitterDTO = TransmitterMapper.toDTO(finalConsumerBill.getTransmitter());
        CreateReceiver receiverDTO = ReceiverMapper.toDTO(finalConsumerBill.getReceiver());

        CreateFinalConsumerBillDTO dto = new CreateFinalConsumerBillDTO(
                finalConsumerBill.getPaymentCondition(),
                transmitterDTO,
                receiverDTO,
                finalConsumerBill.getProducts().stream().map(
                        billItem -> {
                            CreateBillItemRequestDTO requestDTO = new CreateBillItemRequestDTO();
                            requestDTO.setProductId(billItem.getProductId());
                            requestDTO.setRequestedQuantity(billItem.getRequestedQuantity());
                            return requestDTO;
                        })
                        .collect(Collectors.toList()),
                finalConsumerBill.getWithheldIva());

        // ✅ Mapear pago si existe
        if (finalConsumerBill.getPayment() != null) {
            Payment p = finalConsumerBill.getPayment();
            PaymentDTO paymentDto = new PaymentDTO(
                    p.getPaymentType(),
                    p.getCardType(),
                    p.getMaskedCardNumber(),
                    p.getCardHolder(),
                    p.getAuthorizationCode());
            dto.setPayment(paymentDto);
        }

        return dto;
    }

    public static ShowBillDto toShowBillDto(FinalConsumerBill finalConsumerBill) {
        ShowReceiver receiverDTO = ReceiverMapper.toShowReceiverDto(finalConsumerBill.getReceiver());
        ShowTransmitter transmitterDTO = TransmitterMapper.toShowTransmitterDto(finalConsumerBill.getTransmitter());

        ShowBillDto show = new ShowBillDto();
        show.setGenerationCode(finalConsumerBill.getGenerationCode());
        show.setControlNumber(finalConsumerBill.getControlNumber());
        show.setBillGenerationDate(finalConsumerBill.getBillGenerationDate());
        show.setAccount(finalConsumerBill.getAccount());
        show.setPaymentCondition(finalConsumerBill.getPaymentCondition());
        show.setTransmitter(transmitterDTO);
        show.setReceiver(receiverDTO);

        show.setProducts(finalConsumerBill.getProducts().stream().map(productos -> {
            BillItemDTO billItemDTO = new BillItemDTO();
            billItemDTO.setId(productos.getId());
            billItemDTO.setProductId(productos.getProductId());
            billItemDTO.setName(productos.getName());
            billItemDTO.setRequestedQuantity(productos.getRequestedQuantity());
            billItemDTO.setPrice(productos.getPrice());
            billItemDTO.setSubTotal(productos.getSubTotal());
            return billItemDTO;
        }).collect(Collectors.toList()));

        show.setNonTaxedSales(finalConsumerBill.getNonTaxedSales());
        show.setExemptSales(finalConsumerBill.getExemptSales());
        show.setTaxedSales(finalConsumerBill.getTaxedSales());
        show.setIva(finalConsumerBill.getIva());
        show.setPerceivedIva(finalConsumerBill.getPerceivedIva());
        show.setWithheldIva(finalConsumerBill.getWithheldIva());
        show.setTotalWithIva(finalConsumerBill.getTotalWithIva());
        show.setPromotionApplied(finalConsumerBill.getPromotionApplied());
        show.setPromotionCode(finalConsumerBill.getPromotionCode());
        show.setPromotionName(finalConsumerBill.getPromotionName());
        show.setPromotionDiscount(finalConsumerBill.getPromotionDiscount());
        show.setProductsWithPromotion(finalConsumerBill.getProductsWithPromotion());

        // ✅ Convertir Payment local a PaymentDTO de librería
        if (finalConsumerBill.getPayment() != null) {
            Payment p = finalConsumerBill.getPayment();
            shareddtos.billmodule.bill.PaymentDTO sharedPayment = new shareddtos.billmodule.bill.PaymentDTO();
            sharedPayment.setPaymentType(p.getPaymentType());
            sharedPayment.setCardType(p.getCardType());
            sharedPayment.setMaskedCardNumber(p.getMaskedCardNumber());
            sharedPayment.setCardHolder(p.getCardHolder());
            sharedPayment.setAuthorizationCode(p.getAuthorizationCode());
            show.setPayment(sharedPayment);
        }

        return show;
    }
}
