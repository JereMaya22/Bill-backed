package com.gbill.getallbill.model;

import jakarta.persistence.*;
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
    private String companyNIT;
    private String companyAddress;
    private String companyPhone;
    private String companyEmail;

    //Receptor
    private String clientName;
    private String clientDUI;
    private int clientPhone;
    private String clientEmail;

    //products
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id")
    private List<Product> listproducts;

}
