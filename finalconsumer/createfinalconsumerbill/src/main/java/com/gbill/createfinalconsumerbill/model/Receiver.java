package com.gbill.createfinalconsumerbill.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Receiver extends BillingParty{
    private String lastName;

    public Receiver(String name, String lastName, String document, String address, String email, String phone) {
        super.setName(name);
        super.setDocument(document);
        super.setAddress(address);
        super.setEmail(email);
        super.setPhone(phone);
        this.lastName = lastName;

    }
}
