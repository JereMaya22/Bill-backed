package com.gbill.createfinalconsumerbill.modeldto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFinalConsumerBillDTO {

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
    private List<ProductBillDTO> products;

}
