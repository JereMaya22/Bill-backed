package com.gbill.createfinalconsumerbill.mapper;

import com.gbill.createfinalconsumerbill.model.Transmitter;
import com.gbill.createfinalconsumerbill.modeldto.CreateTransmitter;

import shareddtos.billmodule.bill.ShowTransmitter;

public class TransmitterMapper {
    public static Transmitter toEntity(CreateTransmitter dto) {
        return new Transmitter(
            dto.getCompanyName(),
            dto.getCompanyDocument(),
            dto.getCompanyAddress(),
            dto.getCompanyEmail(),
            dto.getCompanyPhone()
        );
    }

    public static CreateTransmitter toDTO(Transmitter transmitter) {
        return new CreateTransmitter(
            transmitter.getName(),
            transmitter.getDocument(),
            transmitter.getAddress(),
            transmitter.getEmail(),
            transmitter.getPhone()
        );
    }

    public static ShowTransmitter toShowTransmitterDto(Transmitter transmitter) {
        return new ShowTransmitter(
            transmitter.getName(),
            transmitter.getDocument(),
            transmitter.getAddress(),
            transmitter.getEmail(),
            transmitter.getPhone()
        );
    }
    
}
