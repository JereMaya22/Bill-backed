package com.gbill.createfinalconsumerbill.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity
@EqualsAndHashCode(callSuper = true)
@Data
public class Transmitter extends BillingParty{

    public Transmitter(String name, String document, String address, String email, String phone) {
        super.setName(name);
        super.setDocument(document);
        super.setAddress(address);
        super.setEmail(email);
        super.setPhone(phone);
    }
}
