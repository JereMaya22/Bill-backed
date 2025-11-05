package com.gbill.createfinalconsumerbill.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

/**
 * Clase base para entidades relacionadas a partes de la factura (emisor/receptor).
 * Centraliza campos compartidos y su mapeo.
 * - @MappedSuperclass: los campos se incorporan en las tablas de las subclases.
 */
@MappedSuperclass
@Data
public class BillingParty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonProperty("nombre")
    private String name;
    
    @JsonProperty("dui")
    private String document;
    
    private String address;
    
    @JsonProperty("correo")
    private String email;
    
    @JsonProperty("telefono")
    private String phone;
}
