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
        return new ShowBillDto(
            finalConsumerBill.getGenerationCode(),
            finalConsumerBill.getControlNumber(),
            finalConsumerBill.getBillGenerationDate(),
            finalConsumerBill.getAccount(),
            finalConsumerBill.getPaymentCondition(),
            toTransmitterDto(finalConsumerBill.getTransmitter()),
            toReceiverDto(finalConsumerBill.getReceiver()),
            finalConsumerBill.getProducts().stream().map(FinalConsumerBillMapper::toBillItemDto).collect(Collectors.toList()),
            finalConsumerBill.getNonTaxedSales(),
            finalConsumerBill.getExemptSales(),
            finalConsumerBill.getTaxedSales(),
            finalConsumerBill.getIva(),
            finalConsumerBill.getPerceivedIva(),
            finalConsumerBill.getWithheldIva(),
            finalConsumerBill.getTotalWithIva()
        );
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
