package com.gbill.createfinalconsumerbill.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entidad de emisor (empresa) de la factura.
 * Hereda campos de BillingParty y provee constructor por defecto para JPA.
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor  // ← AGREGAR: Genera constructor sin argumentos
public class Transmitter extends BillingParty{

    public Transmitter(String name, String document, String address, String email, String phone) {
        super.setName(name);
        super.setDocument(document);
        super.setAddress(address);
        super.setEmail(email);
        super.setPhone(phone);
    }
}
