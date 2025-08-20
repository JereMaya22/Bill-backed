package com.gbill.getallbill.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String codeGenerator;
    private Date date;

    //Emisor
    private String companyName;
    private Long companyNIT;
    private String companyAddress;
    private String companyPhone;
    private String companyEmail;

    //Receptor
    private String clientName;
    private String clientDUI;
    private int clientPhone;
    private String clientEmail;

    //products
    private List<Product> listproducts;

}
