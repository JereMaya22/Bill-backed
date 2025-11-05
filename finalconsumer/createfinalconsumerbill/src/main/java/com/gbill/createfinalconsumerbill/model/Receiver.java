package com.gbill.createfinalconsumerbill.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entidad de receptor (cliente) de la factura.
 * Hereda campos comunes de BillingParty.
 * Nota: JPA requiere constructor por defecto (provisto por @NoArgsConstructor).
 * El campo lastName se mapea desde JSON externo usando @JsonProperty("apellido").
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
public class Receiver extends BillingParty{
    @JsonProperty("apellido")
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
