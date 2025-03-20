package com.gbill.createfinalconsumerbill.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class FinalConsumerBill {
    
    private Long id;
    private String invoidId;
    private Date date;

    //transmitter
    private String companyName;
    private String companyDocument;
    private String companyAddress;
    private String companyEmail;
    private String companyPhone;    

    //receiver
    private String customerName;
    private String customerDocument;
    private String customerAddress;
    private String customerEmail;
    private String customerPhone;  

    //products
    private List<ProductBill> products;

}
