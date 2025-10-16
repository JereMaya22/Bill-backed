package com.gbill.getallfinalconsumerbill.mapper;

import java.util.stream.Collectors;

import com.gbill.getallfinalconsumerbill.model.BillItem;
import com.gbill.getallfinalconsumerbill.model.FinalConsumerBill;
import com.gbill.getallfinalconsumerbill.model.Receiver;
import com.gbill.getallfinalconsumerbill.model.Transmitter;

import shareddtos.billmodule.BillItem.BillItemDTO;
import shareddtos.billmodule.bill.ShowBillDto;
import shareddtos.billmodule.bill.ShowReceiver;
import shareddtos.billmodule.bill.ShowTransmitter;

public class FinalConsumerBillMapper {

    public static ShowBillDto toDto(FinalConsumerBill finalConsumerBill) {
        ShowBillDto dto = new ShowBillDto();
        dto.setGenerationCode(finalConsumerBill.getGenerationCode());
        dto.setControlNumber(finalConsumerBill.getControlNumber());
        dto.setBillGenerationDate(finalConsumerBill.getBillGenerationDate());
        dto.setAccount(finalConsumerBill.getAccount());
        dto.setPaymentCondition(finalConsumerBill.getPaymentCondition());
        dto.setTransmitter(toTransmitterDto(finalConsumerBill.getTransmitter()));
        dto.setReceiver(toReceiverDto(finalConsumerBill.getReceiver()));
        dto.setProducts(finalConsumerBill.getProducts()
                .stream()
                .map(FinalConsumerBillMapper::toBillItemDto)
                .collect(Collectors.toList()));

        dto.setNonTaxedSales(finalConsumerBill.getNonTaxedSales());
        dto.setExemptSales(finalConsumerBill.getExemptSales());
        dto.setTaxedSales(finalConsumerBill.getTaxedSales());
        dto.setIva(finalConsumerBill.getIva());
        dto.setPerceivedIva(finalConsumerBill.getPerceivedIva());
        dto.setWithheldIva(finalConsumerBill.getWithheldIva());
        dto.setTotalWithIva(finalConsumerBill.getTotalWithIva());

        // 🔸 Nuevos campos añadidos en tu ShowBillDto
        dto.setPromotionApplied(finalConsumerBill.getPromotionApplied());
        dto.setPromotionCode(finalConsumerBill.getPromotionCode());
        dto.setPromotionName(finalConsumerBill.getPromotionName());
        dto.setPromotionDiscount(finalConsumerBill.getPromotionDiscount());
        dto.setProductsWithPromotion(finalConsumerBill.getProductsWithPromotion());

        // 🔸 Y el pago si existe
        if (finalConsumerBill.getPayment() != null) {
            shareddtos.billmodule.bill.PaymentDTO paymentDto = new shareddtos.billmodule.bill.PaymentDTO();
            paymentDto.setPaymentType(finalConsumerBill.getPayment().getPaymentType());
            paymentDto.setCardType(finalConsumerBill.getPayment().getCardType());
            paymentDto.setMaskedCardNumber(finalConsumerBill.getPayment().getMaskedCardNumber());
            paymentDto.setCardHolder(finalConsumerBill.getPayment().getCardHolder());
            paymentDto.setAuthorizationCode(finalConsumerBill.getPayment().getAuthorizationCode());
            dto.setPayment(paymentDto);
        }

        return dto;
    }

    private static ShowTransmitter toTransmitterDto(Transmitter transmitter) {
        if (transmitter == null) {
            return null;
        }
        return new ShowTransmitter(
            transmitter.getName(),
            transmitter.getDocument(),
            transmitter.getAddress(),
            transmitter.getEmail(),
            transmitter.getPhone()
        );
    }

    private static ShowReceiver toReceiverDto(Receiver receiver) {
        if (receiver == null) {
            return null;
        }
        return new ShowReceiver(
            receiver.getName(),
            receiver.getLastName(),
            receiver.getDocument(),
            receiver.getAddress(),
            receiver.getEmail(),
            receiver.getPhone()
        );
    }

    private static BillItemDTO toBillItemDto(BillItem billItem) {
        if (billItem == null) {
            return null;
        }
        BillItemDTO billItemDTO = new BillItemDTO();
        billItemDTO.setId(billItem.getId());
        billItemDTO.setProductId(billItem.getProductId());
        billItemDTO.setName(billItem.getName());
        billItemDTO.setRequestedQuantity(billItem.getRequestedQuantity());
        billItemDTO.setPrice(billItem.getPrice());
        billItemDTO.setSubTotal(billItem.getSubTotal());
        return billItemDTO;
    }
}
