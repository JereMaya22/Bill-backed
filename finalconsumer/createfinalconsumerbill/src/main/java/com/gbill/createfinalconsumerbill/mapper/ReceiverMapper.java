package com.gbill.createfinalconsumerbill.mapper;

import com.gbill.createfinalconsumerbill.model.Receiver;
import com.gbill.createfinalconsumerbill.modeldto.CreateReceiver;

import shareddtos.billmodule.bill.ShowReceiver;

public class ReceiverMapper {

    public static Receiver toEntity(CreateReceiver dto) {
        return new Receiver(
            dto.getCustomerName(),
            dto.getCustomerLastname(),
            dto.getCustomerDocument(),
            dto.getCustomerAddress(),
            dto.getCustomerEmail(),
            dto.getCustomerPhone()
        );
    }

    public static CreateReceiver toDTO(Receiver receiver) {
        return new CreateReceiver(
            receiver.getName(),
            receiver.getDocument(),
            receiver.getAddress(),
            receiver.getEmail(),
            receiver.getPhone(),
            receiver.getLastName()
        );
    }

    public static ShowReceiver toShowReceiverDto(Receiver receiver) {
        return new ShowReceiver(
            receiver.getName(),
            receiver.getDocument(),
            receiver.getAddress(),
            receiver.getEmail(),
            receiver.getPhone(),
            receiver.getLastName()
        );
    }

}
