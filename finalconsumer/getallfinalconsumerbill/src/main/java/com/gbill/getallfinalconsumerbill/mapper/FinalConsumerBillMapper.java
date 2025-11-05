package com.gbill.getallfinalconsumerbill.mapper;

import java.util.stream.Collectors;

import com.gbill.getallfinalconsumerbill.model.BillItem;
import com.gbill.getallfinalconsumerbill.model.FinalConsumerBill;
import com.gbill.getallfinalconsumerbill.model.Receiver;
import com.gbill.getallfinalconsumerbill.model.Transmitter;

import shareddtos.billmodule.BillItem.BillItemDTO;
import shareddtos.billmodule.bill.PaymentDTO;
import shareddtos.billmodule.bill.ShowBillDto;
import shareddtos.billmodule.bill.ShowReceiver;
import shareddtos.billmodule.bill.ShowTransmitter;

public class FinalConsumerBillMapper {

    public static ShowBillDto toDto(FinalConsumerBill bill) {
        ShowBillDto dto = new ShowBillDto();

        // ✅ Datos generales de la factura
        dto.setGenerationCode(bill.getGenerationCode());
        dto.setControlNumber(bill.getControlNumber());
        dto.setBillGenerationDate(bill.getBillGenerationDate());
        dto.setAccount(bill.getAccount());
        dto.setPaymentCondition(bill.getPaymentCondition());

        // ✅ Transmitter (emisor)
        dto.setTransmitter(toTransmitterDto(bill.getTransmitter()));

        // ✅ Receiver (receptor)
        dto.setReceiver(toReceiverDto(bill.getReceiver()));

        // ✅ Productos
        if (bill.getProducts() != null) {
            dto.setProducts(
                bill.getProducts()
                    .stream()
                    .map(FinalConsumerBillMapper::toBillItemDto)
                    .collect(Collectors.toList())
            );
        }

        // ✅ Totales
        dto.setNonTaxedSales(bill.getNonTaxedSales());
        dto.setExemptSales(bill.getExemptSales());
        dto.setTaxedSales(bill.getTaxedSales());
        dto.setIva(bill.getIva());
        dto.setPerceivedIva(bill.getPerceivedIva());
        dto.setWithheldIva(bill.getWithheldIva());
        dto.setTotalWithIva(bill.getTotalWithIva());

        // ✅ Promociones
        dto.setPromotionApplied(bill.getPromotionApplied());
        dto.setPromotionCode(bill.getPromotionCode());
        dto.setPromotionName(bill.getPromotionName());
        dto.setPromotionDiscount(bill.getPromotionDiscount());
        dto.setProductsWithPromotion(bill.getProductsWithPromotion());

        // ✅ Pago
        if (bill.getPayment() != null) {
            PaymentDTO paymentDto = new PaymentDTO();
            paymentDto.setPaymentType(bill.getPayment().getPaymentType());
            paymentDto.setCardType(bill.getPayment().getCardType());
            paymentDto.setMaskedCardNumber(bill.getPayment().getMaskedCardNumber());
            paymentDto.setCardHolder(bill.getPayment().getCardHolder());
            paymentDto.setAuthorizationCode(bill.getPayment().getAuthorizationCode());
            dto.setPayment(paymentDto);
        }

        // ✅ Campos de factura devuelta (reversión)
        dto.setIsReversed(bill.getIsReversed());
        dto.setReturnBillCode(bill.getReturnBillCode());
        dto.setOriginBillCode(bill.getOriginBillCode());

        return dto;
    }

    // ============================
    // Métodos auxiliares
    // ============================

    private static ShowTransmitter toTransmitterDto(Transmitter transmitter) {
        if (transmitter == null) return null;
        return new ShowTransmitter(
            transmitter.getName(),
            transmitter.getDocument(),
            transmitter.getAddress(),
            transmitter.getEmail(),
            transmitter.getPhone()
        );
    }

    private static ShowReceiver toReceiverDto(Receiver receiver) {
        if (receiver == null) return null;
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
        if (billItem == null) return null;
        BillItemDTO dto = new BillItemDTO();
        dto.setId(billItem.getId());
        dto.setProductId(billItem.getProductId());
        dto.setName(billItem.getName());
        dto.setRequestedQuantity(billItem.getRequestedQuantity());
        dto.setPrice(billItem.getPrice());
        dto.setSubTotal(billItem.getSubTotal());
        return dto;
    }
}
