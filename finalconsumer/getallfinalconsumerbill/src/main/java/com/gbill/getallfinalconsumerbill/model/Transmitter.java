package com.gbill.getallfinalconsumerbill.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Transmitter extends BillingParty{

    public Transmitter(String name, String document, String address, String email, String phone) {
        super.setName(name);
        super.setDocument(document);
        super.setAddress(address);
        super.setEmail(email);
        super.setPhone(phone);
    }
}
